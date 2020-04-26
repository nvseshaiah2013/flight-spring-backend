package com.cg.flight.exceptions;

public class UserRegistrationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UserRegistrationException()
	{
		super("Username already exists in our System");
	}
	

}
