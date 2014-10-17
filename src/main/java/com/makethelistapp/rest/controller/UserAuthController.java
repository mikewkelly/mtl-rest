package com.makethelistapp.rest.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.makethelistapp.core.dao.impl.JdbcUserDaoImpl;
import com.makethelistapp.core.model.User;

@Controller
@RequestMapping("/api/auth")
public class UserAuthController {


//	@RequestMapping(method = RequestMethod.GET, value = "/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
//	ResponseEntity<User> getUserById(@PathVariable int idUser) throws Exception {
//
//		JdbcUserDaoImpl jdbcUserDao = new JdbcUserDaoImpl();
//		User user = jdbcUserDao.getUserById(idUser);
//		
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{idUser}")
	@ResponseBody
	public User getUser(@PathVariable("idUser") int idUser) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
		JdbcUserDaoImpl jdbcUserDao = ctx.getBean("jdbcUserDaoImpl", JdbcUserDaoImpl.class);
		
		//JdbcUserDaoImpl jdbcUserDao = new JdbcUserDaoImpl();
		User user = jdbcUserDao.getUserById(idUser);	
		return user;
	}
	
	
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public Person getPerson(@PathVariable("id") Long personId) {
//	return personDao.getPerson(personId);
//
//	}
	
}
