package com.makethelistapp.core.model;

import java.util.ArrayList;

public class Organization extends Model{
	
	int id;
	String name;
	String street;
	String city;
	String province;
	String country;
	String contactName;
	String contactPhone;
	String contactEmail;
	String status;
	
	public Organization() {
		
	}
	
	boolean delete() {
		return false;
	}

	boolean save() {
		return false;
	}
	
	public static Model getByPK(int primaryKey) {
		return null;
	}
	
	public static Model getByAttributes(ArrayList<String> attributes) {
		
		return null;
	}
}
