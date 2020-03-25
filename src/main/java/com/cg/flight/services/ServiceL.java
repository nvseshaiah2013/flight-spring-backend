
package com.cg.flight.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IFlightDao;
import com.cg.flight.dao.UserDao;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;


@Service
public class ServiceL implements IService{
	
	@Autowired
	public IFlightDao flightRepo;
	
	@Autowired
	public UserDao userRepo;
	
	@Override
	public void init() {
				
	}

	@Override
	public String userAuthenticate(LoginRequest request) {
		userRepo.authenticateUser(request);
		return "";
	}

	@Override
	@Transactional
	public Boolean registerUser(User user) {
		System.out.println("k");
		return userRepo.addUser(user);
	}

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		
		return null;
	}

	@Override
	@Transactional
	public boolean cancelTicket(Ticket ticket) {
		
		return false;
	}

	@Override
	@Transactional
	public boolean bookFlight(Flight flight, User user) {
		
		return false;
	}

	@Override
	public List<Ticket> getTickets(User user) {
		
		return null;
	}

}
