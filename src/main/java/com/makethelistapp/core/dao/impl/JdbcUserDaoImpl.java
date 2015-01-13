package com.makethelistapp.core.dao.impl;

import com.makethelistapp.core.dao.JdbcUserDao;
import com.makethelistapp.core.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserDaoImpl implements JdbcUserDao {

	
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
	
	public User getUserById(int id) {
		String sql = "SELECT * FROM USER WHERE idUser = ?";	 
		User user = jdbcTemplate.queryForObject(
				sql, new Object[] { id }, new UserRowMapper());	
		return user;
	}
	
	public User getUserByUsername(String email) {
		String sql = "SELECT * FROM USER WHERE username = ?";	 
		User user = jdbcTemplate.queryForObject(
				sql, new Object[] { email }, new UserRowMapper());	
		return user;
	}
	
	
	public int getUserCount() {
		String sql = "SELECT COUNT(*) FROM USER";
		
		//int count = jdbcTemplate.queryForInt(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM USER";
		List<Map<String,Object>> rows = jdbcTemplate.queryForList(sql);
		for (Map<String,Object> row : rows) {
			User user = new User();
	        user.setId((int)row.get("idUser")); 
	        user.setUsername((String)row.get("username"));
	        user.setFirstName((String)row.get("userFirstName"));
	        user.setLastName((String)row.get("userLastName"));
	        user.setStatus((String)row.get("userStatus"));
	        user.setPassword((String)row.get("userPassword"));
	        users.add(user);
		}
		return users;
	}
	
	public class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
	        user.setId(rs.getInt("idUser")); 
	        user.setUsername(rs.getString("username"));
	        user.setFirstName(rs.getString("userFirstName"));
	        user.setLastName(rs.getString("userLastName"));
	        user.setStatus(rs.getString("userStatus"));
	        user.setPassword(rs.getString("userPassword"));
			return user;
		}
		
	}
	
}

