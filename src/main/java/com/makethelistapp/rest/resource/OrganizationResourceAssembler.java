package com.makethelistapp.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.makethelistapp.core.model.Organization;
import com.makethelistapp.rest.controller.OrganizationController;

public class OrganizationResourceAssembler implements ResourceAssembler<Organization, Resource<Organization>>{

	@Override
	public Resource<Organization> toResource(Organization organization) {
		Resource<Organization> resource = new Resource<Organization>(organization);
		resource.add(linkTo(OrganizationController.class).slash(organization.getId()).withSelfRel());
		resource.add(linkTo(OrganizationController.class).slash(organization.getId()).slash("venues").withRel("venues"));
		return resource;
	}

}
