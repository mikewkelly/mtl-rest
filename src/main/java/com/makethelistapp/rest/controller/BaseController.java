package com.makethelistapp.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makethelistapp.core.dao.impl.JdbcUserDaoImpl;
import com.makethelistapp.core.dao.impl.JdbcUserRolesDaoImpl;
import com.makethelistapp.core.model.User;
import com.makethelistapp.core.model.UserRoles;
import com.makethelistapp.resource.UserRolesResourceAssembler;

@Controller
@RequestMapping("/api/base")
public class BaseController {
	
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

}
