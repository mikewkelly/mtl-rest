package com.makethelistapp.core.model;

public class Notification {
	
	int id;
	String type;
	Boolean seen;
	Boolean messageWith;
	int userId;
	int messageId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getSeen() {
		return seen;
	}
	public void setSeen(Boolean seen) {
		this.seen = seen;
	}
	public Boolean getMessageWith() {
		return messageWith;
	}
	public void setMessageWith(Boolean messageWith) {
		this.messageWith = messageWith;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

}
