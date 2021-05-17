package it.univaq.esc.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@Getter @Setter @AllArgsConstructor
public class AutenticazioneUtentiHandler {
	
	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private RegistroUtentiPolisportiva registroUtentiPolisportiva;
	
	

	@RequestMapping("/login")
	@PostMapping
	@CrossOrigin
	public ResponseEntity<UtentePolisportivaAbstract> login(@RequestBody Map<String, String> mappaAutenticazione) {
		UtentePolisportivaAbstract utente = this.getRegistroUtentiPolisportiva().getUtenteByEmail(mappaAutenticazione.get("emailUtente"));
		if(utente == null && utente.getProprieta().get("password").equals(mappaAutenticazione.get("password"))) {
			return new ResponseEntity<UtentePolisportivaAbstract>(utente, HttpStatus.OK);
		}
		else if(utente == null){
			return new ResponseEntity<UtentePolisportivaAbstract>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<UtentePolisportivaAbstract>(HttpStatus.UNAUTHORIZED);
		}
	}
}
