package com.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.authorizeRequests()
			.antMatchers("/").hasRole("EMPLOYEE")
			.antMatchers("/manager/**").hasRole("MANAGER")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.and()
			.formLogin()
			.loginPage("/ShowLoginForm")
			.loginProcessingUrl("/AuthenticateUser")
			.permitAll()
		.and()
			.logout()
			.permitAll()
			.and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied");			
	}
}
