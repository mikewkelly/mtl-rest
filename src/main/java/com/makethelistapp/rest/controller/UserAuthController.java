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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makethelistapp.core.dao.impl.JdbcOrganizationDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcUserDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcUserRolesDaoImpl;
import com.makethelistapp.core.model.Organization;
import com.makethelistapp.core.model.User;
import com.makethelistapp.core.model.UserRoles;

@Controller
@RequestMapping("/api/auth")
public class UserAuthController {
	
	@RequestMapping(method = RequestMethod.GET, value = "")
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/organization")
	@ResponseBody
	public ResponseEntity<List<Organization>> getOrganizations() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		List<Organization> organizations = jdbcOrganizationDao.getAllOrganizations();
		ResponseEntity<List<Organization>> response = new ResponseEntity<List<Organization>>(organizations, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idOrganization}")
	@ResponseBody
	public ResponseEntity<Organization> getOrganization(@PathVariable("idOrganization") int orgId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		Organization organization = jdbcOrganizationDao.getOrganizationById(orgId);
		ResponseEntity<Organization> response = new ResponseEntity<Organization>(organization, HttpStatus.OK);
		((ConfigurableApplicationContext)ctx).close();
		return response;
	}
	
	
	
//	@RequestMapping(method = RequestMethod.GET, value = "/{idUser}")
//	@ResponseBody
//	public User getUser(@PathVariable("idUser") int idUser) {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//		JdbcUserDaoImpl jdbcUserDao = ctx.getBean("jdbcUserDaoImpl", JdbcUserDaoImpl.class);
//		User user = jdbcUserDao.getUserById(idUser);
//		((ConfigurableApplicationContext)ctx).close();
//		return user;
//	}
//	

	

}
