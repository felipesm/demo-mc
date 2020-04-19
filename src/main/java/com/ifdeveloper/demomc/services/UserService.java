package com.ifdeveloper.demomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ifdeveloper.demomc.security.UserSecurity;

public class UserService {
	
	public static UserSecurity authenticated() {
		try {
			return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
