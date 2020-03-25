package com.cg.flight.services;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;




public interface IService {
	public void init();
	public String userAuthenticate(LoginRequest request);
	public Boolean registerUser(User user);
	public List<Flight> getFlights(String source,String destination,String date);
	public boolean cancelTicket(Ticket ticket);
	public boolean bookFlight(Flight flight,User user);
	public List<Ticket> getTickets(User user);
}
