package com.acme.common.business;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.acme.common.service.AbstractCommand;


public interface CommandHandler {

	public void handle(AbstractCommand command);
	
	@Retention(RetentionPolicy.RUNTIME)
	@Component
	@Lazy
	public @interface Handler{
		String value() default "";
	}
}
