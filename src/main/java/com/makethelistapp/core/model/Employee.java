package com.makethelistapp.core.model;

public class Employee {
	
	int id;
	int customerAdded;
	int customerArrived;
	String status;
	int userId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerAdded() {
		return customerAdded;
	}
	public void setCustomerAdded(int customerAdded) {
		this.customerAdded = customerAdded;
	}
	public int getCustomerArrived() {
		return customerArrived;
	}
	public void setCustomerArrived(int customerArrived) {
		this.customerArrived = customerArrived;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	

}
