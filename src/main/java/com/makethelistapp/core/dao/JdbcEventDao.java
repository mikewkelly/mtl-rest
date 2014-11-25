package com.makethelistapp.core.dao;

import java.sql.Timestamp;
import java.util.List;

import com.makethelistapp.core.model.Event;

public interface JdbcEventDao {
	
	public Event getEventById(int id);
	
	public Event getEventByNameAndVenueId(String name, int venueId);
	
	public List<Event> getAllEventsByVenueId(int venueId);
	
	public Event getEventByDateAndVenueId(Timestamp startDate, int venueId);
	
	public List<Event> getAllEventsByVenueIdandStatus(int venueId, String status);
	
	public int updateEvent(Event event);

}
