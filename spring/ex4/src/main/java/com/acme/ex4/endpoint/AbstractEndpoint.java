package com.acme.ex4.endpoint;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractEndpoint {

    protected <T> ResponseEntity<T> ok(T body){
        return ResponseEntity.ok(body);
    }

    protected <T> ResponseEntity<T> created(T body, URI newResourceURi){
        return ResponseEntity.created(newResourceURi).body(body);
    }

    protected ResponseEntity<Void> badRequest(){
    	return badRequest(null);
    }

    protected <T> ResponseEntity<T> badRequest(T body){
        return ResponseEntity.badRequest().body(body);
    }

    protected ResponseEntity<Void> notAuthorized(){
        return notAuthorized(null);
    }

    protected <T> ResponseEntity<T> notAuthorized(T body){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    protected ResponseEntity<Void> forbidden(){
        return forbidden(null);
    }

    protected <T> ResponseEntity<T> forbidden(T body){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @Autowired
    private HttpServletRequest currentRequest;
    
    protected ResponseEntity<Void> notFound(){
    	System.out.println("il y eu une erreur 404 sur "+currentRequest.getRequestURI());
        return ResponseEntity.notFound().build();
    }

    protected ResponseEntity<Void> conflict(){
        return conflict(null);
    }

    protected <T> ResponseEntity<T> conflict(T body){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
