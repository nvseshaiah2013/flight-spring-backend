package com.cg.flight.controllers;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.ErrorMessage;
import com.cg.flight.responses.GlobalResponse;
import com.cg.flight.responses.LoginResponse;
import com.cg.flight.services.IService;
import com.cg.flight.services.JwtUtil;
import com.cg.flight.services.LoginUserService;

@RestController

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private LoginUserService loginService;
	
	@Autowired 
	private IService userService;
	
	@PostMapping(value="/add",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user)
	{
		System.out.println("Add");
		Boolean status = userService.registerUser(user);
		if(status)
		{
			return new ResponseEntity<Object>(new GlobalResponse("Register User","User Registered Successfully"),HttpStatus.CREATED);
		}
		else {			
			return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.UNAUTHORIZED,"Username already Exists"),HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping(value="/authenticate",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = loginService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails);
		User user = userService.findById(authenticationRequest.getUsername());

		return new ResponseEntity<Object>(new LoginResponse(jwt,user),HttpStatus.OK);
	}
	
	@PostMapping(value="/getUser",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserByToken(HttpServletRequest request)
	{
		final String token = request.getHeader("Authorization");
		final String username = jwtUtil.extractUsername(token.substring(7));
		User user = userService.findById(username);
		return new ResponseEntity<Object>(user,HttpStatus.OK);
		
	}
	
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<Object> handleException(Exception e) {
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.FORBIDDEN,e.getLocalizedMessage()),HttpStatus.FORBIDDEN);
	}

}