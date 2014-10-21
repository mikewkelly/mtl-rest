package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.Notification;

public interface JdbcNotificationDao {
	
	public Notification getNotificationById(int id);
	
	public Notification getNotificationByMessageId(int messageId);
	
	public List<Notification> getNotificationAllNotificationsByUserId(int userId, Boolean seen);

}
