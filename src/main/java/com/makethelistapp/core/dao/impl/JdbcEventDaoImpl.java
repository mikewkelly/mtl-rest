package com.makethelistapp.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.makethelistapp.core.dao.JdbcEventDao;
import com.makethelistapp.core.dao.impl.JdbcOrganizationDaoImpl.OrganizationRowMapper;
import com.makethelistapp.core.model.Event;
import com.mysql.jdbc.Statement;

@Component
public class JdbcEventDaoImpl implements JdbcEventDao {
	
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
	public Event getEventById(int id) {
		String sql = "SELECT * FROM event WHERE idEvent=?";
		Event event = jdbcTemplate.queryForObject(sql, new Object[] { id }, new EventRowMapper());
		return event;
	}

	@Override
	public Event getEventByNameAndVenueId(String name, int venueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getAllEventsByVenueId(int venueId) {
		List<Event> events = new ArrayList<Event>();
		String sql = "SELECT * FROM event WHERE venueId=?";

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, new Object[] {venueId});
		for (Map<String,Object> row : rows) {
		Event event = new Event();
		event.setId((int) row.get("idEvent"));
		event.setName((String) row.get("eventName"));
		event.setStart((Timestamp) row.get("eventStart"));
		event.setEnd((Timestamp) row.get("eventEnd"));
		event.setRecurring((String) row.get("eventRecurring"));
		event.setStatus((String) row.get("eventStatus"));
		event.setEventDay((String) row.get("eventDay"));
		event.setVenueId((int) row.get("venueId"));
		event.setDescription((String) row.get("description"));
		event.setAddedBy((String) row.get("addedBy"));
		events.add(event);
		}
		return events;
	}

	@Override
	public Event getEventByDateAndVenueId(Timestamp startDate, int venueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Event> getAllEventsByVenueIdandStatus(int venueId, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteEventById(int id) {
		try {
			String sql = "DELETE FROM EVENT WHERE idEvent = ?";
			jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public int updateEvent(final Event event) {
		final String sql = "INSERT INTO event (`idEvent`,`eventName`,`eventStart`,`eventEnd`,`eventRecurring`,`eventStatus`,`eventDay`,`venueId`, `description`, `addedBy`)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`idEvent` = VALUES(`idEvent`),"
				+ "`eventName` = VALUES(`eventName`),"
				+ "`eventStart` = VALUES(`eventStart`),"
				+ "`eventEnd`= VALUES(`eventEnd`),"
				+ "`eventRecurring` = VALUES(`eventRecurring`),"
				+ "`eventStatus` = VALUES(`eventStatus`),"
				+ "`eventDay` = VALUES(`eventDay`),"
				+ "`venueId` = VALUES(`venueId`),"
				+ "`description` = VALUES(`description`) ,"
				+ "`addedBy` = VALUES(`addedBy`)";

		if (event.getId() == 0) {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	                ps.setInt(1, event.getId());
	                ps.setString(2, event.getName());
	                ps.setTimestamp(3, event.getStart());
	                ps.setTimestamp(4, event.getEnd());
	                ps.setString(5, event.getRecurring());
	                ps.setString(6, event.getStatus());
	                ps.setString(7, event.getEventDay());
	                ps.setInt(8, event.getVenueId());
	                ps.setString(9, event.getDescription());
	                ps.setString(10, event.getAddedBy());

	                return ps;
	            }
	        }, keyHolder);
			
			return keyHolder.getKey().intValue();
			
		} else {
			
			jdbcTemplate.update(new PreparedStatementCreator() {           
				
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql.toString());
	                ps.setInt(1, event.getId());
	                ps.setString(2, event.getName());
	                ps.setTimestamp(3, event.getStart());
	                ps.setTimestamp(4, event.getEnd());
	                ps.setString(5, event.getRecurring());
	                ps.setString(6, event.getStatus());
	                ps.setString(7, event.getEventDay());
	                ps.setInt(8, event.getVenueId());
	                ps.setString(9,  event.getDescription());
	                ps.setString(10, event.getAddedBy());
					return ps;
				}
			});
			return event.getId();
		}


	}
	

	public class EventRowMapper implements RowMapper<Event> {

		@Override
		public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
			Event event = new Event();
			event.setId(rs.getInt("idEvent"));
			event.setName(rs.getString("eventName"));
			event.setStart(rs.getTimestamp("eventStart"));
			event.setEnd(rs.getTimestamp("eventEnd"));
			event.setRecurring(rs.getString("eventRecurring"));
			event.setStatus(rs.getString("eventStatus"));
			event.setEventDay(rs.getString("eventDay"));
			event.setVenueId(rs.getInt("venueId"));
			event.setDescription(rs.getString("description"));
			event.setAddedBy(rs.getString("addedBy"));

			return event;
		}
		
	}

}
