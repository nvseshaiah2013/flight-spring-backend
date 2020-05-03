package com.cg.flight.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IUserDao;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.NotFound;
import com.cg.flight.exceptions.UserRegistrationException;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.LoginResponse;


@Service
public class UserService implements IUserService {
	
	private static Logger logger  = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IUserDao userRepo;
	
	@Autowired
	private LoginUserService loginService;
	
	@Autowired 
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Override
	public User findById(String username) throws Exception{
		User user = userRepo.findById(username);
		if(user == null)
		{	
			logger.error("User with current Username Not Found.!");
			throw new NotFound("User with current Username Not Found.!");
		}
		return user;
	}
	
	@Override
	@Transactional
	public void registerUser(User user) throws UserRegistrationException {
		User tempUser = userRepo.findById(user.getUsername());

		if (tempUser != null)
			 {
			logger.error("Username with " + user.getUsername() + " already Exists");
			throw new UserRegistrationException(); }

		userRepo.addUser(user);
	}
	
	@Override
	public LoginResponse getAuthenticationToken(LoginRequest authenticationRequest) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			logger.error("Incorrect username or password for " + authenticationRequest.getUsername() );
			throw new Exception("Incorrect username or password");
		}

		final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails);
		User user = findById(authenticationRequest.getUsername());
		return new LoginResponse(jwt, user);
	}
}
