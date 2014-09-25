package com.makethelistapp.core.dao.impl;

import com.makethelistapp.core.dao.JdbcUserDao;
import com.makethelistapp.core.model.User;

import java.sql.*;

public class JdbcUserDaoImpl implements JdbcUserDao {


	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/mtl";
	
	static final String USER = "root";
	static final String PASS = "root";

	
	public User getUserByEmail(String email) {
		User user = new User();
		Connection conn = null;
		
		   try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE userEmail = ?");
		      ps.setString(1, email);

		      ResultSet rs = ps.executeQuery();
	
		      if (!rs.isBeforeFirst() ) {    
		    	  System.out.println("No data");
		    	  return null;
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
	
	public User getUserById(int id) {
		User user = new User();
		Connection conn = null;
		
		   try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

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
	
	public void setTest() {
		Connection conn = null;
		
		   try{
		      Class.forName("com.mysql.jdbc.Driver");

		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      PreparedStatement ps = conn.prepareStatement("INSERT INTO USER (`idUser`, `userFirstName`, `userLastName`, `userEmail`, `userStatus`, `userUsername`, `userPassword`) VALUES (0, 'G', 'G', 'G', 'G', 'G', 'G')");
		      
		      String string = "INSERT INTO USER (`idUser`, `userFirstName`, `userLastName`, `userEmail`, `userStatus`, `userUsername`, `userPassword`) VALUES (0, 'G', 'G', 'G', 'G', 'G', 'G');";
		      ps.executeQuery();
	
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
	
	}
}

