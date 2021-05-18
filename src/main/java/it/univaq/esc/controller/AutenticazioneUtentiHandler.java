package it.univaq.esc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.security.AuthenticationRequest;
import it.univaq.esc.security.AuthenticationResponse;
import it.univaq.esc.security.JwtUtil;
import it.univaq.esc.security.MyUserDetailsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @AllArgsConstructor
public class AutenticazioneUtentiHandler {

	private RegistroUtentiPolisportiva registroUtentiPolisportiva;

	private AuthenticationManager authenticationManager;

	private MyUserDetailsService myUserDetailsService;

	private JwtUtil jwtUtil;

	// @RequestMapping("/login")
	// @PostMapping
	// @CrossOrigin
	// public ResponseEntity<UtentePolisportivaAbstract> login(@RequestBody
	// Map<String, String> mappaAutenticazione) {
	// UtentePolisportivaAbstract utente =
	// this.getRegistroUtentiPolisportiva().getUtenteByEmail(mappaAutenticazione.get("emailUtente"));
	// if(utente == null &&
	// utente.getProprieta().get("password").equals(mappaAutenticazione.get("password")))
	// {
	// return new ResponseEntity<UtentePolisportivaAbstract>(utente, HttpStatus.OK);
	// }
	// else if(utente == null){
	// return new ResponseEntity<UtentePolisportivaAbstract>(HttpStatus.NOT_FOUND);
	// }
	// else {
	// return new
	// ResponseEntity<UtentePolisportivaAbstract>(HttpStatus.UNAUTHORIZED);
	// }
	// }

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

		UtentePolisportivaAbstract utente = registroUtentiPolisportiva.getUtenteByEmail(userDetails.getUsername());
		SportivoDTO sportivoDTO = new SportivoDTO();
		sportivoDTO.impostaValoriDTO(utente);

		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt, sportivoDTO));

	}
}
