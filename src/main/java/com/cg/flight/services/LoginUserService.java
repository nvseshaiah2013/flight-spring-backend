package com.cg.flight.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cg.flight.dao.UserDao;


@Service
public class LoginUserService implements UserDetailsService {

	@Autowired private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.cg.flight.entities.User user = userDao.findById(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException(username);
		}
		else			
			return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
	}

}
