package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.UserHasMessage;

public interface JdbcUserHasMessageDao {
	
	public UserHasMessage getUserHasMessageById(int id);
	
	public List<UserHasMessage> getAllUserHasMessageByFromUserId(int fromUserId);
	
	public List<UserHasMessage> getAllUserHasMessageByToUserId(int toUserId);
	
	public List<UserHasMessage> getAllUsersHasMessageBetween(int fromUserId, int toUserId);

}
