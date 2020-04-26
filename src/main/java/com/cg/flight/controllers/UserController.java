package com.cg.flight.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.GlobalResponse;
import com.cg.flight.responses.LoginResponse;
import com.cg.flight.services.IService;
import com.cg.flight.services.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired 
	private IService userService;
	
	/*
	 * Function To Register A New User to the Database.
	 * Can throw an UserRegistrationException 
	 * in case the username provided already exists in the database.
	 * Throws an IllegalStateException if the User object provided by Client fails the validation.
	 * Returns a success status response entity on successful addition of User with a CREATED status.
	 * */
	
	@PostMapping(value="/add",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user) throws Exception
	{
		userService.registerUser(user);
		return new ResponseEntity<Object>(new GlobalResponse("Register User","User Registered Successfully"),HttpStatus.CREATED);		
	}
	
	/*
	 * Function to Authenticate an Existing User.
	 * Can Throw an BadCredentialsException if the username and password combination is invalid.
	 * Throws and IllegalStateException if the LoginRequest provided by client fails validation.
	 * Returns Json Web Token and User Object as Response with the OK Status.
	 * */
	
	@PostMapping(value="/authenticate",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createAuthenticationToken(@Valid @RequestBody LoginRequest authenticationRequest) throws Exception {
		LoginResponse loginresponse = userService.getAuthenticationToken(authenticationRequest);
		loginresponse.getUser().setPassword("password");
		return new ResponseEntity<Object>(loginresponse,HttpStatus.OK);
	}
	
	
	/*
	 * Function to Get an User Object.
	 * Searches for user based on the Bearer Token.
	 * Can Throw an UserNotFound Exception 
	 * On success returns an User Entity Object.
	 * */
	
	
	@GetMapping(value="/getUser",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserByToken(HttpServletRequest request) throws Exception
	{
		final String token = request.getHeader("Authorization");			
		final String username = jwtUtil.extractUsername(token.substring(7));
		User user = userService.findById(username);
		user.setPassword("password");
		return new ResponseEntity<Object>(user,HttpStatus.OK);
		
	}

	/*
		Function to Get the List of Passengers
		Searches for all passengers belonging the user identified by the username
		Can Throw UserNotFound Exception
		On Success returns a List of Passengers
	*/
	@GetMapping(value="/passengers",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getPassengers(HttpServletRequest request){
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		List<Passenger> passengers = userService.getPassengers(username);
		return new ResponseEntity<Object>(passengers,HttpStatus.OK);
	}

	@PostMapping(value="/passengers",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addPassenger(HttpServletRequest request, @Valid @RequestBody Passenger passenger) throws Exception{
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		this.userService.addPassenger(passenger, username);
		return ResponseEntity.ok().build();
	}

	@PutMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateUser(HttpServletRequest request,@Valid @RequestBody Passenger passenger){
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		this.userService.updatePassenger(passenger,username);
		return ResponseEntity.ok().build();
	}
	
}