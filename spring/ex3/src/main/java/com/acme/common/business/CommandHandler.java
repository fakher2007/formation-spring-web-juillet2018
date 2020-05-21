package com.acme.common.business;

import java.util.ArrayList;
import java.util.List;

import com.acme.common.service.AbstractCommand;

public interface CommandHandler {


	static class HandlingContext{
		public final List<Runnable> afterCommit = new ArrayList<>();
	}


	void handle(AbstractCommand command, HandlingContext handlingContext );

}
