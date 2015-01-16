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
		String sql = "SELECT * "
				+ "FROM (SELECT idEvent, eventStart, eventEnd, eventStatus, venueId, eventName, eventTemplateId ,eventRecurring, eventDay "
				+ "FROM event "
				+ "INNER JOIN eventTemplate on event.eventTemplateId = eventTemplate.idEventTemplate) AS temp "
				+ "WHERE temp.idEvent = ?";
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
		String sql = "SELECT idEvent, eventStart, eventEnd, eventStatus, venueId, eventTemplateId, eventName, eventRecurring, eventDay "
				+ "FROM event "
				+ "INNER JOIN eventTemplate on event.eventTemplateId = eventTemplate.idEventTemplate";
		
		
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
		Event event = new Event();
		event.setId((int) row.get("idEvent"));
		event.setName((String) row.get("eventName"));
		event.setStart((Timestamp) row.get("eventStart"));
		event.setEnd((Timestamp) row.get("eventEnd"));
		event.setRecurring((String) row.get("eventRecurring"));
		event.setStatus((String) row.get("eventStatus"));
		event.setVenueId((int) row.get("venueId"));
		event.setEventDay((String) row.get("eventDay"));
		event.setEventTemplateId((int) row.get("eventTemplateId"));
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
	
	public void deleteEventsByTemplateId (int templateId) {
		try {
			String sql = "DELETE FROM EVENT WHERE eventTemplateId = ?";
			jdbcTemplate.update(sql, templateId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deleteEventTemplateById(int id) {
		try {
			String sql = "DELETE FROM EVENTTEMPLATE WHERE idEventTemplate = ?";
			jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	public int updateEvent(final Event event) {
		int eventTemplateId = updateEventTemplate(event);
		event.setEventTemplateId(eventTemplateId);
		
		final String sql = "INSERT INTO event(`idEvent`, `eventStart`, `eventEnd`, `eventStatus`, `venueId`, `eventTemplateId`)"
				+ "VALUES(?,?,?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`eventStart` = VALUES(`eventStart`),"
				+ "`eventEnd` = VALUES(`eventEnd`),"
				+ "`eventStatus` = VALUES(`eventStatus`),"
				+ "`venueId` = VALUES(`venueId`),"
				+ "`eventTemplateId` = VALUES(`eventTemplateId`)";
		
		if (event.getId() == 0) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	                ps.setInt(1, event.getId());
	                ps.setTimestamp(2, event.getStart());
	                ps.setTimestamp(3, event.getEnd());
	                ps.setString(4, event.getStatus());
	                ps.setInt(5, event.getVenueId());
	                ps.setInt(6, event.getEventTemplateId());
	                return ps;
	            }
	        }, keyHolder);
			
			return keyHolder.getKey().intValue();
		} else {
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString());
	                ps.setInt(1, event.getId());
	                ps.setTimestamp(2, event.getStart());
	                ps.setTimestamp(3, event.getEnd());
	                ps.setString(4, event.getStatus());
	                ps.setInt(5, event.getVenueId());
	                ps.setInt(6, event.getEventTemplateId());
	                return ps;
	            }
	        });
			return event.getId();
		}
		
		
	}
	
	public int updateEventTemplate(final Event event) {

		final String sql = "INSERT INTO eventTemplate(`idEventTemplate`,`eventName`, `eventRecurring`, `eventDay`)"
				+ "VALUES(?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`eventName` = VALUES(`eventName`),"
				+ "`eventRecurring` = VALUES(`eventRecurring`),"
				+ "`eventDay` = VALUES(`eventDay`)";
		
		if (event.getEventTemplateId() == 0) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	                ps.setInt(1, event.getEventTemplateId());
	                ps.setString(2, event.getName());
	                ps.setString(3, event.getRecurring());
	                ps.setString(4, event.getEventDay());
	                return ps;
	            }
	        }, keyHolder);
			
			return keyHolder.getKey().intValue();
			
		} else {
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString());
	                ps.setInt(1, event.getEventTemplateId());
	                ps.setString(2, event.getName());
	                ps.setString(3, event.getRecurring());
	                ps.setString(4, event.getEventDay());
	                return ps;
	            }
	        });
			
			return event.getEventTemplateId();
			
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
			event.setVenueId(rs.getInt("venueId"));
			event.setEventDay(rs.getString("eventDay"));
			event.setEventTemplateId(rs.getInt("eventTemplateId"));
			return event;
		}
		
	}

}
