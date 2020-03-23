package com.cg.flight.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IFlightDao;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;


@Service
@Transactional
public class FlightService implements IFlightService{
	
	@Autowired
	public IFlightDao repo;
	
	@Override
	public void init() {
				
	}

	@Override
	public User userAuthenticate(String username, String password) {
		
		return null;
	}

	@Override
	public String registerUser(String name, String username, String password, int age) {
		
		return null;
	}

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		
		return null;
	}

	@Override
	public boolean cancelTicket(Ticket ticket) {
		
		return false;
	}

	@Override
	public boolean bookFlight(Flight flight, User user) {
		
		return false;
	}

	@Override
	public List<Ticket> getTickets(User user) {
		
		return null;
	}

}
