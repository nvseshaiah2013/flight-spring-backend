package com.cg.flight.dao;

import java.util.Set;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;

public interface IPassengerDao {

	Passenger findPassengerById(int id);

	void updatePassenger(Passenger passenger, Passenger passenger_old) throws Exception;

	void addPassenger(Passenger passenger, User user);

	void deletePassenger(Passenger passenger) throws Exception;

	Set<Passenger> getPassengers(User user);
	
	
}
