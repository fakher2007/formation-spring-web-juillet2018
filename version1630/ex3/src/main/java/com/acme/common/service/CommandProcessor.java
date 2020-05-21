package com.acme.common.service;

import java.util.concurrent.CompletableFuture;

public interface CommandProcessor {

	<T extends AbstractCommand> CompletableFuture<T> processAsync(T command);
	
	<T extends AbstractCommand> T process(T command);

}
