package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.GList;

public interface JdbcGListDao {
	
	public GList getGListById(int id);
	
	public List<GList> getAllGListsByEventId(int eventId);
	
	public int updateGList(GList glist);
	

}
