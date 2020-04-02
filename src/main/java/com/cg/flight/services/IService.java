package com.cg.flight.services;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;




public interface IService {
	public void init();
	public Boolean registerUser(User user);
	public List<Flight> getFlights(String source,String destination,String date);
	public boolean cancelTicket(int ticketId,String username);
	public Ticket bookFlight(BookFlightRequest flight);
	public List<Ticket> getTickets(String username);
	public User findById(String username);
}
