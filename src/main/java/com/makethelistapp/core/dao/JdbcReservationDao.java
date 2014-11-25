package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.Reservation;

public interface JdbcReservationDao {
	
	public Reservation getReservationById(int id);
	
	public List<Reservation> getAllReservationsByGListId(int glistId);
	
	public List<Reservation> getAllReservationsByUserId(int userId);
	
	public int updateReservation(Reservation reservation);

}
