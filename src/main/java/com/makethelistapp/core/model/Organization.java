package com.makethelistapp.core.model;

import java.util.ArrayList;

public class Organization implements Model{
	
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
	
	public Organization(String name, String street, String city, String province, String country, String contactName, String contactPhone, String contactEmail, String status) {
		this.id = 0; //for auto-increment
		this.name = name;
		this.street = street;
		this.city = city;
		this.province = province;
		this.country = country;
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.status = status;
		
		//get id from db for new row:
		//call save() -> insert new row into db. 
		//get id of last inserted row from db
		//assign it to this.id
	}
	
	public boolean delete() {
		return false;
	}

	public boolean save() {
		return false;
	}
	
	public static Model getByPK(int primaryKey) {
		return null;
	}
	
	public static Model getByAttributes(ArrayList<String> attributes) {
		
		return null;
	}
}
