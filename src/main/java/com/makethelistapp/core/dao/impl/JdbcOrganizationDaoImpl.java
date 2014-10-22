package com.makethelistapp.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.makethelistapp.core.dao.JdbcOrganizationDao;
import com.makethelistapp.core.model.Organization;

@Component
public class JdbcOrganizationDaoImpl implements JdbcOrganizationDao {
	
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
	public Organization getOrganizationById(int id) {
		String sql = "SELECT * FROM ORGANIZATION WHERE idOrganization = ?";	 
		Organization organization = jdbcTemplate.queryForObject(
				sql, new Object[] { id }, new OrganizationRowMapper());	
		return organization;
	}
	
	public class OrganizationRowMapper implements RowMapper<Organization> {

		@Override
		public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
			Organization organization = new Organization();
			organization.setId(rs.getInt("idOrganization")); 
			organization.setName(rs.getString("orgName")); 
			organization.setStreet(rs.getString("orgStreet"));
			organization.setCity(rs.getString("orgCity"));
			organization.setProvince(rs.getString("orgProvince"));
			organization.setCountry(rs.getString("orgCountry"));
			organization.setContactName(rs.getString("orgContactName"));
			organization.setContactPhone(rs.getString("orgContactPhone"));
			organization.setContactEmail(rs.getString("orgContactEmail"));
			organization.setStatus(rs.getString("orgStatus"));
	        
			return organization;
		}
		
	}

}
