package com.cg.flight.dao;

import org.springframework.data.repository.CrudRepository;

import com.cg.flight.entities.User;

public interface UserDaoJpa extends CrudRepository<User, String> {

}
