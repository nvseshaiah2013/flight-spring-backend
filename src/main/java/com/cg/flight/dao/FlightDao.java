package com.cg.flight.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;
import com.cg.flight.requests.BookFlightRequest;


@Repository
public class FlightDao  implements IFlightDao
{
	@Autowired 
	public EntityManager entityManager;
	@Override
	public String addUser(String name, String username, String password, int age) {
		System.out.println("Called D A");
		entityManager.persist(new User(name,username,password,age));
		System.out.println("Called D B");
		return null;
	}

	@Override
	public User authenticateUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		
	}

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		List<Flight> flights;
		String qStr = "SELECT flight FROM Flight flight WHERE source=:source AND destination=:destination AND "
				+ "to_char(start_Date,'yyyy-mm-dd')= :date AND vacant_seats > 0";
		TypedQuery<Flight> query = entityManager.createQuery(qStr,Flight.class);
		query.setParameter("source", source);
		query.setParameter("destination", destination);
		query.setParameter("date", date);
		flights = query.getResultList();
		return flights;
	}

	@Override
	public List<Ticket> getTickets(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean bookFlight(Flight flight, User user,BookFlightRequest request) {
		try {
			
		Ticket ticket = new Ticket(flight,user,request.getName(),request.getAge(),request.getGender(),request.getIdType(),request.getIdNo());
		flight.setVacant_seats(flight.getVacant_seats()-1);
		flight.addTicket(ticket);
		user.addTicket(ticket);
		entityManager.persist(ticket);
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean cancelTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Flight getFlightById(String flight_code)
	{
		return entityManager.find(Flight.class,flight_code);
	}
}
