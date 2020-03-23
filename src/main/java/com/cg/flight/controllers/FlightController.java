package com.cg.flight.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.services.IFlightService;

@RestController
public class FlightController {
	@Autowired
	IFlightService flightService;
	
	
	@GetMapping(value="/")
	public ResponseEntity<String> home()
	{
		return new ResponseEntity<String>("Route Successfull!",HttpStatus.ACCEPTED);
	}

}
