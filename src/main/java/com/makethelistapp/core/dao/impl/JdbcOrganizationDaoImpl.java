package com.makethelistapp.core.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.makethelistapp.core.dao.JdbcOrganizationDao;
import com.makethelistapp.core.model.Organization;
import com.mysql.jdbc.Statement;



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
	
	public List<Organization> getAllOrganizations() {
		List<Organization> organizations = new ArrayList<Organization>();
		String sql = "SELECT * FROM ORGANIZATION";
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			Organization organization = new Organization();
			organization.setId((int) row.get("idOrganization")); 
			organization.setName((String)row.get("orgName")); 
			organization.setStreet((String) row.get("orgStreet"));
			organization.setCity((String) row.get("orgCity"));
			organization.setProvince((String) row.get("orgProvince"));
			organization.setCountry((String) row.get("orgCountry"));
			organization.setContactName((String) row.get("orgContactName"));
			organization.setContactPhone((String) row.get("orgContactPhone"));
			organization.setContactEmail((String) row.get("orgContactEmail"));
			organization.setStatus((String) row.get("orgStatus"));
			
	        organizations.add(organization);
		}
		return organizations;
	}
	
	@Override
	public int updateOrganization(final Organization organization) {
		
		final String sql = "INSERT INTO organization(`idOrganization`, `orgName`, `orgStreet`, `orgCity`, `orgProvince`, `orgCountry`, `orgContactName`, `orgContactPhone`, `orgContactEmail`, `orgStatus`)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?)"
				+ "ON DUPLICATE KEY UPDATE"
				+ "`orgName` = VALUES(`orgName`),"
				+ "`orgStreet` = VALUES(`orgStreet`),"
				+ "`orgCity` = VALUES(`orgCity`),"
				+ "`orgProvince` = VALUES(`orgProvince`),"
				+ "`orgCountry` = VALUES(`orgCountry`),"
				+ "`orgContactName` = VALUES(`orgContactName`),"
				+ "`orgContactPhone` = VALUES(`orgContactPhone`),"
				+ "`orgContactEmail` = VALUES(`orgContactEmail`),"
				+ "`orgStatus` = VALUES(`orgStatus`)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {           

            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, organization.getId());
                ps.setString(2, organization.getName());
                ps.setString(3, organization.getStreet());
                ps.setString(4, organization.getCity());
                ps.setString(5, organization.getProvince());
                ps.setString(6, organization.getCountry());
                ps.setString(7, organization.getContactName());
                ps.setString(8, organization.getContactPhone());
                ps.setString(9, organization.getContactEmail());
                ps.setString(10, organization.getStatus());
                
                return ps;
            }
        }, keyHolder);
		
		return keyHolder.getKey().intValue();
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
