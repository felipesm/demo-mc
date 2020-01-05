package com.ifdeveloper.demomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ifdeveloper.demomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String configDataBase;
	
	@Bean
	public boolean instanciarDataBase() throws ParseException {
		
		if ("create".equals(configDataBase)) {
			dbService.instanciarTestDataBase();
			return true;
		}
		
		return false;
	}
}
