package com.makethelistapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
  @Override
  protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	  DataSource dataSource = dataSource();
	  auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery(
			"select username, userpassword, enabled from user where username=?")
		.authoritiesByUsernameQuery(
			"select username, role from user_roles where username=?");
  }
  
  //TODO: change this to match API URI structure

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeUrls()
        .antMatchers("/**").access("hasRole('ROLE_ADMIN')")
        .anyRequest().authenticated()
        .and()
        .httpBasic();
  }
  
  @Bean(name = "dataSource2")
	public DriverManagerDataSource dataSource() {
	    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/mtl");
	    driverManagerDataSource.setUsername("root");
	    driverManagerDataSource.setPassword("root");
	    return driverManagerDataSource;
	}
}
