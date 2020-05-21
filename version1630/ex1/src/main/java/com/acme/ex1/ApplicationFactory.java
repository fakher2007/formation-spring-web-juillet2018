package com.acme.ex1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.dao.impl.FoxMovieDaoImpl;
import com.acme.ex1.dao.impl.WarnerMovieDaoImpl;
import com.acme.ex1.service.MovieService;
import com.acme.ex1.service.impl.MovieServiceImpl;
import com.acme.ex1.service.impl.SuperMovieServiceImpl;

public class ApplicationFactory {

	private static Map<String, Object> beans = new HashMap<>();
	
	private static <T> T createBean(T impl) {
		
		ClassLoader loader = impl.getClass().getClassLoader();
		
		Class<?>[] interfaces = impl.getClass().getInterfaces();
		InvocationHandler handler = new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				long n = System.currentTimeMillis();
				Object ret = method.invoke(impl, args);
				long n2 = System.currentTimeMillis();
				System.out.println("it took "+(n2-n)+"ms to call "+method.toString());
				return ret;
			}
		};
		T proxy = (T) Proxy.newProxyInstance(loader, interfaces , handler);
		return proxy;
	}
	
	static {
		MovieDao fox = createBean(new FoxMovieDaoImpl());
		MovieDao warner = createBean(new WarnerMovieDaoImpl());
		MovieService service1 = createBean(new MovieServiceImpl(fox));
		MovieService service2 = createBean(new MovieServiceImpl(warner));
		MovieService superService = createBean(new SuperMovieServiceImpl(Set.of(fox, warner)));
		beans.put("fox", fox);
		beans.put("warner", warner);
		beans.put("service1", service1);
		beans.put("service2", service2);
		beans.put("superService", superService);
	}
	
	public static Object getBean(String name) {
		Object bean = beans.get(name);
		if(bean==null) {
			throw new IllegalArgumentException("no bean named "+name);
		}
		return bean;
	}
}
