package com.cg.flight.services;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;




public interface IFlightService {
	public void init();
	public User userAuthenticate(String username,String password);
	public String registerUser(String name,String username,String password,int age);
	public List<Flight> getFlights(String source,String destination,String date);
	public boolean cancelTicket(Ticket ticket);
	public boolean bookFlight(Flight flight,User user);
	public List<Ticket> getTickets(User user);
}
