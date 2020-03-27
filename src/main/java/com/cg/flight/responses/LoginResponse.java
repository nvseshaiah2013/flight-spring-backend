package com.cg.flight.responses;

import org.springframework.stereotype.Component;


public class LoginResponse {
	private String jwt;
	public LoginResponse(String jwt)
	{
		this.jwt = jwt;
	}
	public LoginResponse()
	{
		
	}
	
	public String getJwt()
	{
		return jwt;
	}
}
