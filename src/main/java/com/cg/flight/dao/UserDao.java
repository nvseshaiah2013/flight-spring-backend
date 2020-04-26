package com.cg.flight.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.Passenger;
import com.cg.flight.entities.User;

@Repository
public class UserDao implements IUserDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public Boolean addUser(User user) {
		String salt = BCrypt.gensalt(10);
		user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
		Passenger passenger = new Passenger("PAN", "123456", user.getName(), user.getAge(), user.getGender());
		user.addPassenger(passenger);
		entityManager.persist(user);
		return true;
	}

	@Override
	public User findById(String username) {
		User user = entityManager.find(User.class, username);
		return user;
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
	public void updatePassenger(Passenger passenger,String username) {
		String qString = "SELECT passenger FROM Passenger passenger WHERE passenger_id=:passenger_id AND username=:username";
		TypedQuery<Passenger> query = entityManager.createQuery(qString,Passenger.class);
		query.setParameter("passenger_id",passenger.getPassenger_id());
		query.setParameter("username",username);
		Passenger passenger_old = query.getSingleResult();
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
}
