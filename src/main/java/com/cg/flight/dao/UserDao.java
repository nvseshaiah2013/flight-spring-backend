package com.cg.flight.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;
import com.cg.flight.exceptions.NotFound;

@Repository
public class UserDao implements IUserDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Boolean addUser(User user) {
		String salt = BCrypt.gensalt(10);
		user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
		Passenger passenger = new Passenger("PAN", "123456", user.getName(), user.getAge(), user.getGender(),0);
		user.addPassenger(passenger);
		entityManager.persist(user);
		return true;
	}

	@Override
	public User findById(String username) {
		User user = entityManager.find(User.class, username);
		return user;
	}

	public Passenger findPassengerById(int id){
		Passenger passenger = entityManager.find(Passenger.class,id);
		return passenger;
	}

	@Override
	public List<Passenger> getPassengers(String username) {
		String qString = "SELECT passenger FROM Passenger passenger WHERE username=:username";
		TypedQuery<Passenger> query = entityManager.createQuery(qString, Passenger.class);
		query.setParameter("username",username);
		List<Passenger> passengers = query.getResultList();
		return passengers;
	}

	@Override
	public void updatePassenger(Passenger passenger,Passenger passenger_old,String username) throws Exception {
		if(passenger_old == null || !passenger_old.getUser().getUsername().equals(username))
			throw new NotFound("Passenger Not Found for Given User");
		passenger_old.setAge(passenger.getAge());
		passenger_old.setIdNo(passenger.getIdNo());
		passenger_old.setIdType(passenger.getIdType());
		passenger_old.setIsValid(passenger.getIsValid());
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
