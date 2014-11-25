package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.Venue;

public interface JdbcVenueDao {
	
	public Venue getVenueById(int id);
	
	public List<Venue> getAllVenuesByOrganizationId(int organizationId);
	
	public List<Venue> getAllVenuesByLocation(String city, String province, String country, String status);
	
	public List<Venue> getAllVenuesByNameAndLocation(String name, String city, String province, String country, String status);
	
	public int updateVenue(Venue venue);
	
}
