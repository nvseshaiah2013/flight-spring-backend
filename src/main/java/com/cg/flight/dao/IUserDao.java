package com.cg.flight.dao;

import com.cg.flight.entities.User;

public interface IUserDao {
	String addUser(String name,String username,String password,int age);
	User authenticateUser(String username,String password);
}
