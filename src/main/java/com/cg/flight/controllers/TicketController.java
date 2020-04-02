package com.cg.flight.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.Ticket;
import com.cg.flight.requests.TicketCancelRequest;
import com.cg.flight.responses.ErrorMessage;
import com.cg.flight.responses.GlobalResponse;
import com.cg.flight.services.IService;
import com.cg.flight.services.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
@RestController
@RequestMapping(value="/tickets")
public class TicketController {
	
	@Autowired
	private IService ticketService;
	
	@Autowired 
	private JwtUtil jwtService;
	
	@GetMapping(value="/all",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ticket>> getTickets(HttpServletRequest request)
	{
		String token = request.getHeader("Authorization");
		String username = jwtService.extractUsername(token.substring(7));
		return new ResponseEntity<List<Ticket>>(ticketService.getTickets(username),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/cancel",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancelTicket(HttpServletRequest request) throws IOException
	{
		String token = request.getHeader("Authorization");
		String username = jwtService.extractUsername(token.substring(7));
		Boolean status = false;
		try {
			
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		TicketCancelRequest ticketCancel =  new ObjectMapper().readValue(body, TicketCancelRequest.class);
		status = ticketService.cancelTicket(ticketCancel.getTicket_id(),username);
		}
		catch(IOException exception) {
			throw new IOException("Invalid Ticket Cancellation Request",exception);
		}
		if(status)
			return new ResponseEntity<Object>(new GlobalResponse("Cancel Ticket","Successfully Cancelled Ticket"),HttpStatus.ACCEPTED) ;
		else 
			return new ResponseEntity<Object>(new GlobalResponse("CancelTicket","Ticket Cancellation Failure"),HttpStatus.CONFLICT) ;
	}
	
	@ExceptionHandler({IOException.class})
	public ResponseEntity<Object> handleException(Exception exception) {
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.BAD_REQUEST,exception.getMessage()),HttpStatus.BAD_REQUEST);
	}
	
}
