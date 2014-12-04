package com.makethelistapp.resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import com.makethelistapp.core.dao.impl.JdbcOrganizationDaoImpl;
import com.makethelistapp.core.model.Organization;
import com.makethelistapp.core.model.UserRoles;
import com.makethelistapp.rest.controller.RestController;

@Component
public final class UserRolesResourceAssembler implements ResourceAssembler<UserRoles, Resource<UserRoles>> {

	@Override
	public Resource<UserRoles> toResource(UserRoles userRole) {
		Resource<UserRoles> resource = new Resource<UserRoles>(userRole); 
		int orgId = userRole.getOrganizationId();
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		Organization organization = jdbcOrganizationDao.getOrganizationById(orgId);
		((ConfigurableApplicationContext)ctx).close();
		resource.add(linkTo(RestController.class).slash("organization").slash(orgId).withRel(organization.getName()));
		return resource;
	}

}
