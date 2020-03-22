package com.ifdeveloper.demomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ifdeveloper.demomc.services.DBService;
import com.ifdeveloper.demomc.services.MockMailService;
import com.ifdeveloper.demomc.services.interfaces.EmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instanciarDataBase() throws ParseException {
		
		dbService.instanciarTestDataBase();		
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockMailService();
	}
}
