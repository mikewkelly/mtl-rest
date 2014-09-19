package com.makethelistapp.core.events;

import java.sql.*;

import com.makethelistapp.config.DBConfig;;

public class AuthEvent {
	
	String username;
	String password;
	
	public AuthEvent(String username, String password) {
		this.username = username;
		this.password = password;
	}
	/**
	 * Queries the db to determine if a user's supplied username and password combination are valid
	 * @return the user's idUser, null if not found.
	 */
	public Integer authUser(){
		Connection conn = null;
		Integer idUser = new Integer(null);
		  try{
		      Class.forName(DBConfig.JDBC_DRIVER);
		      conn = DriverManager.getConnection(DBConfig.AUTH_DB_URL,DBConfig.DB_USER,DBConfig.DB_PASS);
		      PreparedStatement ps = conn.prepareStatement("SELECT idUser FROM user WHERE userUsername = ? AND userPassword = ?");
		      ps.setString(1, this.username);
		      ps.setString(2, this.password);
		      ResultSet rs = ps.executeQuery();
		      if (!rs.isBeforeFirst() ) {    
		    	  //No user found with the supplied username/password combination
		    	 } 
		      while(rs.next()){
		    	  int idUser1  = rs.getInt("idUser");
		    	  idUser = Integer.valueOf(idUser1);
		      }
		      rs.close();
		      conn.close();
		   }catch(SQLException se){
		      //TODO: add logging
		   }catch(Exception e){
		      //TODO: add logging
		   }finally{
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		    	//TODO: add logging
		      }
		   }
		return idUser;
	}
}
