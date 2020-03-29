package com.cg.flight.controllers;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.Flight;

import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.services.IService;
import com.cg.flight.services.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping(value="/flights")
public class FlightController {
	@Autowired
	private IService flightService;
	
	@Autowired
	private JwtUtil jwtService;
	
	@GetMapping(value="/all",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getFlights(@RequestParam("source") String source,
			@RequestParam("destination") String destination,
			@RequestParam("date") String date)
	{		
		List<Flight> flights = flightService.getFlights(source, destination, date);
		return new ResponseEntity(flights,HttpStatus.OK);
	}
	
	
	@PostMapping(value="/book",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity bookFlight(HttpServletRequest request) throws IOException
	{
		String token = request.getHeader("Authorization");
		String username = jwtService.extractUsername(token.substring(7));
	
		String list = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		BookFlightRequest bookRequest = new ObjectMapper().readValue(list, BookFlightRequest.class);
		bookRequest.setUsername(username);
		Boolean status = flightService.bookFlight(bookRequest);
		if(status)
			return new ResponseEntity("{\"message\":\"Success\"}",HttpStatus.ACCEPTED) ;
		else 
			return new ResponseEntity("{\"message\":\"Booking Failed\"}",HttpStatus.CONFLICT) ;
	
	}
	
	
}


