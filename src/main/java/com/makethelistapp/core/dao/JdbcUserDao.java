package com.makethelistapp.core.dao;

import com.makethelistapp.core.model.User;

public interface JdbcUserDao {

	public User getUserByUsername(String email);
	
	public User getUserById(int id);
	
}
