package com.cg.flight.exceptions;

public class NotFound extends Exception{

    private static final long serialVersionUID = 1L;

    public NotFound(){
        super();
    }

    public NotFound(String message){
        super(message);
    }
}