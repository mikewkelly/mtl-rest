package com.makethelistapp.core.model;

public class UserHasMessage {
	
	int id;
	int userToId;
	int userFromId;
	String message;
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	


}
