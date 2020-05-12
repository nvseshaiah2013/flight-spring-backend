package com.cg.flight.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flight.dao.IFlightDao;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.InvalidPassengerException;
import com.cg.flight.exceptions.NoVacancyException;
import com.cg.flight.exceptions.NotFound;
import com.cg.flight.requests.BookFlightRequest;

@Service
public class FlightService implements IFlightService {

	private static Logger logger = LoggerFactory.getLogger(FlightService.class);

	@Autowired
	private IFlightDao flightRepo;

	@Autowired
	private IUserService userService;
	

	/**
	 * Function to Get List of Flights Based on The source,destination and Date.
	 * On Success function returns the list of Flights.
	 * */

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		logger.info("Flights requested for " + source + " to " + destination + " " + date);
		return flightRepo.getFlights(source, destination, date);
	}
	
	
	/**
	 * Function to Find Flight using flight_code
	 * Can throw Flight NotFound exception 
	 * On success returns Flight Object	 
	 * 
	 * */

	@Override
	public Flight findFlightById(String flight_code) throws Exception {
		Flight flight = this.flightRepo.getFlightById(flight_code);
		if (flight == null) {
			logger.error("Flight with flight_code " + flight_code + " could not be Found!");
			throw new NotFound("Flight By Given Id Not Found");
		}
		return flight;
	}
	
	
	/**
	 * Function to Book Flight Ticket.
	 * Function Can Throw UserNotFoundException, NoVacancyException. InvalidPassengerException, FlightNotFoundException.
	 * On Success function Returns a Confirmed Ticket.
	 * */

	@Override
	@Transactional
	public Ticket bookFlight(BookFlightRequest request) throws Exception {
		Flight flight = this.findFlightById(request.getFlight_code());
		if (flight.getVacant_seats() <= 0) {
			logger.error("No Vacant Seats in Flight " + flight.getFlight_name() + " " + " Flight Code: "
					+ flight.getFlight_code());
			throw new NoVacancyException(
					"The flight " + flight.getFlight_code() + " " + flight.getFlight_name() + " has no vacant seats");
		}
		if (request.getIdType().equals("PAN")) {
			if (!request.getIdNo().matches("[A-Z]{5}[0-9]{4}[A-Z]")) {
				logger.error("PAN No." + request.getIdNo() + " is Invalid. Permitted Format: ABCDE1234A");
				throw new InvalidPassengerException("PAN No. is Invalid. Permitted Format: ABCDE1234A");
			}
		} else if (request.getIdType().equals("Aadhar")) {
			if (!request.getIdNo().matches("[2-9][0-9]{11}")) {
				logger.error("Aadhar No. " + request.getIdNo() + " is Invalid. Permitted Format: 201234567890");
				throw new InvalidPassengerException("Aadhar No. is Invalid. Permitted Format: 201234567890");
			}
		} else if (request.getIdType().equals("DL")) {
			if (!request.getIdNo().matches(
					"((A[NPRS])|(BR)|(C[GH])|(D[LD])|(G[AJ])|(H[RP])|(J[KH])|(K[LA])|"
					+ "(L[AD])|(M[PHNLZ])|(NL)|(OD)|(P[BY])|(RJ)|(SK)|(T[NSR])|"
					+ "(U[PK])|(WB))(\\d{1}[1-9])(20(([0-1][0-9])|(20)))(\\d{6}[1-9])"))

			{
				logger.error("Driving License " + request.getIdNo() + " is Invalid. Permitted Format: MH0220201234567");
				throw new InvalidPassengerException("Driving License is Invalid. Permitted Format: MH0220201234567");
			}
		} else if (request.getIdType().equals("Passport")) {
			if (!request.getIdNo().matches("[A-Z][0-9]{7}")) {
				logger.error("Passport is " + request.getIdNo() + "Invalid. Permitted Format: A1234567 ");
				throw new InvalidPassengerException("Passport is Invalid. Permitted Format: A1234567 ");
			}
		}
		User user = this.userService.findById(request.getUsername());
		return bookSeat(flight, user, request);

	}

	@Transactional
	private Ticket bookSeat(Flight flight, User user, BookFlightRequest request) throws Exception {
		logger.info("Seat Booking started for " + user.getUsername() + " for flight " + flight.getFlight_code());
		return flightRepo.bookFlight(flight, user, request);
	}

	/**
	 * Function to Get List of Source cities.
	 * On Success function returns the list of Source Cities..
	 * */
	
	
	@Override
	public List<String> getSources() {
		return flightRepo.getSources();
	}
	
	
	/**
	 * Function to Get List of Destination cities.
	 * On Success function returns the list of Destination Cities..
	 * */

	@Override
	public List<String> getDestinations() {
		return flightRepo.getDestinations();
	}
}
