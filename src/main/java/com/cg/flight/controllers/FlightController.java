package com.cg.flight.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.services.IService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value="/flights")
public class FlightController {
	@Autowired
	IService flightService;
	
	
	@GetMapping(value="/",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> home()
	{
		System.out.println("Protected");
		return new ResponseEntity<String>("{\"message\":\"Route Successfull!\"}",HttpStatus.ACCEPTED);
	}

}
