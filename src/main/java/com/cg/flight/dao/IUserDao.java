package com.cg.flight.dao;

import java.util.List;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;

public interface IUserDao {
	Boolean addUser(User user);
	User findById(String username);
	List<Passenger> getPassengers(String username);
	void updatePassenger(Passenger passenger,String username);
	void addPassenger(Passenger passenger,User username);
}
