package com.makethelistapp.core.model;

import java.sql.Timestamp;

public class Event {
	
	//Event Table
	int id;
	Timestamp start;
	Timestamp end;
	String status;
	int venueId;
	//fk refs eventTemplateId
	
	//EventTemplate Table
	int eventTemplateId;
	String name;
	String recurring;
	String eventDay;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public String getRecurring() {
		return recurring;
	}
	public void setRecurring(String recurring) {
		this.recurring = recurring;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getVenueId() {
		return venueId;
	}
	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}
	public int getEventTemplateId() {
		return eventTemplateId;
	}
	public void setEventTemplateId(int eventTemplateId) {
		this.eventTemplateId = eventTemplateId;
	}
	public String getEventDay() {
		return eventDay;
	}
	public void setEventDay(String day) {
		this.eventDay = day;
	}

}
