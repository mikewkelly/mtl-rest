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

import com.makethelistapp.core.dao.JdbcVenueDao;
import com.makethelistapp.core.dao.impl.JdbcOrganizationDaoImpl.OrganizationRowMapper;
import com.makethelistapp.core.model.Organization;
import com.makethelistapp.core.model.Reservation;
import com.makethelistapp.core.model.Venue;
import com.mysql.jdbc.Statement;

@Component
public class JdbcVenueDaoImpl implements JdbcVenueDao {
	
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
	public Venue getVenueById(int id) {
		String sql = "SELECT * FROM venue WHERE idVenue = ?";	 
		Venue venue = jdbcTemplate.queryForObject(
				sql, new Object[] { id }, new VenueRowMapper());	
		return venue;
	}

	@Override
	public List<Venue> getAllVenuesByOrganizationId(int organizationId) {
		List<Venue> venues = new ArrayList<Venue>();
		String sql = "SELECT * FROM venue WHERE organizationId=" + organizationId;
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			Venue venue = new Venue();
			venue.setId((int) row.get("idVenue"));
			venue.setName((String) row.get("venueName"));
			venue.setStreet((String) row.get("venueStreet"));
			venue.setCity((String) row.get("venueCity"));
			venue.setProvince((String) row.get("venueProvince"));
			venue.setCountry((String) row.get("venueCountry"));
			venue.setStatus((String) row.get("venueStatus"));
			venue.setOrganizationId((int) row.get("organizationId"));
			
			venues.add(venue);
		}
		return venues;
	}

	@Override
	public List<Venue> getAllVenuesByLocation(String city, String province,
			String country, String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Venue> getAllVenuesByNameAndLocation(String name, String city,
			String province, String country, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteVenueById(int id) {
		try {
			String sql = "DELETE FROM VENUE WHERE idVenue = ?";
			jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public int updateVenue(final Venue venue) {
		
		final String sql = "INSERT INTO venue (`idVenue`,`venueName`,`venueStreet`,`venueCity`,`venueProvince`,`venueCountry`,`venueStatus`,`organizationId`)"
				+ "VALUES(?,?,?,?,?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`venueName` = VALUES(`venueName`),"
				+ "`venueStreet` = VALUES(`venueStreet`),"
				+ "`venueCity` = VALUES(`venueCity`),"
				+ "`venueProvince` = VALUES(`venueProvince`),"
				+ "`venueCountry` = VALUES(`venueCountry`),"
				+ "`venueStatus` = VALUES(`venueStatus`),"
				+ "`organizationId` = VALUES(`organizationId`)";
		
		if (venue.getId() == 0) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	                ps.setInt(1, venue.getId());
	                ps.setString(2, venue.getName());
	                ps.setString(3, venue.getStreet());
	                ps.setString(4, venue.getCity());
	                ps.setString(5, venue.getProvince());
	                ps.setString(6, venue.getCountry());
	                ps.setString(7, venue.getStatus());
	                ps.setInt(8, venue.getOrganizationId());

	                return ps;
	            }
	        }, keyHolder);
			
			return keyHolder.getKey().intValue();
			
		} else {
		
			
			jdbcTemplate.update(new PreparedStatementCreator() {           

	            public PreparedStatement createPreparedStatement(Connection connection)
	                    throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(sql.toString());
	                ps.setInt(1, venue.getId());
	                ps.setString(2, venue.getName());
	                ps.setString(3, venue.getStreet());
	                ps.setString(4, venue.getCity());
	                ps.setString(5, venue.getProvince());
	                ps.setString(6, venue.getCountry());
	                ps.setString(7, venue.getStatus());
	                ps.setInt(8, venue.getOrganizationId());

	                return ps;
	            }
	        });
			
			return venue.getId();
		}
		


	}
	
	
	
	public class VenueRowMapper implements RowMapper<Venue> {
		@Override
		public Venue mapRow(ResultSet rs, int rowNum) throws SQLException {
			Venue venue = new Venue();
			venue.setId(rs.getInt("idVenue"));
			venue.setName(rs.getString("venueName"));
			venue.setStreet(rs.getString("venueStreet"));
			venue.setCity(rs.getString("venueCity"));
			venue.setProvince(rs.getString("venueProvince"));
			venue.setCountry(rs.getString("venueCountry"));
			venue.setStatus(rs.getString("venueStatus"));
			venue.setOrganizationId(rs.getInt("organizationId"));
			return venue;
		}
		
	}

}
