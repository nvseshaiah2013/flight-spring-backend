package com.cg.flight.requests;

import org.springframework.stereotype.Component;

@Component
public class BookFlightRequest {
	private String username;
	private String flight_code;
	
	public BookFlightRequest()
	{
		
	}	
	public BookFlightRequest(String username, String flight_code) {
		this.username = username;
		this.flight_code = flight_code;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlight_code() {
		return flight_code;
	}
	public void setFlight_code(String flight_code) {
		this.flight_code = flight_code;
	}
	
	
}
