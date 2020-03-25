package com.cg.flight.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.services.IService;
import com.cg.flight.services.JwtUtil;
import com.cg.flight.services.LoginUserService;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private LoginUserService loginService;
	
	@Autowired 
	private IService service;
	
	@GetMapping(value="/")
	public ResponseEntity<String> getUsers()
	{
		return ResponseEntity.ok("Fetched");
	}
	
	@PostMapping(value="/sample")
	public ResponseEntity<String> getRespo()
	{
		return ResponseEntity.ok("Sample");
	}
	
	@PostMapping(value="/add")
	public ResponseEntity<String> addUser(@Valid @RequestBody User user)
	{
		System.out.println("Add");
		service.registerUser(user);
		return ResponseEntity.ok("User Registered Successfully");
	}
	
	@PostMapping(value="/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

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

		return ResponseEntity.ok(jwt);
	}
	
	
	@RequestMapping(value="/**")
	public ResponseEntity<String> notFound()
	{
		
		return new ResponseEntity<String>("The Requested Page does Not Exist As of Now",HttpStatus.NOT_FOUND);
	}
}
