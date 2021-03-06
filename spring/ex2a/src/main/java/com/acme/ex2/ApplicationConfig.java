package com.acme.ex2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import com.acme.common.service.impl.CommandProcessorImpl;
import com.acme.ex2.aspect.HandlerPerformanceMonitor;

@Configuration
@ComponentScan 
@Import({HandlerPerformanceMonitor.class, CommandProcessorImpl.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableMBeanExport
public class ApplicationConfig {

	@Bean @Scope("prototype")
	public Logger logger(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}
	
}
