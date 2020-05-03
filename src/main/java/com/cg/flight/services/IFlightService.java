package com.cg.flight.services;

import java.util.List;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.requests.BookFlightRequest;

public interface IFlightService {

	List<Flight> getFlights(String source, String destination, String date);

	Flight findFlightById(String flight_code) throws Exception;

	Ticket bookFlight(BookFlightRequest request) throws Exception;

	List<String> getSources();

	List<String> getDestinations();

}
