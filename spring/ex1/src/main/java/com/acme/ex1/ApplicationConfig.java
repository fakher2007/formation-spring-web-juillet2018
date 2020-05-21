package com.acme.ex1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.acme.ex1.dao.impl.FoxMovieDaoImpl;
import com.acme.ex1.dao.impl.WarnerMovieDaoImpl;
import com.acme.ex1.service.MovieService;
import com.acme.ex1.service.impl.MovieServiceImpl;

@Configuration @ComponentScan
public class ApplicationConfig {

	@Bean(name="service1")
	public MovieService service1(FoxMovieDaoImpl dao) {
		return new MovieServiceImpl(dao);
	}
	
	@Bean(name="service2")
	public MovieService service(WarnerMovieDaoImpl dao) {
		return new MovieServiceImpl(dao);
	}
	
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		System.out.println(ctx.getBeansOfType(MovieService.class));
		
	}
}
