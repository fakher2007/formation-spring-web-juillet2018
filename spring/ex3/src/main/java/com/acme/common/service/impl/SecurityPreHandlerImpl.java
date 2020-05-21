package com.acme.common.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.acme.common.service.AbstractCommand;
import com.acme.common.service.CommandPreHandler;
import com.acme.common.service.AbstractCommand.Usecase;

public class SecurityPreHandlerImpl implements CommandPreHandler {

	@Autowired
	private Logger logger;
	
	@Override
	public void beforeHandle(AbstractCommand command) {
		
		if(command.getClass().isAnnotationPresent(Usecase.class)){
			Usecase usecase = command.getClass().getAnnotation(Usecase.class);
			/*
			if(usecase.secured()){
				String ucName = usecase.name();
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if(authentication == null){
					throw new SecurityException();
				}
				logger.info("{} is secured, check if {} can process it", ucName, authentication.getName() );
				boolean hasAccess = authentication.getAuthorities()
						.stream()
						.map(x -> x.getAuthority())
						.anyMatch(x -> x.equals("*") || x.equals(ucName));
				if(!hasAccess){
					logger.warn("no, {} cannot process {}", authentication.getName(), ucName);
					throw new SecurityException();
				}
				else{
					logger.info("yes, {} can process {}", authentication.getName(), ucName);
				}
			}*/
		}
	}
}
