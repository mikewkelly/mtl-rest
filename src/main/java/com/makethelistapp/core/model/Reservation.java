package com.makethelistapp.core.model;

public class Reservation {
	
	int id;
	String firstName;
	String lastName;
	int freeCover;
	int halfCover;
	int numGuests;
	String note;
	int glistId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getFreeCover() {
		return freeCover;
	}
	public void setFreeCover(int freeCover) {
		this.freeCover = freeCover;
	}
	public int getHalfCover() {
		return halfCover;
	}
	public void setHalfCover(int halfCover) {
		this.halfCover = halfCover;
	}
	public int getNumGuests() {
		return numGuests;
	}
	public void setNumGuests(int numGuests) {
		this.numGuests = numGuests;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getGlistId() {
		return glistId;
	}
	public void setGlistId(int glistId) {
		this.glistId = glistId;
	}

}
