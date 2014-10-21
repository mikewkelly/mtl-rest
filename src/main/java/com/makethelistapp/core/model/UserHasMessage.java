package com.makethelistapp.core.model;

public class UserHasMessage {
	
	int id;
	int userToId;
	int userFromId;
	int messageId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserToId() {
		return userToId;
	}
	public void setUserToId(int userToId) {
		this.userToId = userToId;
	}
	public int getUserFromId() {
		return userFromId;
	}
	public void setUserFromId(int userFromId) {
		this.userFromId = userFromId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	


}
