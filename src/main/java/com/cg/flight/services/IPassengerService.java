package com.cg.flight.services;


import java.util.Set;

import com.cg.flight.entities.Passenger;

public interface IPassengerService {

	Passenger findPassengerById(int id) throws Exception;

	Set<Passenger> getPassengers(String username) throws Exception;

	void updatePassenger(Passenger passenger, String username, int id) throws Exception;

	void addPassenger(Passenger passenger, String username) throws Exception;

	void deletePassenger(int id, String username) throws Exception;
	

}
