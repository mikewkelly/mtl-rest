package com.makethelistapp.rest.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

import com.makethelistapp.core.dao.impl.JdbcEventDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcGListDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcVenueDaoImpl;
import com.makethelistapp.core.model.Event;
import com.makethelistapp.core.model.GList;
import com.makethelistapp.core.model.Reservation;
import com.makethelistapp.core.model.Venue;
import com.makethelistapp.rest.controller.OrganizationController;

public class ReservationResourceAssembler implements ResourceAssembler<Reservation, Resource<Reservation>>{

	@Override
	public Resource<Reservation> toResource(Reservation reservation) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		int glistId = reservation.getGlistId();
		GList glist = jdbcGListDao.getGListById(glistId);
		int eventId = glist.getEventId();
		Event event = jdbcEventDao.getEventById(eventId);
		int venueId = event.getVenueId();
		Venue venue = jdbcVenueDao.getVenueById(venueId);
		int orgId = venue.getOrganizationId();
		
		
		Resource<Reservation> resource = new Resource<Reservation>(reservation);
		
		resource.add(linkTo(OrganizationController.class).slash(orgId).slash("venues").slash(venueId).slash("events").slash(eventId).slash("glists").slash(glistId).slash("reservations").slash(reservation.getId()).withSelfRel());
		
		((ConfigurableApplicationContext)ctx).close();
		return resource;
	}

}
