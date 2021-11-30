package com.springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@EnableWebMvc
@Configuration
@ComponentScan("com.springsecurity")
@PropertySource("classpath:persistence-mysql.properties")
public class Config 
{
	@Autowired
	private Environment env;
	
	@Bean
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/view/");
		vr.setSuffix(".jsp");
		return vr;
	}
	
	@Bean
	public DataSource dataSource()
	{
		ComboPooledDataSource sds = new ComboPooledDataSource();
		
		try
		{
			sds.setDriverClass(env.getProperty("jdbc.driver"));
		}
		catch (Exception e) 
		{
			throw new RuntimeException();
		}
		
		sds.setJdbcUrl(env.getProperty("jdbc.url"));
		sds.setUser(env.getProperty("jdbc.user"));
		sds.setPassword(env.getProperty("jdbc.password"));
		
		sds.setInitialPoolSize(Integer.parseInt(env.getProperty("connection.pool.initialPoolSize")));
		sds.setMinPoolSize(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));
		sds.setMaxPoolSize(Integer.parseInt(env.getProperty("connection.pool.maxPoolSize")));
		sds.setMaxIdleTime(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));
		
		return sds;
	}
	
	/*@Bean
	public DataSource dataSource()
	{
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/springsecuritypt?useSSL=false");
		ds.setUsername("root");
		ds.setPassword("root");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		return ds;
	}*/
	
	
	
}
