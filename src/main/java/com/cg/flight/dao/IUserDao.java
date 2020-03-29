package com.cg.flight.dao;

import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;

public interface IUserDao {
	Boolean addUser(User user);
	String authenticateUser(LoginRequest request);
	User findById(String username);
}
