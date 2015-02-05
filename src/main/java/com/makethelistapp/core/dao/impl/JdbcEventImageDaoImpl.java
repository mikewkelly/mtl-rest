package com.makethelistapp.core.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.makethelistapp.core.dao.JdbcEventImageDao;
import com.makethelistapp.core.model.EventImage;
import com.mysql.jdbc.Statement;

@Component
public class JdbcEventImageDaoImpl implements JdbcEventImageDao {
	
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
	public EventImage getEventImageById(int id) {
		String sql = "SELECT * FROM eventImage WHERE idEventImage=?";
		EventImage eventImage = jdbcTemplate.queryForObject(sql, new Object[] { id }, new EventImageRowMapper());
		return eventImage;
	}
	
	public EventImage getCurrentEventImage() {
		String sql = "SELECT * FROM eventImage WHERE current=?";
		int id = 1;
		try {
			EventImage eventImage = jdbcTemplate.queryForObject(sql, new Object[] { id }, new EventImageRowMapper());
			return eventImage;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<EventImage> getAllEventImagesByEventId(int eventId) {
		List<EventImage> eventImages = new ArrayList<EventImage>();
		String sql = "SELECT * FROM eventImage WHERE eventId=?";
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, new Object[] {eventId});
		for (Map<String,Object> row : rows) {
			EventImage eventImage = new EventImage();
			eventImage.setId((int) row.get("idEventImage"));
			eventImage.setEventId((int) row.get("eventId"));
			eventImage.setUrl((String) row.get("url"));
			eventImage.setAddedBy((String) row.get("addedBy"));
			eventImage.setCurrent((Boolean) row.get("current"));
			eventImages.add(eventImage);
		}
		return eventImages;
	}

	@Override
	public int updateEventImage(final EventImage eventImage) {
		final String sql = "INSERT INTO eventimage (`idEventImage`,`eventId`,`url`,`addedBy`,`current`)"
				+ "VALUES(?,?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`idEventImage` = VALUES(`idEventImage`),"
				+ "`eventId` = VALUES(`eventId`),"
				+ "`url` = VALUES(`url`),"
				+ "`addedBy`= VALUES(`addedBy`),"
				+ "`current` = VALUES(`current`)";

		if (eventImage.getId() == 0) {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	                ps.setInt(1, eventImage.getId());
	                ps.setInt(2, eventImage.getEventId());
	                ps.setString(3, eventImage.getUrl());
	                ps.setString(4, eventImage.getAddedBy());
	                ps.setBoolean(5, eventImage.getCurrent());
	                return ps;
	            }
	        }, keyHolder);
			
			return keyHolder.getKey().intValue();
			
		} else {
			
			jdbcTemplate.update(new PreparedStatementCreator() {           
				
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql.toString());
	                ps.setInt(1, eventImage.getId());
	                ps.setInt(2, eventImage.getEventId());
	                ps.setString(3, eventImage.getUrl());
	                ps.setString(4, eventImage.getAddedBy());
	                ps.setBoolean(5, eventImage.getCurrent());
					return ps;
				}
			});
			return eventImage.getId();
		}
	}
	
	public class EventImageRowMapper implements RowMapper<EventImage> {

		@Override
		public EventImage mapRow(ResultSet rs, int rowNum) throws SQLException {
			EventImage eventImage = new EventImage();

			eventImage.setId(rs.getInt("idEventImage"));
			eventImage.setEventId(rs.getInt("eventId"));
			eventImage.setUrl(rs.getString("url"));
			eventImage.setAddedBy(rs.getString("addedBy"));
			eventImage.setCurrent(rs.getBoolean("current"));

			return eventImage;
		}
		
	}

}
