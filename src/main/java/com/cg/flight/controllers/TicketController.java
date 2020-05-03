package com.cg.flight.controllers;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.Ticket;
import com.cg.flight.requests.TicketCancelRequest;
import com.cg.flight.responses.GlobalResponse;
import com.cg.flight.services.ITicketService;
import com.cg.flight.services.JwtUtil;


@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*")
@RestController
@RequestMapping(value="/tickets")
public class TicketController {
	
	@Autowired
	private ITicketService ticketService;
	
	static Logger logger = LoggerFactory.getLogger(TicketController.class);
	
	@Autowired 
	private JwtUtil jwtService;
	
	/*
	 * Function to Get List of Tickets owned by user identified by username.
	 * Can Throw UserNotFoundException
	 * On Success Returns list of Tickets.
	 * */

	@GetMapping(value="/all",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Ticket>> getTickets(HttpServletRequest request) throws Exception
	{
		String token = request.getHeader("Authorization");
		String username = jwtService.extractUsername(token.substring(7));
		logger.info("User with username: " + username + " requested list of tickets booked by him/her" );
		return new ResponseEntity<Set<Ticket>>(ticketService.getTickets(username),HttpStatus.OK);
		
	}
	
	/*
	 * Function To Cancel Ticket owned by User identified By username.
	 * Can Throw Invalid TicketException, Or TicketNotFoundException.
	 * On Success the ticket gets Cancelled.
	 * */
	
	@PostMapping(value="/cancel",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancelTicket(HttpServletRequest request,@Valid @RequestBody TicketCancelRequest ticketCancel) throws Exception
	{
		String token = request.getHeader("Authorization");
		String username = jwtService.extractUsername(token.substring(7));
		ticketService.cancelTicket(ticketCancel.getTicket_id(),username);
		logger.info("User with username " + username + " requested to cancel ticket with id: " + + ticketCancel.getTicket_id());
		return new ResponseEntity<Object>(new GlobalResponse("Cancel Ticket","Successfully Cancelled Ticket"),HttpStatus.ACCEPTED) ;
	}	
}
