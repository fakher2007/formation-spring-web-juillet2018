package com.acme.common.service;

public interface CommandExceptionHandler {

	void handleException(Exception ex, AbstractCommand command);
}
