package com.makethelistapp.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makethelistapp.core.dao.impl.JdbcEventDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcGListDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcOrganizationDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcReservationDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcVenueDaoImpl;
import com.makethelistapp.core.model.Event;
import com.makethelistapp.core.model.GList;
import com.makethelistapp.core.model.Organization;
import com.makethelistapp.core.model.Reservation;
import com.makethelistapp.core.model.Venue;
import com.makethelistapp.rest.resource.EventResourceAssembler;
import com.makethelistapp.rest.resource.GListResourceAssembler;
import com.makethelistapp.rest.resource.OrganizationResourceAssembler;
import com.makethelistapp.rest.resource.ReservationResourceAssembler;
import com.makethelistapp.rest.resource.VenueResourceAssembler;


@Controller
@RequestMapping("/api/organization")
public class OrganizationController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Organization>> getOrganization(@PathVariable("idOrganization") int orgId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		Organization organization = jdbcOrganizationDao.getOrganizationById(orgId);
		((ConfigurableApplicationContext)ctx).close();
		OrganizationResourceAssembler orgResAss = new OrganizationResourceAssembler();
		Resource<Organization> resource = orgResAss.toResource(organization);
		ResponseEntity<Resource<Organization>> response = new ResponseEntity<Resource<Organization>>(resource, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Organization>> addOrganization(@RequestBody Organization organization) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		OrganizationResourceAssembler orgResAss = new OrganizationResourceAssembler();
		
		try {
			int newId = jdbcOrganizationDao.updateOrganization(organization);
			organization.setId(newId);
			Resource<Organization> resource = orgResAss.toResource(organization);
			ResponseEntity<Resource<Organization>> response = new ResponseEntity<Resource<Organization>>(resource, HttpStatus.ACCEPTED);
			((ConfigurableApplicationContext)ctx).close();
			return response;
			
		} catch (Exception e) {
			Resource<Organization> resource = orgResAss.toResource(null);
			ResponseEntity<Resource<Organization>> response = new ResponseEntity<Resource<Organization>>(resource, HttpStatus.NOT_ACCEPTABLE);
			((ConfigurableApplicationContext)ctx).close();
			return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{idOrganization}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus updateOrganization(@PathVariable("idOrganization") int orgId, @RequestBody Organization organization) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		
		if (organization.getId() != orgId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcOrganizationDao.updateOrganization(organization);
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{idOrganization}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus deleteOrganization(@PathVariable("idOrganization") int orgId, @RequestBody Organization organization) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		
		if (organization.getId() != orgId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcOrganizationDao.deleteOrganizaionById(organization.getId());

			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Resource<Venue>>> getVenues(@PathVariable("idOrganization") int orgId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		List<Venue> venues = jdbcVenueDao.getAllVenuesByOrganizationId(orgId);
		((ConfigurableApplicationContext)ctx).close();
		VenueResourceAssembler vra = new VenueResourceAssembler();
		List<Resource<Venue>> resources = new ArrayList<Resource<Venue>>();
		for (int i=0;i<venues.size();i++) {
			Resource<Venue> resource = vra.toResource(venues.get(i));
			resources.add(resource);
		}
		ResponseEntity<List<Resource<Venue>>> response = new ResponseEntity<List<Resource<Venue>>>(resources, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues/{idVenue}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Venue>> getVenue(@PathVariable("idVenue") int venueId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		Venue venue = jdbcVenueDao.getVenueById(venueId);
		VenueResourceAssembler vra = new VenueResourceAssembler();
		Resource<Venue> resource = vra.toResource(venue);
		ResponseEntity<Resource<Venue>> response = new ResponseEntity<Resource<Venue>>(resource, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idOrganization}/venues", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Venue>> addVenue(@RequestBody Venue venue) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		VenueResourceAssembler vra = new VenueResourceAssembler();

		try {
			int newId = jdbcVenueDao.updateVenue(venue);
			venue.setId(newId);
			Resource<Venue> resource = vra.toResource(venue);
			ResponseEntity<Resource<Venue>> response = new ResponseEntity<Resource<Venue>>(resource, HttpStatus.ACCEPTED);
			((ConfigurableApplicationContext)ctx).close();
			return response;

		} catch (Exception e) {
			Resource<Venue> resource = vra.toResource(null);
			ResponseEntity<Resource<Venue>> response = new ResponseEntity<Resource<Venue>>(resource, HttpStatus.NOT_ACCEPTABLE);
			((ConfigurableApplicationContext)ctx).close();	
			return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{idOrganization}/venues/{idVenue}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus updateVenue(@PathVariable("idVenue") int venueId, @RequestBody Venue venue) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		
		if (venue.getId() != venueId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcVenueDao.updateVenue(venue);
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{idOrganization}/venues/{idVenue}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus deleteVenue(@PathVariable("idVenue") int venueId, @RequestBody Venue venue) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		
		if (venue.getId() != venueId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcVenueDao.deleteVenueById(venue.getId());
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues/{idVenue}/events", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Resource<Event>>> getEvents(@PathVariable("idVenue") int venueId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		List<Event> events = jdbcEventDao.getAllEventsByVenueId(venueId);
		EventResourceAssembler era = new EventResourceAssembler();
		List<Resource<Event>> resources = new ArrayList<Resource<Event>>();
		for (int i=0;i<events.size();i++) {
			Resource<Event> resource = era.toResource(events.get(i));
			resources.add(resource);
		}
		ResponseEntity<List<Resource<Event>>> response = new ResponseEntity<List<Resource<Event>>>(resources, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}	
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Event>> getEvent(@PathVariable("idEvent") int eventId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		Event event = jdbcEventDao.getEventById(eventId);
		EventResourceAssembler era = new EventResourceAssembler();
		Resource<Event> resource = era.toResource(event);
		ResponseEntity<Resource<Event>> response = new ResponseEntity<Resource<Event>>(resource, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idOrganization}/venues/{idVenue}/events", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Event>> addEvent(@RequestBody Event event) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		EventResourceAssembler era = new EventResourceAssembler();
		
		try {
			int newId = jdbcEventDao.updateEvent(event);
			((ConfigurableApplicationContext)ctx).close();
			event.setId(newId);
			Resource<Event> resource = era.toResource(event);
			ResponseEntity<Resource<Event>> response = new ResponseEntity<Resource<Event>>(resource, HttpStatus.ACCEPTED);
			return response;
		} catch (Exception e) {
			Resource<Event> resource = era.toResource(null);
			ResponseEntity<Resource<Event>> response = new ResponseEntity<Resource<Event>>(resource, HttpStatus.NOT_ACCEPTABLE);
			((ConfigurableApplicationContext)ctx).close();
			return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus updateEvent(@PathVariable("idEvent") int eventId, @RequestBody Event event) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		
		if (event.getId() != eventId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcEventDao.updateEvent(event);
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus deleteEvent(@PathVariable("idEvent") int eventId, @RequestBody Event event) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		
		if (event.getId() != eventId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			if (event.getEventTemplateId() == 0) {
				jdbcEventDao.deleteEventById(event.getId());
			} else {
				jdbcEventDao.deleteEventsByTemplateId(event.getEventTemplateId());
				jdbcEventDao.deleteEventTemplateById(event.getEventTemplateId());
			}
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Resource<GList>>> getGLists(@PathVariable("idEvent") int eventId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		List<GList> glists = jdbcGListDao.getAllGListsByEventId(eventId);
		List<Resource<GList>> resources = new ArrayList<Resource<GList>>();
		GListResourceAssembler glra = new GListResourceAssembler();
		for (int i=0;i<glists.size();i++) {
			Resource<GList> resource = glra.toResource(glists.get(i));
			resources.add(resource);
		}
		ResponseEntity<List<Resource<GList>>> response = new ResponseEntity<List<Resource<GList>>>(resources, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<GList>> addGlist(@RequestBody GList glist) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		GListResourceAssembler glra = new GListResourceAssembler();
		try {
			int newId = jdbcGListDao.updateGList(glist);
			glist.setId(newId);
			Resource<GList> resource = glra.toResource(glist);
			ResponseEntity<Resource<GList>> response = new ResponseEntity<Resource<GList>>(resource, HttpStatus.ACCEPTED);
			((ConfigurableApplicationContext)ctx).close();
			return response;

		} catch (Exception e) {
			Resource<GList> resource = glra.toResource(null);
			ResponseEntity<Resource<GList>> response = new ResponseEntity<Resource<GList>>(resource, HttpStatus.NOT_ACCEPTABLE);
			((ConfigurableApplicationContext)ctx).close();
			return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGlist}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus updateGlist(@PathVariable("idGlist") int glistId, @RequestBody GList glist) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		
		if (glist.getId() != glistId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcGListDao.updateGList(glist);
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGlist}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus deleteGlist(@PathVariable("idGlist") int glistId, @RequestBody GList glist) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		
		if (glist.getId() != glistId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcGListDao.deleteGListById(glist.getId());
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Resource<Reservation>>> getReservations(@PathVariable("idGList") int glistId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		List<Reservation> reservations = jdbcReservationDao.getAllReservationsByGListId(glistId);
		List<Resource<Reservation>> resources = new ArrayList<Resource<Reservation>>();
		ReservationResourceAssembler rra = new ReservationResourceAssembler();
		for (int i=0;i<reservations.size();i++) {
			Resource<Reservation> resource = rra.toResource(reservations.get(i));
			resources.add(resource);
		}
		ResponseEntity<List<Resource<Reservation>>> response = new ResponseEntity<List<Resource<Reservation>>>(resources, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Reservation>> addReservation(@RequestBody Reservation reservation) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		ReservationResourceAssembler rra = new ReservationResourceAssembler();
		
		try {
			int newId = jdbcReservationDao.updateReservation(reservation);
			reservation.setId(newId);
			Resource<Reservation> resource = rra.toResource(reservation);
			ResponseEntity<Resource<Reservation>> response = new ResponseEntity<Resource<Reservation>>(resource, HttpStatus.ACCEPTED);
			((ConfigurableApplicationContext)ctx).close();
			return response;

		} catch (Exception e) {
			Resource<Reservation> resource = rra.toResource(null);
			ResponseEntity<Resource<Reservation>> response = new ResponseEntity<Resource<Reservation>>(resource, HttpStatus.NOT_ACCEPTABLE);
			((ConfigurableApplicationContext)ctx).close();
			return response;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resource<Reservation>> getReservation(@PathVariable("idReservation") int reservationId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		Reservation reservation = jdbcReservationDao.getReservationById(reservationId);
		ReservationResourceAssembler rra = new ReservationResourceAssembler();
		Resource<Reservation> resource = rra.toResource(reservation);
		ResponseEntity<Resource<Reservation>> response = new ResponseEntity<Resource<Reservation>>(resource, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus updateReservation(@PathVariable("idReservation") int reservationId, @RequestBody Reservation reservation) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		
		if (reservation.getId() != reservationId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcReservationDao.updateReservation(reservation);
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.ACCEPTED;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HttpStatus deleteReservation(@PathVariable("idReservation") int reservationId, @RequestBody Reservation reservation) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		
		if (reservation.getId() != reservationId) {
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NOT_ACCEPTABLE;
		}
		
		try {
			jdbcReservationDao.deleteReservationById(reservation.getId());
			((ConfigurableApplicationContext)ctx).close();
			return HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return HttpStatus.NOT_ACCEPTABLE;
		}
	}
	
	//Maybe
	
	//TODO - /public/venues/{city}
	
	//TODO = /public/venues/{id}/events
	
	//TODO - /public/venues/{id}/events/{id}/glists/0/reservations - PUT ONLY
	
	//TODO - /public/reservation/{userId}
	
}
