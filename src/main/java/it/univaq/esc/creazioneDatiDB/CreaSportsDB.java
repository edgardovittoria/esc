package it.univaq.esc.creazioneDatiDB;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.SportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Singleton
@AllArgsConstructor
public class CreaSportsDB {

	private SportRepository sportRepository;
	
	
	public void creaSports() {
		Sport calcetto = new Sport("calcetto", 10, 5);
		Sport tennis = new Sport("tennis", 2, 2);
		Sport pallavolo = new Sport("pallavolo", 12, 6);

		sportRepository.save(calcetto);
		sportRepository.save(tennis);
		sportRepository.save(pallavolo);
	}
	
	
	public List<Sport> getListaSportCreati(){
		return sportRepository.findAll();
	}
	
	public Sport getSportConNome(String nomeSport) {
		List<Sport> sports = sportRepository.findAll();
		for(Sport sport : sports) {
			if(sport.getNome().equals(nomeSport)) {
				return sport;
			}
		}
		return null;
	}
	
}
