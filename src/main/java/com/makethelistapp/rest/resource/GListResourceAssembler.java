package com.makethelistapp.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.makethelistapp.core.dao.impl.JdbcEventDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcVenueDaoImpl;
import com.makethelistapp.core.model.Event;
import com.makethelistapp.core.model.GList;
import com.makethelistapp.core.model.Venue;
import com.makethelistapp.rest.controller.OrganizationController;

public class GListResourceAssembler implements ResourceAssembler<GList, Resource<GList>>{

	@Override
	public Resource<GList> toResource(GList glist) {
		Resource<GList> resource = new Resource<GList>(glist);
		int glistId = glist.getId();
		int eventId = glist.getEventId();
	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		
		Event event = jdbcEventDao.getEventById(eventId);
		int venueId = event.getVenueId();
		Venue venue = jdbcVenueDao.getVenueById(venueId);
		int orgId = venue.getOrganizationId();
		
		resource.add(linkTo(OrganizationController.class).slash(orgId).slash("venues").slash(venueId).slash("events").slash(eventId).slash("glists").slash(glistId).slash("reservations").withRel("reservations"));
		
		((ConfigurableApplicationContext)ctx).close();
		return resource;
	}

}
