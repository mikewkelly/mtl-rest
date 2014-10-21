package com.makethelistapp.core.dao;

import java.util.List;

import com.makethelistapp.core.model.UserRoles;

public interface JdbcUserRolesDao {
	
	public UserRoles getUserRoleById(int id);
	
	public List<UserRoles> getAllUserRolesByOrganizationId(int organizationId);
	
	public List<UserRoles> getAllUserRolesByOrganizationIdandUsername(int organizationId, String username);
	
	public List<UserRoles> getAllUserRolesByUsername(String username);

}
