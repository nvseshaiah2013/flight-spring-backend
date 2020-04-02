package com.cg.flight.responses;

public class GlobalResponse {
	private String header;
	private String message;
	
	public GlobalResponse() {
		
	}
	public GlobalResponse(String header,String message) {
		this.message = message;
		this.header = header;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
