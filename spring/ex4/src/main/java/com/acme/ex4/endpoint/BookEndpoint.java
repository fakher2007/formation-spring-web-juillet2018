package com.acme.ex4.endpoint;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.common.service.CommandProcessor;
import com.acme.ex3.business.filter.BookFilter;
import com.acme.ex3.model.entity.Book;
import com.acme.ex3.service.command.FindBookCommand;
import com.acme.ex4.dto.BookDto;

@RestController
public class BookEndpoint {

	@Autowired
	private CommandProcessor processor;
	
	@RequestMapping(path="books", method=RequestMethod.GET)
	public List<BookDto> list(@RequestParam String title){
		FindBookCommand cmd = new FindBookCommand();
	    BookFilter filter = new BookFilter();
	    filter.setTitle(title);
	    cmd.setFilter(filter);
	    cmd = this.processor.process(cmd);
	    List<Book> results =  cmd.getMultipleResults().getItems();
	    return results.stream().map(b -> new BookDto(b, false)).collect(Collectors.toList());
	}
	
	@RequestMapping(path="books_", method=RequestMethod.GET)
	public List<BookDto> list(@Valid BookFilter filter){
		
	    FindBookCommand cmd = new FindBookCommand();
	    cmd.setFilter(filter);
	    cmd = this.processor.process(cmd);
	    List<Book> results =  cmd.getMultipleResults().getItems();
	    return results.stream().map(b -> new BookDto(b, false)).collect(Collectors.toList());
	}
	
	@RequestMapping(path="searches", method=RequestMethod.POST, params="domain=books")
	@CrossOrigin()
	public List<BookDto> search(@RequestBody @Valid BookFilter filter){
		
	    FindBookCommand cmd = new FindBookCommand();
	    cmd.setFilter(filter);
	    cmd = this.processor.process(cmd);
	    List<Book> results =  cmd.getMultipleResults().getItems();
	    return results.stream().map(b -> new BookDto(b, false)).collect(Collectors.toList());
	}
	
	// books/128
	@RequestMapping(path="books/{id}", method=RequestMethod.GET)
	public ResponseEntity<BookDto> book(@PathVariable int id)
	{
	    FindBookCommand cmd = new FindBookCommand();
	    cmd.setId(id);
	    cmd = this.processor.process(cmd);
	    Book result =  cmd.getSingleResult();
	    return Optional.ofNullable(result)
	    		.map(b -> ResponseEntity.ok(new BookDto(b, true)))
	    		.orElse(ResponseEntity.notFound().build());
	    /*
	    if(result==null) {
	    	return ResponseEntity.notFound().build();
	    }
	    else {
	    	return ResponseEntity.ok(new BookDto(result, true));
	    }*/
 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
