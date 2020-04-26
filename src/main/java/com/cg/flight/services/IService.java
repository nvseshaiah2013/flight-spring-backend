package com.cg.flight.services;

import java.util.List;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.LoginResponse;

public interface IService {
	
	void init();
	
	void registerUser(User user) throws Exception;	
	
	List<Flight> getFlights(String source,String destination,String date);

	void updatePassenger(Passenger passenger,String username);

	List<Passenger> getPassengers(String username );
	
	boolean cancelTicket(int ticketId,String username);
	
	Ticket bookFlight(BookFlightRequest flight);
	
	List<Ticket> getTickets(String username);
	
	User findById(String username) throws Exception;
	
	List<String> getSources();
	
	List<String> getDestinations();
	
	LoginResponse getAuthenticationToken(LoginRequest authenticationRequest) throws Exception;

	void addPassenger(Passenger passenger, String username) throws Exception;
}
