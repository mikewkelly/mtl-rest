package com.makethelistapp.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.makethelistapp.core.dao.impl.JdbcUserDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcUserRolesDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcVenueDaoImpl;
import com.makethelistapp.core.model.Event;
import com.makethelistapp.core.model.GList;
import com.makethelistapp.core.model.Organization;
import com.makethelistapp.core.model.Reservation;
import com.makethelistapp.core.model.User;
import com.makethelistapp.core.model.UserRoles;
import com.makethelistapp.core.model.Venue;
import com.makethelistapp.resource.UserRolesResourceAssembler;

@Controller
@RequestMapping("/api")
public class RestController {
	
//	@RequestMapping(method = RequestMethod.GET, value = "/start")
//	@ResponseBody
//	public ResponseEntity<List<UserRoles>> getUserOrganizations() {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//	    String name = auth.getName();
//	    
//	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//		JdbcUserRolesDaoImpl jdbcUserRolesDao = ctx.getBean("jdbcUserRolesDaoImpl", JdbcUserRolesDaoImpl.class);
//		List<UserRoles> userRoles = jdbcUserRolesDao.getAllUserRolesByUsername(name);
//		//build URI and add to each role
//		UserRoles ur = userRoles.get(0);
//		//Link link = new Link("");
//		UserRolesResourceAssembler gra = new UserRolesResourceAssembler();
//		Resource<UserRoles> resource = gra.toResource(ur);
//		
//		((ConfigurableApplicationContext)ctx).close();
//		ResponseEntity<List<UserRoles>> response = new ResponseEntity<List<UserRoles>>(userRoles, HttpStatus.OK);
//		return response;
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/start")
	@ResponseBody
	public ResponseEntity<List<Resource<UserRoles>>> getUserOrganizations() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcUserRolesDaoImpl jdbcUserRolesDao = ctx.getBean("jdbcUserRolesDaoImpl", JdbcUserRolesDaoImpl.class);
		List<UserRoles> userRoles = jdbcUserRolesDao.getAllUserRolesByUsername(name);
		
		UserRolesResourceAssembler resourceAssembler = new UserRolesResourceAssembler();
		List<Resource<UserRoles>> resources = new ArrayList<Resource<UserRoles>>();
		for (int i=0;i<userRoles.size();i++) {
			UserRoles userRole = userRoles.get(i);
			Resource<UserRoles> resource = resourceAssembler.toResource(userRole);
			resources.add(resource);
		}

		((ConfigurableApplicationContext)ctx).close();
		ResponseEntity<List<Resource<UserRoles>>> response = new ResponseEntity<List<Resource<UserRoles>>>(resources, HttpStatus.OK);
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/me")
	@ResponseBody
	public ResponseEntity<User> getMe() {
		//TODO - Important
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcUserDaoImpl jdbcUserDao = ctx.getBean("jdbcUserDaoImpl", JdbcUserDaoImpl.class);
		User user = jdbcUserDao.getUserByUsername(name);
		((ConfigurableApplicationContext)ctx).close();
		ResponseEntity<User> response = new ResponseEntity<User>(user, HttpStatus.OK);
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}")
	@ResponseBody
	public ResponseEntity<Organization> getOrganization(@PathVariable("idOrganization") int orgId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		Organization organization = jdbcOrganizationDao.getOrganizationById(orgId);
		ResponseEntity<Organization> response = new ResponseEntity<Organization>(organization, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/organization")
	@ResponseBody
	public ResponseEntity<Integer> addOrganization(@RequestBody Organization organization) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);

		try {
			int newId = jdbcOrganizationDao.updateOrganization(organization);
			((ConfigurableApplicationContext)ctx).close();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return new ResponseEntity<Integer>(-1, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organization/{idOrganization}")
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
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues")
	@ResponseBody
	public ResponseEntity<List<Venue>> getVenues(@PathVariable("idOrganization") int orgId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		List<Venue> venues = jdbcVenueDao.getAllVenuesByOrganizationId(orgId);
		ResponseEntity<List<Venue>> response = new ResponseEntity<List<Venue>>(venues, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}")
	@ResponseBody
	public ResponseEntity<Venue> getVenue(@PathVariable("idVenue") int venueId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		Venue venue = jdbcVenueDao.getVenueById(venueId);
		ResponseEntity<Venue> response = new ResponseEntity<Venue>(venue, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/organization/{idOrganization}/venues")
	@ResponseBody
	public ResponseEntity<Integer> addVenue(@RequestBody Venue venue) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);

		try {
			int newId = jdbcVenueDao.updateVenue(venue);
			((ConfigurableApplicationContext)ctx).close();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return new ResponseEntity<Integer>(-1, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organization/{idOrganization}/venues/{idVenue}")
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events")
	@ResponseBody
	public ResponseEntity<List<Event>> getEvents(@PathVariable("idVenue") int venueId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		List<Event> events = jdbcEventDao.getAllEventsByVenueId(venueId);
		ResponseEntity<List<Event>> response = new ResponseEntity<List<Event>>(events, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}")
	@ResponseBody
	public ResponseEntity<Event> getEvent(@PathVariable("idEvent") int eventId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		Event event = jdbcEventDao.getEventById(eventId);
		ResponseEntity<Event> response = new ResponseEntity<Event>(event, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/organization/{idOrganization}/venues/{idVenue}/events")
	@ResponseBody
	public ResponseEntity<Integer> addEvent(@RequestBody Event event) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		
		try {
			int newId = jdbcEventDao.updateEvent(event);
			((ConfigurableApplicationContext)ctx).close();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return new ResponseEntity<Integer>(-1, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}")
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
	

	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists")
	@ResponseBody
	public ResponseEntity<List<GList>> getGLists(@PathVariable("idEvent") int eventId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		List<GList> glists = jdbcGListDao.getAllGListsByEventId(eventId);
		ResponseEntity<List<GList>> response = new ResponseEntity<List<GList>>(glists, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists")
	@ResponseBody
	public ResponseEntity<Integer> addGlist(@RequestBody GList glist) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		
		try {
			int newId = jdbcGListDao.updateGList(glist);
			((ConfigurableApplicationContext)ctx).close();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return new ResponseEntity<Integer>(-1, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGlist}")
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

	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations")
	@ResponseBody
	public ResponseEntity<List<Reservation>> getReservations(@PathVariable("idGList") int glistId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		List<Reservation> reservations = jdbcReservationDao.getAllReservationsByGListId(glistId);
		ResponseEntity<List<Reservation>> response = new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations")
	@ResponseBody
	public ResponseEntity<Integer> addReservation(@RequestBody Reservation reservation) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		
		try {
			int newId = jdbcReservationDao.updateReservation(reservation);
			((ConfigurableApplicationContext)ctx).close();
			return new ResponseEntity<Integer>(newId, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			((ConfigurableApplicationContext)ctx).close();
			e.printStackTrace();
			return new ResponseEntity<Integer>(-1, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}")
	@ResponseBody
	public ResponseEntity<Reservation> getReservation(@PathVariable("idReservation") int reservationId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		Reservation reservation = jdbcReservationDao.getReservationById(reservationId);
		ResponseEntity<Reservation> response = new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}")
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
	
	//Maybe
	
	//TODO - /public/venues/{city}
	
	//TODO = /public/venues/{id}/events
	
	//TODO - /public/venues/{id}/events/{id}/glists/0/reservations - PUT ONLY
	
	//TODO - /public/reservation/{userId}
	
}
