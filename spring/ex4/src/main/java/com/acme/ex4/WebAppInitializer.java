package com.acme.ex4;

import javax.servlet.ServletException;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.acme.ex3.ApplicationConfig;


public class WebAppInitializer implements WebApplicationInitializer {
	
	@Configuration @EnableWebMvc @ComponentScan
	public static class RestConfiguration extends WebMvcConfigurerAdapter {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}
    }
	
	@Override
	public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext appCtx = new AnnotationConfigWebApplicationContext();
		appCtx.register(ApplicationConfig.class); // contexte d'ex3
		servletContext.addListener(new org.springframework.web.context.ContextLoaderListener(appCtx));


		// Activation de la DispatcherSerlvet
        
        // déclaration du contexte web sur lequel doit s'appuyer la servlet Spring
		AnnotationConfigWebApplicationContext webCtx= new AnnotationConfigWebApplicationContext();
		webCtx.register(RestConfiguration.class);

		// instanciation de la DispatcherServlet de Spring
		DispatcherServlet springMvcServlet = new DispatcherServlet(webCtx);

		// inscription de la servlet Spring auprès du servletContainer, grâce au servletContext
		javax.servlet.ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("springMvcServlet", springMvcServlet);
        servletRegistration.addMapping("/api/*");
        servletRegistration.setLoadOnStartup(0);
        
	}
}
