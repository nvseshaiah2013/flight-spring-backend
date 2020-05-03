package com.cg.flight.dao;

import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.NotFound;

@Repository
public class PassengerDao implements IPassengerDao{
	
	@Autowired	
	private EntityManager entityManager;
	
	
	@Override
	public Passenger findPassengerById(int id){
		Passenger passenger = entityManager.find(Passenger.class,id);
		return passenger;
	}

	@Override
	public Set<Passenger> getPassengers(User user) {
		Set<Passenger> passengers = user.getPassengers();
		return passengers;
	}

	@Override
	public void updatePassenger(Passenger passenger,Passenger passenger_old,String username) throws Exception {
		if(passenger_old == null || !passenger_old.getUser().getUsername().equals(username))
			throw new NotFound("Passenger Not Found for Given User");
		passenger_old.setAge(passenger.getAge());
		passenger_old.setIdNo(passenger.getIdNo());
		passenger_old.setIdType(passenger.getIdType());
		entityManager.merge(passenger_old);
		
	}

	@Override
	public void addPassenger(Passenger passenger,User user){
		user.addPassenger(passenger);
		entityManager.persist(passenger);
	}

	@Override
	public void deletePassenger(Passenger passenger,User user) throws Exception {
		if (passenger == null || !passenger.getUser().getUsername().equals(user.getUsername()))
			throw new NotFound("Passenger Not Found for Given User");		
		entityManager.remove(passenger);
	}
}
