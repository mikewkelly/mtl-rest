package com.makethelistapp.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.makethelistapp.core.dao.JdbcReservationDao;
import com.makethelistapp.core.dao.impl.JdbcGListDaoImpl.GListRowMapper;
import com.makethelistapp.core.model.Reservation;

@Component
public class JdbcReservationDaoImpl implements JdbcReservationDao {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	@Override
	public Reservation getReservationById(int id) {
		String sql = "SELECT * FROM reservation WHERE idReservation = ?";
		Reservation reservation = jdbcTemplate.queryForObject(sql, new Object[] { id }, new ReservationRowMapper());
		return reservation;
	}

	@Override
	public List<Reservation> getAllReservationsByGListId(int glistId) {
		List<Reservation> reservations = new ArrayList<Reservation>();
		String sql = "SELECT * FROM reservation WHERE glistId=" + glistId;
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			Reservation reservation = new Reservation();
			reservation.setId((int) row.get("idReservation"));
			reservation.setFirstName((String) row.get("reservationFirstName"));
			reservation.setLastName((String) row.get("reservationLastName"));
			if (row.get("reservationFreeCover") != null) {
				reservation.setFreeCover((int) row.get("reservationFreeCover"));
			}
			if (row.get("reservationHalfCover") != null) {
				reservation.setHalfCover((int) row.get("reservationHalfCover"));
			}
			if (row.get("reservationNumGuests") != null) {
				reservation.setNumGuests((int) row.get("reservationNumGuests"));
			}
			if (row.get("reservationPayCover") != null) {
				reservation.setPayCover((int) row.get("reservationPayCover"));
			}

			if (row.get("userId") != null) {
				reservation.setUserId((int) row.get("userId"));
			}	
			reservation.setNote((String) row.get("reservationNote"));
			reservation.setGlistId((int) row.get("glistId"));			
			reservation.setArrived((boolean) row.get("reservationArrived"));
			reservation.setStatus((String) row.get("reservationStatus"));
			reservations.add(reservation);
		}
		return reservations;
	}

	@Override
	public List<Reservation> getAllReservationsByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void updateReservation(Reservation reservation) {
		int id = reservation.getId();
		String firstName = reservation.getFirstName();
		String lastName = reservation.getLastName();
		int freeCover = reservation.getFreeCover();
		int halfCover = reservation.getHalfCover();
		int numGuests = reservation.getNumGuests();
		int payCover = reservation.getPayCover();
		int glistId = reservation.getGlistId();
		int userId = reservation.getUserId(); //optional
		boolean arrived = reservation.isArrived();
		String status = reservation.getStatus();
		String note = reservation.getNote();
		
		String sql = "INSERT INTO reservation VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
//				+ "ON DUPLICATE KEY UPDATE"
//				+ "reservationFirstName = VALUES(reservationFirstName),"
//				+ "reservationLastName = VALUES(reservationLastName),"
//				+ "reservationFreeCover = VALUES(reservationFreeCover),"
//				+ "reservationHalfCover = VALUES(reservationHalfCover),"
//				+ "reservationNumGuests = VALUES(reservationNumGuests),"
//				+ "reservationPayCover = VALUES(reservationPayCover),"
//				+ "glistId = VALUES(glistId),"
//				+ "userId = VALUES(userId),"
//				+ "reservationArrived = VALUES(reservationArrived),"
//				+ "reservationStatus = VALUES(reservationStatus),"
//				+ "reservationNote = VALUES(reservationNote)";
		
		jdbcTemplate.update(sql, id, firstName, lastName, freeCover, halfCover, numGuests, payCover, glistId, userId, arrived, status, note);
		
	}
	
	public class ReservationRowMapper implements RowMapper<Reservation> {

		@Override
		public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reservation reservation = new Reservation();
			reservation.setId(rs.getInt("idReservation"));
			reservation.setFirstName(rs.getString("reservationFirstName"));
			reservation.setLastName(rs.getString("reservationLastName"));
			reservation.setFreeCover(rs.getInt("reservationFreeCover"));
			reservation.setHalfCover(rs.getInt("reservationHalfCover"));
			reservation.setNumGuests(rs.getInt("reservationNumGuests"));
			reservation.setPayCover(rs.getInt("reservationPayCover"));
			reservation.setNote(rs.getString("reservationNote"));
			reservation.setGlistId(rs.getInt("glistId"));
			reservation.setUserId(rs.getInt("userId"));
			reservation.setArrived(rs.getBoolean("reservationArrived"));
			reservation.setStatus(rs.getString("reservationStatus"));
			return reservation;
		}
		
	}




}
