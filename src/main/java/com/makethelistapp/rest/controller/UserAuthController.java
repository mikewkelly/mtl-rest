package com.makethelistapp.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
//	@RequestMapping(method = RequestMethod.GET, value = "/count")
//	@ResponseBody
//	public int getCount() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//		JdbcUserDaoImpl jdbcUserDao = ctx.getBean("jdbcUserDaoImpl", JdbcUserDaoImpl.class);
//		int userCount = jdbcUserDao.getUserCount();
//		((ConfigurableApplicationContext)ctx).close();
//		return userCount;
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/me")
	@ResponseBody
	public User getMe() {
		//TODO - Important
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcUserDaoImpl jdbcUserDao = ctx.getBean("jdbcUserDaoImpl", JdbcUserDaoImpl.class);
		User user = jdbcUserDao.getUserByUsername(name);
		((ConfigurableApplicationContext)ctx).close();
		return user;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	@ResponseBody
	public List<Organization> getUserOrganizations() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    
	    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcUserRolesDaoImpl jdbcUserRolesDao = ctx.getBean("jdbcUserRolesDaoImpl", JdbcUserRolesDaoImpl.class);
		List<UserRoles> userRoles = jdbcUserRolesDao.getAllUserRolesByUsername(name);
		JdbcOrganizationDaoImpl jdbcOrganizationDao = ctx.getBean("jdbcOrganizationDaoImpl", JdbcOrganizationDaoImpl.class);
		
		List<Organization> organizations = new ArrayList<Organization>();
		int length = userRoles.size();
		for (int i=0;i<length;i++) {
			int orgId = userRoles.get(i).getOrganizationId();
			Organization org = jdbcOrganizationDao.getOrganizationById(orgId);
			organizations.add(org);
		}
		
		
		((ConfigurableApplicationContext)ctx).close();
		
		return organizations;
	}
	
	
//	@RequestMapping(method = RequestMethod.GET, value = "/users")
//	@ResponseBody
//	public List<User> getUsers() {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//		JdbcUserDaoImpl jdbcUserDao = ctx.getBean("jdbcUserDaoImpl", JdbcUserDaoImpl.class);
//		List<User> users = jdbcUserDao.getAllUsers();
//		((ConfigurableApplicationContext)ctx).close();
//		return users;
//	}
	

}
