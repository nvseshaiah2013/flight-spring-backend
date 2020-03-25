package com.cg.flight.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Flight;
import com.cg.flight.entities.Ticket;
import com.cg.flight.entities.User;


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
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Flight> getFlights(String source, String destination, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ticket> getTickets(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean bookFlight(Flight flight, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancelTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return false;
	}

}
