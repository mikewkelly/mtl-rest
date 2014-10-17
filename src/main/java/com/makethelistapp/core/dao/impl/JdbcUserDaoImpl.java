package com.makethelistapp.core.dao.impl;

import com.makethelistapp.core.dao.JdbcUserDao;
import com.makethelistapp.core.model.User;

import java.sql.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserDaoImpl implements JdbcUserDao {

	@Autowired //may need to change @Autowired for multiple datasources
	private DataSource dataSource;

	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public User getUserById(int id) {
		User user = new User();
		Connection conn = null;
		
		   try{
			   
			  conn = dataSource.getConnection();

		      PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE idUser = ?");
		      ps.setInt(1, id);

		      ResultSet rs = ps.executeQuery();
	
		      if (!rs.isBeforeFirst() ) {    
		    	  user.setId(id);
		    	  user.setFirstName("It Was Null");
		    	  return user;
		    	 } 

		      while(rs.next()){
		         //Retrieve by column name
		         user.setId(rs.getInt("idUser")); 
		         user.setEmail(rs.getString("userEmail"));
		         user.setFirstName(rs.getString("userFirstName"));
		         user.setLastName(rs.getString("userLastName"));
		         user.setStatus(rs.getString("userStatus"));
		         user.setPassword(rs.getString("userPassword"));
		      }
		      rs.close();
		      conn.close();
		   }catch(SQLException se){

		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		return user;
	}
	
	public User getUserByEmail(String email) {
		User user = new User();
		return user;
	}
	
	
}

