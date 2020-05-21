package com.acme.ex5.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.acme.common.business.CommandException;

@ControllerAdvice
public class _MyControllerAdvice {
	
	// ajouter ici un ExceptionHandler pour Throwable.
	// celle ci doit recevoir en argument l'instance de la Throwable levée et renvoyer présenter la vue _errors/other-exception
	
	// ajouter ici un ExceptionHandler pour CommandException
	// celle ci doit recevoir en argument l'instance de la CommandException levée et renvoyer un ModelAndView qui présente la vue _errors/command-exception
	
	@ExceptionHandler(CommandException.class)
	public String onCommandException(CommandException e, Model model) {
		model.addAttribute("exception", e);
		return "/_errors/command-exception";
	}
}
