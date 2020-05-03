package com.cg.flight.dao;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;



public interface IFlightDao {
	List<Flight> getFlights(String source,String destination,String date);
	Ticket bookFlight(Flight flight,User user,BookFlightRequest request) throws Exception;
	Flight getFlightById(String flight_code);
	List<String> getSources();
	List<String> getDestinations();
}
