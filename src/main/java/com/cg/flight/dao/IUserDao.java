package com.cg.flight.dao;

import com.cg.flight.entities.User;

public interface IUserDao {
	
	Boolean addUser(User user);
	
	User findById(String username);
}
