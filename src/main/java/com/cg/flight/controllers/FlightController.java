package com.cg.flight.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.services.IFlightService;
import com.cg.flight.services.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping(value = "/flights")
public class FlightController {
	
	@Autowired
	private IFlightService flightService;

	static Logger logger = LoggerFactory.getLogger(FlightController.class);

	@Autowired
	private JwtUtil jwtService;
	
	/*
	 * Function to Get List of Flights Based on The source,destination and Date.
	 * On Success function returns the list of Flights.
	 * */

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getFlights(@RequestParam("source") String source,
			@RequestParam("destination") String destination, @RequestParam("date") String date) {
		logger.info(
				"User requested For Flights of :" + source + " to: " + destination + " for Date : " + date);
		List<Flight> flights = flightService.getFlights(source, destination, date);
		logger.info("User requested For Flights of : " + source + " Destination: " + destination + " for Date : " + date
				+ " returned " + flights.size() + " results.");
		return new ResponseEntity<Object>(flights, HttpStatus.OK);
	}

	/*
	 * Function to Get List of Source cities.
	 * On Success function returns the list of Source Cities..
	 * */
	@GetMapping(value = "/sources", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getSources() {
		logger.info("User requested For Source City Names");
		return new ResponseEntity<>(flightService.getSources(), HttpStatus.OK);
	}
	
	/*
	 * Function to Get List of Destination cities.
	 * On Success function returns the list of Destination Cities..
	 * */

	@GetMapping(value = "/destinations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> getDestinations() {
		logger.info("User requested For Destination City Names");
		return new ResponseEntity<>(flightService.getDestinations(), HttpStatus.OK);
	}
	
	/*
	 * Function to Book Flight Ticket.
	 * Function Can Throw UserNotFoundException, NoVacancyException. InvalidPassengerException, FlightNotFoundException.
	 * On Success function Returns a Confirmed Ticket.
	 * */

	@PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> bookFlight(HttpServletRequest request,
			@Valid @RequestBody BookFlightRequest bookRequest) throws Exception {
		String token = request.getHeader("Authorization");
		String username = jwtService.extractUsername(token.substring(7));
		bookRequest.setUsername(username);
		logger.info("User with " + username + " requested to book Flight " + bookRequest.getFlight_code()
				+ " for Passenger " + bookRequest.getName());
		Ticket ticket = flightService.bookFlight(bookRequest);
		logger.info("User booked ticket with Id: " + ticket.getTicket_id() + " Source City: "
				+ ticket.getFlight().getSource() + " Destination City: " + ticket.getFlight().getDestination()
				+ " with Flight Code " + ticket.getFlight().getFlight_code());
		return new ResponseEntity<Object>(ticket, HttpStatus.ACCEPTED);
	}
}
