package com.cg.flight.exceptions;

public class InvalidRequestException extends Exception{
    
    private static final long serialVersionUID = 1L;

    public InvalidRequestException() {
        super("The request resource is Invalid");
    }

    public InvalidRequestException(String message){
        super(message);
    }
}