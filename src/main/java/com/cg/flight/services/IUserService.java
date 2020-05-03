package com.cg.flight.services;

import com.cg.flight.entities.User;
import com.cg.flight.exceptions.UserRegistrationException;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.LoginResponse;

public interface IUserService {

	User findById(String username) throws Exception;

	void registerUser(User user) throws UserRegistrationException;

	LoginResponse getAuthenticationToken(LoginRequest authenticationRequest) throws Exception;

}
