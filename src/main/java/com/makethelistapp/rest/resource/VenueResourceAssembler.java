package com.makethelistapp.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.makethelistapp.core.model.Venue;
import com.makethelistapp.rest.controller.OrganizationController;

public class VenueResourceAssembler implements ResourceAssembler<Venue, Resource<Venue>>{

	@Override
	public Resource<Venue> toResource(Venue venue) {
		Resource<Venue> resource = new Resource<Venue>(venue);
		int orgId = venue.getOrganizationId();
		resource.add(linkTo(OrganizationController.class).slash(orgId).slash("venues").slash(venue.getId()).slash("events").withRel("events"));
		return resource;
	}

}
