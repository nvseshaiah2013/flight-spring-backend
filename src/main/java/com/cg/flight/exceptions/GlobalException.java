package com.cg.flight.exceptions;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.flight.responses.ErrorMessage;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        Map<String,String> errors = new HashMap<>();
        
        ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .forEach(error->{

         	if(!errors.containsKey(error.getField()))
        	{
        		errors.put(error.getField(), error.getDefaultMessage());   
        	}
        });

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);

    }
	
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<Object> handleWrongPassword(BadCredentialsException exception, WebRequest request){
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage()),HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler({UserRegistrationException.class,JwtException.class})
	public ResponseEntity<Object> handleUserRegistration(UserRegistrationException exception){
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({NotFound.class})
    public ResponseEntity<Object> handleNotFound(NotFound exception){
        return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage()),HttpStatus.NOT_FOUND);
    }
}
