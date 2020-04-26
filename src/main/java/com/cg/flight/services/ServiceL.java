
package com.cg.flight.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IFlightDao;
import com.cg.flight.dao.TicketDao;
import com.cg.flight.dao.UserDao;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.NotFound;
import com.cg.flight.exceptions.UserRegistrationException;
import com.cg.flight.requests.BookFlightRequest;
import com.cg.flight.requests.LoginRequest;
import com.cg.flight.responses.LoginResponse;

@Service
public class ServiceL implements IService {

	@Autowired
	private IFlightDao flightRepo;

	@Autowired
	private UserDao userRepo;

	@Autowired
	private TicketDao ticketRepo;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private LoginUserService loginService;

	@Override
	public void init() {

	}

	@Override
	@Transactional
	public void registerUser(User user) throws UserRegistrationException {
		User tempUser = userRepo.findById(user.getUsername());

		if (tempUser != null)
			throw new UserRegistrationException();

		userRepo.addUser(user);
	}

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {

		return flightRepo.getFlights(source, destination, date);
	}

	@Override
	public User findById(String username) throws Exception{
		User user = userRepo.findById(username);
		if(user == null)
			throw new NotFound("User with current Username Not Found.!");
		return user;
	}

	@Override
	@Transactional
	public boolean cancelTicket(int ticketId, String username) {
		Ticket ticket = ticketRepo.findById(ticketId);
		if (ticket == null || !(ticket.getUser().getUsername().equals(username)))
			return false;
		else {
			return ticketRepo.cancelTicket(ticket);
		}
	}

	@Override
	@Transactional
	public Ticket bookFlight(BookFlightRequest request) {
		Flight flight = flightRepo.getFlightById(request.getFlight_code());
		if (flight.getVacant_seats() <= 0)
			return null;
		User user = userRepo.findById(request.getUsername());
		return bookSeat(flight, user, request);

	}

	@Transactional
	private Ticket bookSeat(Flight flight, User user, BookFlightRequest request) {
		return flightRepo.bookFlight(flight, user, request);
	}

	@Override
	public List<Ticket> getTickets(String username) {
		User user = userRepo.findById(username);
		return ticketRepo.getTickets(user);

	}

	@Override
	public List<String> getSources() {
		return flightRepo.getSources();
	}

	@Override
	public List<String> getDestinations() {
		return flightRepo.getDestinations();
	}

	@Override
	public LoginResponse getAuthenticationToken(LoginRequest authenticationRequest) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtUtil.generateToken(userDetails);
		User user = findById(authenticationRequest.getUsername());
		return new LoginResponse(jwt, user);
	}

	@Override
	public List<Passenger> getPassengers(String username ) {
		return userRepo.getPassengers(username);
	}

	@Override
	@Transactional
	public void updatePassenger(Passenger passenger,String username){
		userRepo.updatePassenger(passenger, username);
	}

	@Override
	@Transactional
	public void addPassenger(Passenger passenger, String username) throws Exception {
		User user = findById(username);
		if(user == null)
			throw new NotFound("User with given username Not Found");
		this.userRepo.addPassenger(passenger, user);

	}
}
