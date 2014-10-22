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

import com.makethelistapp.core.dao.JdbcUserRolesDao;
import com.makethelistapp.core.model.UserRoles;

@Component
public class JdbcUserRolesDaoImpl implements JdbcUserRolesDao{
	
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
	public UserRoles getUserRoleById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoles> getAllUserRolesByOrganizationId(int organizationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoles> getAllUserRolesByOrganizationIdandUsername(
			int organizationId, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoles> getAllUserRolesByUsername(String username) {
		List<UserRoles> userRoles = new ArrayList<UserRoles>();
		
		String sql = "SELECT * FROM USER_ROLES WHERE username=?";

		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { username });
		for (Map<String,Object> row : rows) {
			UserRoles userRole = new UserRoles();
			userRole.setId((int)row.get("userRoleId")); 
		    userRole.setUsername((String)row.get("username"));
		    userRole.setRole((String)row.get("role"));
		    userRole.setOrganizationId((int)row.get("organizationId"));
	        userRoles.add(userRole);
		}
		return userRoles;

	}
	
	public class UserRolesRowMapper implements RowMapper<UserRoles> {

		@Override
		public UserRoles mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRoles userRole = new UserRoles();
	        userRole.setId(rs.getInt("userRoleId")); 
	        userRole.setUsername(rs.getString("username"));
	        userRole.setRole(rs.getString("role"));
	        userRole.setOrganizationId(rs.getInt("organizationId"));
			return userRole;
		}
		
	}

}
