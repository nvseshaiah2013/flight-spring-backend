package com.cg.flight.services;

import java.util.List;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;

public interface IService {
	void init();
	Boolean registerUser(User user);
	List<Flight> getFlights(String source,String destination,String date);
	boolean cancelTicket(int ticketId,String username);
	Ticket bookFlight(BookFlightRequest flight);
	List<Ticket> getTickets(String username);
	User findById(String username);
	List<String> getSources();
	List<String> getDestinations();
}
