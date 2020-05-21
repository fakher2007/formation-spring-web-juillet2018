package com.acme.ex3;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.acme.common.persistence.impl.JpaObjectStoreImpl;
import com.acme.common.service.impl.CommandProcessorImpl;

@Configuration 
@ComponentScan // pour les 3 handlers 
@Import({JpaObjectStoreImpl.class, CommandProcessorImpl.class})
@EnableTransactionManagement(proxyTargetClass=true)
@EnableMBeanExport
public class ApplicationConfig {

	@Bean @Scope("prototype")
	public Logger logger(InjectionPoint ip) {
		return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
	}
	
	@Bean
	public EntityManagerFactory emf() {
		return Persistence.createEntityManagerFactory("hib");
	}
	
	@Bean
	public PlatformTransactionManager txManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	@Component
	public static class MyBean{
		@PersistenceContext
		private EntityManager em;
		
		@Transactional(propagation=Propagation.REQUIRED)
		public void doSomething() {
			System.out.println("do something inside tx");
			TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
				@Override
				public void afterCommit() {
					System.out.println("send email");
				}
			});
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		MyBean bean = ctx.getBean(MyBean.class);
		System.out.println(bean);
		bean.doSomething();
	}
	
}
