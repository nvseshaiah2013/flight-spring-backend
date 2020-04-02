package com.cg.flight.exceptions;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
}
