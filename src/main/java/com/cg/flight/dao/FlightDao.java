package com.cg.flight.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;

@Repository
public class FlightDao implements IFlightDao {
	
	
	@Autowired
	public EntityManager entityManager;
	
	/*
	 * Function to Get the List of Flights
	 * Function Accepts Source as String, Destination as String, date as String
	 * Returns a List of Flights based on the Query combination of source,destination and date.
	 * */

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		List<Flight> flights;
		String qStr = "SELECT flight FROM Flight flight WHERE LOWER(source)=:source AND LOWER(destination)=:destination AND "
				+ "to_char(start_Date,'yyyy-mm-dd')= :date AND vacant_seats > 0";
		TypedQuery<Flight> query = entityManager.createQuery(qStr, Flight.class);
		query.setParameter("source", source.toLowerCase());
		query.setParameter("destination", destination.toLowerCase());
		query.setParameter("date", date);
		flights = query.getResultList();
		return flights;
	}

	@Override
	public Ticket bookFlight(Flight flight, User user, BookFlightRequest request) {
		try {

			Ticket ticket = new Ticket(flight, user, request.getName(), request.getAge(), request.getGender(),
					request.getIdType(), request.getIdNo());
			flight.setVacant_seats(flight.getVacant_seats() - 1);
			flight.addTicket(ticket);
			user.addTicket(ticket);
			entityManager.persist(ticket);
			return ticket;
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public Flight getFlightById(String flight_code) {
		return entityManager.find(Flight.class, flight_code);
	}
	
	@Override
	public List<String> getSources(){
		String qStr = "SELECT DISTINCT(flight.source) FROM Flight flight";
		TypedQuery<String> query = entityManager.createQuery(qStr, String.class);
		return query.getResultList();
	}
	
	@Override
	public List<String> getDestinations(){
	String qStr = "SELECT DISTINCT(flight.destination) FROM Flight flight";
	TypedQuery<String> query = entityManager.createQuery(qStr, String.class);
	return query.getResultList();
	}
}
