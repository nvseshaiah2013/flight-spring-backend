package com.cg.flight.controllers;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;
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
	
//	@GetMapping(value="/a")
//	public ResponseEntity<String> getUsers()
//	{
//		System.out.println("ho");
//		return ResponseEntity.ok("Fetched");
//	}
//	
//	@PostMapping(value="/sample",produces=MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<String> getRespo()
//	{
//		System.out.println("ho");
//		return ResponseEntity.ok("{\"message\":\"Sample\"}");
//	}
	
	@PostMapping(value="/add",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@Valid @RequestBody User user)
	{
		System.out.println("Add");
		userService.registerUser(user);
		return new ResponseEntity<String>("{\"message\":\"Mission Successfull\"}",HttpStatus.CREATED);
	}
	
	@PostMapping(value="/authenticate",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

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

		return new ResponseEntity(new LoginResponse(jwt),HttpStatus.OK);
	}
	
//	
//	@RequestMapping(value="/**")
//	public ResponseEntity<String> notFound()
//	{
//		
//		return new ResponseEntity<String>("The Requested Page does Not Exist As of Now",HttpStatus.NOT_FOUND);
//	}
}
