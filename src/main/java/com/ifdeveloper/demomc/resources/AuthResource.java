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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Autorização", description = "Recurso para operação de Autorização")
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@ApiOperation(value = "Atualiza o token")
	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSecurity userSecurity = UserService.authenticated();
		String token = jwtUtil.generateToken(userSecurity.getUsername());
		response.addHeader("Authorization", "Token ".concat(token));
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

}
