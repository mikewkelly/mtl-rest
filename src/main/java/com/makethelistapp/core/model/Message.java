package com.makethelistapp.core.model;

public class Message {
	
	int id;
	int messageToEmployeeId;
	String contents;
	int notificationId;
	int messageFromEmployeeId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMessageToEmployeeId() {
		return messageToEmployeeId;
	}
	public void setMessageToEmployeeId(int messageToEmployeeId) {
		this.messageToEmployeeId = messageToEmployeeId;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	public int getMessageFromEmployeeId() {
		return messageFromEmployeeId;
	}
	public void setMessageFromEmployeeId(int messageFromEmployeeId) {
		this.messageFromEmployeeId = messageFromEmployeeId;
	}

}
