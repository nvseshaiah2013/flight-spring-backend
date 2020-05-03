package com.cg.flight.exceptions;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.flight.responses.ErrorMessage;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GlobalException.class);

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

        String result = "";
        Iterator<Map.Entry<String,String>> iterator = errors.entrySet().iterator();
        while(iterator.hasNext()) {
        	Map.Entry<String, String> entry = iterator.next();
        	result = result + entry.getKey() + "->" +entry.getValue() + "\n";
        }
        logger.error(result);
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.BAD_REQUEST,result),HttpStatus.BAD_REQUEST);

    }
	
    @ExceptionHandler({BadCredentialsException.class,InvalidRequestException.class,InvalidPassengerException.class})
	public ResponseEntity<Object> handleWrongPassword(Exception exception, WebRequest request){
    	logger.error(exception.getMessage());
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage()),HttpStatus.FORBIDDEN);
	}
    
	@ExceptionHandler({UserRegistrationException.class,JwtException.class,NoVacancyException.class})
	public ResponseEntity<Object> handleUserRegistration(Exception exception){
		logger.error(exception.getMessage());
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({NotFound.class})
    public ResponseEntity<Object> handleNotFound(NotFound exception){
    	logger.error(exception.getMessage());
        return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleServerException(Exception exception){
    	logger.error(exception.getMessage());
        return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()),
                HttpStatus.BAD_REQUEST);

    }
}
