package com.makethelistapp.core.dao;

import com.makethelistapp.core.model.Organization;

public interface JdbcOrganizationDao {
	
	public Organization getOrganizationById(int id);

	public int updateOrganization(Organization organization);

}
