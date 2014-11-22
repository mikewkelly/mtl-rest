package com.makethelistapp.rest.controller;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

@Controller
@RequestMapping("/api")
public class RestController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/start")
	@ResponseBody
	public ResponseEntity<List<UserRoles>> getUserOrganizations() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcUserRolesDaoImpl jdbcUserRolesDao = ctx.getBean("jdbcUserRolesDaoImpl", JdbcUserRolesDaoImpl.class);
		List<UserRoles> userRoles = jdbcUserRolesDao.getAllUserRolesByUsername(name);
		
		((ConfigurableApplicationContext)ctx).close();
		ResponseEntity<List<UserRoles>> response = new ResponseEntity<List<UserRoles>>(userRoles, HttpStatus.OK);
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
	
	
//	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues")
//	@ResponseBody
//	public ResponseEntity<Organization> getVenues(@PathVariable("idOrganization") int orgId) {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
//		Organization organization = jdbcOrganizationDao.getOrganizationById(orgId);
//		ResponseEntity<Organization> response = new ResponseEntity<Organization>(organization, HttpStatus.OK);
//		((ConfigurableApplicationContext)ctx).close();
//		return response;
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}")
	@ResponseBody
	public ResponseEntity<Venue> getVenues(@PathVariable("idOrganization") int orgId, @PathVariable("idVenue") int venueId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcVenueDaoImpl jdbcVenueDao = ctx.getBean("jdbcVenueDaoImpl", JdbcVenueDaoImpl.class);
		Venue venue = jdbcVenueDao.getVenueById(venueId);
		ResponseEntity<Venue> response = new ResponseEntity<Venue>(venue, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	

	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events")
	@ResponseBody
	public ResponseEntity<List<Event>> getEvents(@PathVariable("idOrganization") int orgId, @PathVariable("idVenue") int venueId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcEventDaoImpl jdbcEventDao = ctx.getBean("jdbcEventDaoImpl", JdbcEventDaoImpl.class);
		List<Event> events = jdbcEventDao.getAllEventsByVenueId(venueId);
		ResponseEntity<List<Event>> response = new ResponseEntity<List<Event>>(events, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists")
	@ResponseBody
	public ResponseEntity<List<GList>> getGLists(@PathVariable("idOrganization") int orgId, @PathVariable("idVenue") int venueId, @PathVariable("idEvent") int eventId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcGListDaoImpl jdbcGListDao = ctx.getBean("jdbcGListDaoImpl", JdbcGListDaoImpl.class);
		List<GList> glists = jdbcGListDao.getAllGListsByEventId(eventId);
		ResponseEntity<List<GList>> response = new ResponseEntity<List<GList>>(glists, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}

	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations")
	@ResponseBody
	public ResponseEntity<List<Reservation>> getReservations(@PathVariable("idOrganization") int orgId, @PathVariable("idVenue") int venueId, @PathVariable("idEvent") int eventId, @PathVariable("idGList") int glistId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		List<Reservation> reservations = jdbcReservationDao.getAllReservationsByGListId(glistId);
		ResponseEntity<List<Reservation>> response = new ResponseEntity<List<Reservation>>(reservations, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}")
	@ResponseBody
	public ResponseEntity<Reservation> getReservation(@PathVariable("idOrganization") int orgId, @PathVariable("idVenue") int venueId, @PathVariable("idEvent") int eventId, @PathVariable("idGList") int glistId, @PathVariable("idReservation") int reservationId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		Reservation reservation = jdbcReservationDao.getReservationById(reservationId);
		ResponseEntity<Reservation> response = new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/organization/{idOrganization}/venues/{idVenue}/events/{idEvent}/glists/{idGList}/reservations/{idReservation}")
	@ResponseBody
	public HttpStatus updateReservation(@PathVariable("idOrganization") int orgId, @PathVariable("idVenue") int venueId, @PathVariable("idEvent") int eventId, @PathVariable("idGList") int glistId, @PathVariable("idReservation") int reservationId, @RequestBody Reservation reservation) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcReservationDaoImpl jdbcReservationDao = ctx.getBean("jdbcReservationDaoImpl", JdbcReservationDaoImpl.class);
		
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
