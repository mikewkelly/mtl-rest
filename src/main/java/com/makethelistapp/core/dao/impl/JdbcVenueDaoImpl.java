package com.makethelistapp.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.makethelistapp.core.dao.JdbcVenueDao;
import com.makethelistapp.core.dao.impl.JdbcOrganizationDaoImpl.OrganizationRowMapper;
import com.makethelistapp.core.model.Organization;
import com.makethelistapp.core.model.Venue;

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
	public List<Venue> getAllVenuesByOrganizationId(int organizationId,
			String status) {
		// TODO Auto-generated method stub
		return null;
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
	
	public class VenueRowMapper implements RowMapper<Venue> {
		
//		int id;
//		String name;
//		String street;
//		String city;
//		String province;
//		String country;
//		String status;
//		int organizationId;

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
