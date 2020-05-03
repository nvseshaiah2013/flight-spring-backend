package com.cg.flight.exceptions;

public class InvalidPassengerException extends Exception {

	private static final long serialVersionUID = 1L;

	 public InvalidPassengerException() {
	        super("The resource received is invalid");
	    }

	    public InvalidPassengerException(String message){
	        super(message);
	    }
}
