
package com.cg.flight.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IFlightDao;
import com.cg.flight.dao.TicketDao;
import com.cg.flight.dao.UserDao;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.requests.LoginRequest;


@Service
public class ServiceL implements IService{
	
	@Autowired
	private IFlightDao flightRepo;
	
	@Autowired
	private UserDao userRepo;
	
	@Autowired 
	private TicketDao ticketRepo;
	
	@Override
	public void init() {
				
	}

	@Override
	@Transactional
	public Boolean registerUser(User user) {
		return userRepo.addUser(user);
	}

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		
		return flightRepo.getFlights(source, destination, date);
	}

	@Override
	@Transactional
	public boolean cancelTicket(int ticketId,String username) {
		Ticket ticket = ticketRepo.findById(ticketId);
		if(ticket == null || !(ticket.getUser().getUsername().equals(username)))
				return false;
		else
		{
			return ticketRepo.cancelTicket(ticket);
		}
	}

	@Override
	@Transactional
	public boolean bookFlight(BookFlightRequest request) {
		Flight flight = flightRepo.getFlightById(request.getFlight_code());
		if(flight.getVacant_seats() <=0 )
			return false;
		User user = userRepo.findById(request.getUsername());
		return bookSeat(flight,user,request);		
		
	}
	
	@Transactional
	private boolean bookSeat(Flight flight,User user,BookFlightRequest request)
	{
		return flightRepo.bookFlight(flight, user,request);
	}
	
	@Override
	public List<Ticket> getTickets(String username) {
		User user = userRepo.findById(username);
		return ticketRepo.getTickets(user);
		
	}

}
