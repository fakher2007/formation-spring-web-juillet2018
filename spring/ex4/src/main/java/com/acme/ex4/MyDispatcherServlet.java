package com.acme.ex4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyDispatcherServlet extends HttpServlet {

    private WebApplicationContext ctx;

    private Map<RequestMapping, Method> requestHandlers;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private Map<String, ObjectMapper> mappers;

    public MyDispatcherServlet(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void initialize() throws ServletException {

        ConfigurableApplicationContext context=(ConfigurableApplicationContext)ctx;
        context.refresh();

        this.mappers = new HashMap<>();
        this.mappers.put("application/json", new ObjectMapper());
        this.mappers.put("application/xml", new XmlMapper());

        Collection<Object> restControllers = this.ctx.getBeansWithAnnotation(RestController.class).values();
        Collection<Object> controllers = this.ctx.getBeansWithAnnotation(RestController.class).values();

        this.requestHandlers = Stream.concat(restControllers.stream(), controllers.stream())
                .flatMap(c -> Stream.of(c.getClass().getMethods()))
                .filter(m -> m.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toMap(m -> m.getAnnotation(RequestMapping.class), m -> m));
    }

    private Method getRequestHandler(HttpServletRequest req){

        String requestPath = req.getPathInfo().substring(1);
        String verb = req.getMethod();
        Method methodToInvoke = null;// allons chercher dans requestHandlers la méthode à appeler

        System.out.println("request path "+requestPath);
        for (Entry<RequestMapping, Method> reqHandler : requestHandlers.entrySet()) {
            RequestMapping mapping = reqHandler.getKey();
            String mappingPath = mapping.path()[0];
            String mappingPathWithRegexp = mappingPath.replaceAll("\\{(\\w.*?)\\}", "[a-zA-Z0-9]*\\$");
            // si mapping.path()[0] est /candidates/{id}
            // mappingPathWithRegexp sera candidates/[a-zA-Z0-9]*$

            // nous pouvons alors voir si le path de la requête entrante (exemple : /candidates/1)
            // match avec l'expression régulière mappingPathWithRegexp
            if(requestPath.matches(mappingPathWithRegexp)){
                System.out.println(reqHandler.getValue());
                // si oui, voyons si le verbe de la requête entrante fait parti des méthodes
                // gérées par le handlers (cf la propriété method de l'annotation RequestMapping)
                if(Stream.of(mapping.method()).anyMatch(m -> m.name().equalsIgnoreCase(verb))){
                    if(methodToInvoke!=null){
                        throw new IllegalStateException("multiple handlers found for "+requestPath);
                    }
                    methodToInvoke = reqHandler.getValue();
                }
            }
        }

        return methodToInvoke;
    }


    boolean ready = false;
    @SuppressWarnings("unused")
	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!ready){
            initialize();
            ready =true;
        }
        Method requestHandler= getRequestHandler(req);

        if(requestHandler==null){
            resp.setStatus(404);
        }
        Object controller = ctx.getBean(requestHandler.getDeclaringClass());

        ModelMap modelForView = new ModelMap();
        Map<String, Object> modelForViewAsMap = new HashMap<>();
        
        try {
            Parameter[] params = requestHandler.getParameters();
            Object[] argsForHandler = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                Parameter p = params[i];
                // well knowns types
                if(p.getType().equals(HttpServletRequest.class)) argsForHandler[i] = req;
                if(p.getType().equals(HttpServletResponse.class)) argsForHandler[i] = resp;
                if(p.getType().equals(InputStream.class)) argsForHandler[i] = req.getInputStream();
                if(p.getType().equals(OutputStream.class)) argsForHandler[i] = resp.getOutputStream();
                if(p.getType().equals(HttpSession.class)) argsForHandler[i] = req.getSession();

                
                if(p.getType().equals(ModelMap.class)) argsForHandler[i] = modelForView;
                if(p.getType().equals(Map.class)) argsForHandler[i] = modelForViewAsMap;


                // annotated types
                // param name : maven compiler plugin (<parameters>true</parameters>) or asm (https://stackoverflow.com/a/2729907)
                else if(p.isAnnotationPresent(RequestParam.class)){
                    String paramValue = req.getParameter(p.getName());
                    if(paramValue==null && p.getType().isPrimitive()){
                        throw new IllegalArgumentException("cannot give a null value to the method parameter");
                    }
                    if(!p.getType().isAssignableFrom(String.class))
                    {
                        // conversion de paramValue dans le type du paramètre (Integer.parse, Double.parse, Boolean.parse...)
                    }
                    else {
                        argsForHandler[i]= paramValue;
                    }
                }
                else if(p.isAnnotationPresent(RequestHeader.class)){
                    String headerValue = req.getHeader(p.getName());
                    if(headerValue==null && p.getType().isPrimitive()){
                        throw new IllegalArgumentException("cannot give a null value to the method parameter");
                    }
                    if(!p.getType().isAssignableFrom(String.class))
                    {
                        // conversion de paramValue dans le type du paramètre (Integer.parse, Double.parse, Boolean.parse...)
                    }
                    else {
                        argsForHandler[i]= headerValue;
                    }

                }
                else if(p.isAnnotationPresent(RequestBody.class)){
                    ObjectMapper mapper = mappers.get(req.getContentType());
                    if(mapper == null){
                        resp.setStatus(406);
                        return;
                    }

                    Object o = mapper.readValue(req.getInputStream(), p.getType());

                    if(p.isAnnotationPresent(Valid.class)){
                        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
                        if(!constraintViolations.isEmpty()){
                            resp.setStatus(400);
                            return;
                        }
                    }
                }
                else if(p.isAnnotationPresent(PathVariable.class)){

                    String mappingPath =requestHandler.getAnnotation(RequestMapping.class).path()[0];
                    String mappingPathWithRegexp = mappingPath.replace("{"+p.getName()+"}", "(.*?)");
                    Pattern r = Pattern.compile(mappingPathWithRegexp);

                    String requestPath = req.getPathInfo().substring(1);
                    Matcher m = r.matcher(requestPath);
                    if(m.matches()) {
                        String value = m.toMatchResult().group(1);
                        if(!p.getType().isAssignableFrom(String.class))
                        {
                            // conversion de value dans le type du paramètre (Integer.parse, Double.parse, Boolean.parse...)
                        }
                        else {
                            argsForHandler[i] = value;
                        }
                    }
                }
                else{
                    Object o = p.getType().getConstructor().newInstance();
                    for (Entry<String, String[]> reqParam : req.getParameterMap().entrySet()) {
                        String name= reqParam.getKey();
                        String[] values =  reqParam.getValue();
                    }
                    if(p.isAnnotationPresent(Valid.class)){
                        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);
                        if(!constraintViolations.isEmpty()){
                            resp.setStatus(400);
                            return;
                        }
                    }
                }
            }


            Object ret = requestHandler.invoke(controller, argsForHandler);
            if(ret!=null){
                if (ret instanceof String && controller.getClass().isAnnotationPresent(Controller.class)){
                    String next = (String)ret;
                    if(next.startsWith("redirect:/")){
                        String _next = next.replace("redirect:/", "");
                        resp.sendRedirect(_next);
                    }
                    else {
                    	if(!modelForView.isEmpty()) {
                            for (Entry<String, Object> attribute : modelForView.entrySet()) {
                                req.setAttribute(attribute.getKey(), attribute.getValue());
                            }
                        }
                    	if(!modelForViewAsMap.isEmpty()) {
                            for (Entry<String, Object> attribute : modelForViewAsMap.entrySet()) {
                                req.setAttribute(attribute.getKey(), attribute.getValue());
                            }
                        }
                    	getServletContext().getRequestDispatcher("/WEB-INF/views/"+next+".jsp").forward(req, resp);
                    }
                }
                else /*REST*/ {

                    Object body;
                    if (ret instanceof ResponseEntity) {
                        ResponseEntity<?> re = (ResponseEntity) ret;
                        resp.setStatus(re.getStatusCode().value());
                        for (Entry<String, List<String>> header : re.getHeaders().entrySet()) {
                            resp.setHeader(header.getKey(), String.join(",", header.getValue()));
                        }
                        body = re.getBody();
                    } else {
                        resp.setStatus(200);
                        body = ret;
                    }
                    String accept = req.getHeader("accept");
                    ObjectMapper mapper= mappers.get(accept);
                    if(mapper == null){
                        resp.setStatus(415);
                        return;
                    }
                    String contentType = accept;
                    String bodyAsString = mapper.writeValueAsString(body);
                    resp.getWriter().print(bodyAsString);
                    resp.setContentType(contentType);
                    resp.setContentLength(bodyAsString.length());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
