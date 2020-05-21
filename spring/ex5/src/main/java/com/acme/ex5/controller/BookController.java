package com.acme.ex5.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.acme.common.persistence.Results;
import com.acme.common.service.CommandProcessor;
import com.acme.ex3.business.filter.BookFilter;
import com.acme.ex3.model.entity.Book;
import com.acme.ex3.model.entity.Reservation;
import com.acme.ex3.service.command.FindBookCommand;
import com.acme.ex3.service.command.ReservationCommand;

import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class BookController {

	@Autowired
	private CommandProcessor processor;
	
	@GetMapping("books")
	public String list(Map<String, Object> model) {
		BookFilter filter = new BookFilter();
		model.put("filter", filter);
		return "books/list";
	}
	
	@GetMapping(path="books", params="title")
	public String list(@Valid @ModelAttribute("filter") BookFilter filter, BindingResult br, Map<String, Object> model) {
		if(br.hasErrors()) {
			return "books/list";
		}
		FindBookCommand cmd = new FindBookCommand();
		cmd.setFilter(filter);
		cmd = this.processor.process(cmd);
		Results<Book> results =  cmd.getMultipleResults();
		model.put("results", results);
		return "books/list";
	}
	
	@GetMapping("books/{id}")
	public String detail(@PathVariable int id, Map<String, Object> model) {
		FindBookCommand cmd = new FindBookCommand();
		cmd.setId(id);
		cmd = this.processor.process(cmd);
		Book book = cmd.getSingleResult();
		model.put("entity", book);
		model.put("reservation-command", new ReservationCommand());
		return "books/detail";
	}
	
	@PostMapping("reservations")
	public String borrow(@Valid @ModelAttribute("reservation-command") ReservationCommand command, BindingResult br, Authentication auth) {
		
		if(br.hasErrors() && !(br.hasFieldErrors("username") && br.getFieldErrorCount()==1) ) {
			return "books/detail";	
		}
		command.setUsername(auth.getName());
		System.out.println("ok");
		return "redirect:/books";
	}
}
