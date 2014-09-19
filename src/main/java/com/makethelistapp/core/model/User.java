package com.makethelistapp.core.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class User extends Model{
	int id;
	String firstName;
	String lastName;
	String email;
	String city;
	String province;
	String country;
	String pCode;
	String phone;
	Date birthdate;
	Timestamp lastActive;
	String userStatus;
	String username;
	String password;
	
	public User() {
		
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public static Model getByPK(int primaryKey) {
	
		return null;
	}

	public static Model getByAttributes(ArrayList<String> attributes) {
		
		return null;
	}

}
