package com.cg.flight.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.cg.flight.entities.User;
import com.cg.flight.requests.LoginRequest;

@Repository
public class UserDao implements IUserDao {
	
	@Autowired private EntityManager entityManager;

	@Override
	public Boolean addUser(User user) {
		System.out.println("H");
		String salt = BCrypt.gensalt(10);
		user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
		entityManager.persist(user);
		return true;
	}

	@Override
	public String authenticateUser(LoginRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
