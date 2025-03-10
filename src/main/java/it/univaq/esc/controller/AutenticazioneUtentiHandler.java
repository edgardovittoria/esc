package it.univaq.esc.controller;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.security.AuthenticationRequest;
import it.univaq.esc.security.AuthenticationResponse;
import it.univaq.esc.security.JwtUtil;
import it.univaq.esc.security.MyUserDetailsService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @NoArgsConstructor
public class AutenticazioneUtentiHandler {
	
	@Resource(name = "MAPPER_SINGOLO_UTENTE")
	private MapperFactory mapperFactory;

	@Autowired
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Autowired
	private JwtUtil jwtUtil;


	@RequestMapping("/autenticazione")
	@PostMapping
	@CrossOrigin
	public ResponseEntity<?> autenticazione(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Email o password Sbagliati", e);
		}

		final UserDetails userDetails = myUserDetailsService
			.loadUserByUsername(authenticationRequest.getUsername());

		UtentePolisportiva utente = registroUtentiPolisportiva.trovaUtenteInBaseAllaSua(userDetails.getUsername());
		UtentePolisportivaDTO sportivoDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(utente);

		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt, sportivoDTO));

	}
}
