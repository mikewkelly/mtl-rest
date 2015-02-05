package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.EventImage;

public interface JdbcEventImageDao {
	
	public EventImage getEventImageById(int id);
	
	public List<EventImage> getAllEventImagesByEventId(int eventId);
	
	public int updateEventImage(EventImage eventImage);

}
