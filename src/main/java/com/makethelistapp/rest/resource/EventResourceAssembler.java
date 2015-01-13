package com.makethelistapp.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import com.makethelistapp.core.dao.impl.JdbcVenueDaoImpl;
import com.makethelistapp.core.model.Event;
import com.makethelistapp.core.model.Venue;
import com.makethelistapp.rest.controller.OrganizationController;

public class EventResourceAssembler implements ResourceAssembler<Event, Resource<Event>>{

	@Override
	public Resource<Event> toResource(Event event) {
		Resource<Event> resource = new Resource<Event>(event);
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		Venue venue = jdbcVenueDao.getVenueById(event.getVenueId());
		int orgId = venue.getOrganizationId();
		((ConfigurableApplicationContext)ctx).close();
		resource.add(linkTo(OrganizationController.class).slash(orgId).slash("venues").slash(venue.getId()).slash("events").slash(event.getId()).withSelfRel());
		resource.add(linkTo(OrganizationController.class).slash(orgId).slash("venues").slash(venue.getId()).slash("events").slash(event.getId()).slash("glists").withRel("glists"));
		return resource;
	}

}
