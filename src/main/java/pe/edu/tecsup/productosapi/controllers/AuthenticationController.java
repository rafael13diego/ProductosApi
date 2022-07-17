package pe.edu.tecsup.productosapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.tecsup.productosapi.entities.Usuario;
import pe.edu.tecsup.productosapi.services.AuthenticationService;
import pe.edu.tecsup.productosapi.services.JwtTokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
    private AuthenticationService authenticationService;
	
	@Autowired
    private JwtTokenService jwtTokenService;
	
	@PostMapping("login")
	public Usuario login(@RequestParam String username, @RequestParam String password) {
		logger.info("login("+username+", "+password+")");
		
		Usuario usuario = authenticationService.login(username, password);
		logger.info("Login success: " + usuario);
		
		String token = jwtTokenService.generateToken(usuario.getUsername());
		logger.info("Token: " + token);
		usuario.setToken(token);
		
		return usuario;
	}
	
}
