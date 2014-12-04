package com.makethelistapp.rest.resource;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import com.makethelistapp.core.model.UserRoles;
import com.makethelistapp.rest.controller.OrganizationController;

@Component
public final class UserRolesResourceAssembler implements ResourceAssembler<UserRoles, Resource<UserRoles>> {

	@Override
	public Resource<UserRoles> toResource(UserRoles userRole) {
		Resource<UserRoles> resource = new Resource<UserRoles>(userRole); 
		int orgId = userRole.getOrganizationId();
		resource.add(linkTo(OrganizationController.class).slash(orgId).withRel("organization"));
		return resource;
	}

}
