package com.cg.flight.dao;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;



public interface IFlightDao {
	String addUser(String name,String username,String password,int age);
	User authenticateUser(String username,String password);
	void init();
	List<Flight> getFlights(String source,String destination,String date);
	boolean bookFlight(Flight flight,User user);
	List<Ticket> getTickets(User user);
	boolean cancelTicket(Ticket ticket);
}
