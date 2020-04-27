package com.cg.flight.dao;

import java.util.List;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;

public interface IUserDao {
	Boolean addUser(User user);
	User findById(String username);
	List<Passenger> getPassengers(String username);
	void updatePassenger(Passenger passenger,Passenger passenger_old,String username) throws Exception;
	void addPassenger(Passenger passenger,User username);
	void deletePassenger(Passenger passenger,User user) throws Exception;
}
