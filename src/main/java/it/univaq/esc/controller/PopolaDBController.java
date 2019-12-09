package it.univaq.esc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.SportivoRepository;

@RestController
@RequestMapping("/test/popolaDB")
public class PopolaDBController {

	@Autowired
	private SportivoRepository sportivoRepository;
	
	@GetMapping("/sportivo")
	public void addSportivo() {
		String[] name = new String[] {"Giovanni", "Marco", "Andrea", "Pietro", "Vincenzo", "Mimmo", "Gianni", "Ottorino", "Daniele", "Simone"};
		String[] cognomi = new String[] {"DiGiovanni", "DiMarco", "DIAndrea", "DiPietro", "DiVincenzo", "DiMimmo", "DiGianni", "DiOttorino", "DiDaniele", "DiSimone"};
		for(int i = 0; i<10;i++) {
			Sportivo sportivo = new Sportivo();
			sportivo.setNome(name[i]);
			sportivo.setCognome(cognomi[i]);
			sportivoRepository.save(sportivo);
		}
	}
	
}
