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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.Passenger;
import com.cg.flight.responses.GlobalResponse;
import com.cg.flight.services.IPassengerService;
import com.cg.flight.services.JwtUtil;

@RestController
@CrossOrigin(value = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping(value = "/passengers")
public class PassengerController {

	static Logger logger = LoggerFactory.getLogger(PassengerController.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private IPassengerService passengerService;

	/*
	 * Function to Get the List of Passengers Searches for all passengers belonging
	 * the user identified by the username Can Throw UserNotFound Exception On
	 * Success returns a List of Passengers
	 */
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPassengers(HttpServletRequest request) throws Exception {
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		Set<Passenger> passengers = passengerService.getPassengers(username);
		logger.info("User requested with username: " + username + " with number of passengers " + passengers.size());
		return new ResponseEntity<Object>(passengers, HttpStatus.OK);
	}
	
	/*
	 * Function to Add a Passenger belonging to a certain User identified by Username.
	 * It can throw UserNotFound Exception, It can throw Invalid Passenger Exception
	 * On Success the passenger is added to database.
	 * */
	
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addPassenger(HttpServletRequest request, @Valid @RequestBody Passenger passenger)
			throws Exception {
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		this.passengerService.addPassenger(passenger, username);
		logger.info("User with username: " + username + " added Passenger with name " + passenger.getName());
		return new ResponseEntity<>(new GlobalResponse("Passenger Addition", "Passenger Added Successfully"),
				HttpStatus.CREATED);
	}

	/*
	 * Function to Update Passenger belonging to a certain User identified by username.
	 * It can throw Usernot Found Exception, It can throw Invalid Passenger Exception.
	 * On Success the passenger is updated to Database.
	 * */
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updatePassenger(HttpServletRequest request, @Valid @RequestBody Passenger passenger)
			throws Exception {
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		this.passengerService.updatePassenger(passenger, username, passenger.getPassenger_id());
		logger.info("User with username: " + username + " updated Passenger with name " + passenger.getName());
		return new ResponseEntity<>(new GlobalResponse("Passenger Updation", "Passenger Updated Successfully"),
				HttpStatus.ACCEPTED);
	}

	/*
	 * Function to Delete Passenger belonging to a certain User identified by username.
	 * It can throw UsernotFound Exception, It can throw Invalid Passenger Id Exception.
	 * On Success the passenger is deleted from Database.
	 * */
	
	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deletePassenger(HttpServletRequest request, @PathVariable("id") int id)
			throws Exception {
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		this.passengerService.deletePassenger(id, username);
		logger.info("User with username: " + username + " deleted Passenger with id " + id);
		return new ResponseEntity<>(new GlobalResponse("Passenger Deletion", "Passenger Deleted Successfully"),
				HttpStatus.ACCEPTED);
	}

}