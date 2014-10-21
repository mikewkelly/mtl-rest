package com.makethelistapp.core.dao;

import com.makethelistapp.core.model.Message;

public interface JdbcMessageDao {
	
	public Message getMessageById(int id);

}
