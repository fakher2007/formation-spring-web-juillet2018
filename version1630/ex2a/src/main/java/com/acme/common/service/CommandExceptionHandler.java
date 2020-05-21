package com.acme.common.service;

public interface CommandExceptionHandler {

	void handleException(AbstractCommand command, Exception e);
	
}
