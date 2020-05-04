package com.cg.flight.dao;

import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;

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
	public void updatePassenger(Passenger passenger,Passenger passenger_old) throws Exception {
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
	public void deletePassenger(Passenger passenger) throws Exception {		
		entityManager.remove(passenger);
	}
}
