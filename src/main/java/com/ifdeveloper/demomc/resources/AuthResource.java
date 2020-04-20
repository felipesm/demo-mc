package com.ifdeveloper.demomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ifdeveloper.demomc.security.JWTUtil;
import com.ifdeveloper.demomc.security.UserSecurity;
import com.ifdeveloper.demomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSecurity userSecurity = UserService.authenticated();
		String token = jwtUtil.generateToken(userSecurity.getUsername());
		response.addHeader("Authorization", "Token ".concat(token));
		return ResponseEntity.noContent().build();
	}

}
