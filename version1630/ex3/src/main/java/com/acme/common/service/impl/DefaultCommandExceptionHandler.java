package com.acme.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.common.service.AbstractCommand;
import com.acme.common.service.CommandExceptionHandler;

public class DefaultCommandExceptionHandler implements CommandExceptionHandler {

	private boolean enabled;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public void handleException(Exception ex, AbstractCommand command) {
		logger.info("inside handleException");
		if(enabled){
			logger.info("do exception handling for "+ex.getClass().getName());
			@SuppressWarnings("unused")
			String cmdId = command.toString();
			// serialisation de la commande dans un fichier sous le nom cmdId.xml
			// et écriture de la stacktrace dans un fichier sous le nom cmdId.stacktrace.txt
		}
		else{
			logger.info("do nothing for "+ex.getClass().getName());
		}

	}

}
