package com.acme.ex4.endpoint;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.acme.common.business.CommandException;

@RestControllerAdvice
public class _ControllerAdvice {

	
    public static class ValidationError{
        public String field,error, message;
        public Object rejectedValue;

        public ValidationError(FieldError fieldError){
            this.field = fieldError.getField();
            this.error = fieldError.getCode();
            this.rejectedValue = fieldError.getRejectedValue();
            this.message = fieldError.getDefaultMessage();
        }
    }
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> otherException(Throwable t){
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> securityException(SecurityException e){
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
    }
    
    @ExceptionHandler(CommandException.class)
    public ResponseEntity<?> onCommandException(CommandException e){
	    return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handler1(MethodArgumentNotValidException exception){
	    BindingResult br = exception.getBindingResult();
	    List<ValidationError> errors = onValidationFailure(br);
	    return ResponseEntity.badRequest().body(errors);
    }
    
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handler2(BindException exception){
	    BindingResult br = exception.getBindingResult();
	    List<ValidationError> errors = onValidationFailure(br);
	    return ResponseEntity.badRequest().body(errors);
    }
    
    private List<ValidationError> onValidationFailure(BindingResult br){
        return br.getFieldErrors().stream()
                .map(fe -> new ValidationError(fe))
                .collect(Collectors.toList());
    }

}
