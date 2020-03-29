package com.cg.flight.services;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.requests.LoginRequest;




public interface IService {
	public void init();
	public Boolean registerUser(User user);
	public List<Flight> getFlights(String source,String destination,String date);
	public boolean cancelTicket(int ticketId,String username);
	public boolean bookFlight(BookFlightRequest flight);
	public List<Ticket> getTickets(String username);
}
