package pe.edu.tecsup.productosapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.tecsup.productosapi.entities.Usuario;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@GetMapping
	public Usuario profile(@AuthenticationPrincipal Usuario usuario) {
		logger.info("profile("+usuario+")");
		
		return usuario;
	}
	
}

