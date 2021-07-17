package it.univaq.esc.creazioneDatiDB;

import groovy.lang.Singleton;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaBuilder;
import it.univaq.esc.repository.UtentePolisportivaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Singleton
@AllArgsConstructor
public class CreaUtentiDB {

	private CreaSportsDB creaSportsDB;
	private UtentePolisportivaRepository utentePolisportivaRepository;

	public void creaUtenti() {
		List<Sport> listaSportPraticati = new ArrayList<Sport>();
		listaSportPraticati.add(creaSportsDB.getSportConNome("tennis"));
		UtentePolisportiva sportivoPrenotante = new UtentePolisportivaBuilder("Pippo", "Franco",
				"pippofranco@bagaglino.com", "pippo").assegnaRuoloSportivo(listaSportPraticati).build();

		List<Sport> listaSportPraticati1 = new ArrayList<Sport>();
		listaSportPraticati1.add(creaSportsDB.getSportConNome("tennis"));
		listaSportPraticati1.add(creaSportsDB.getSportConNome("calcetto"));
		listaSportPraticati1.add(creaSportsDB.getSportConNome("pallavolo"));
		UtentePolisportiva sportivo1 = new UtentePolisportivaBuilder("Mario", "Rossi", "mario.rossi@gmail.com.com",
				"mario").assegnaRuoloSportivo(listaSportPraticati1).build();

		List<Sport> listaSportPraticati2 = new ArrayList<Sport>();
		listaSportPraticati2.add(creaSportsDB.getSportConNome("tennis"));
		listaSportPraticati2.add(creaSportsDB.getSportConNome("calcetto"));
		UtentePolisportiva sportivo2 = new UtentePolisportivaBuilder("Giuseppe", "Flavio", "giuseppe.flavio@gmail.com", "giuseppe")
				.assegnaRuoloSportivo(listaSportPraticati2).assegnaRuoloIstruttore(listaSportPraticati1)
				.assegnaRuoloManutentore().build();

		List<Sport> listaSportPraticati3 = new ArrayList<Sport>();
		listaSportPraticati3.add(creaSportsDB.getSportConNome("pallavolo"));
		listaSportPraticati3.add(creaSportsDB.getSportConNome("calcetto"));
		UtentePolisportiva sportivo3 = new UtentePolisportivaBuilder("Giovanni", "Storti", "giovanni.storti@gmail.com", "giovanni")
				.assegnaRuoloSportivo(listaSportPraticati3).assegnaRuoloDirettorePolisportiva().build();

		utentePolisportivaRepository.save(sportivoPrenotante);
		utentePolisportivaRepository.save(sportivo1);
		utentePolisportivaRepository.save(sportivo2);
		utentePolisportivaRepository.save(sportivo3);
	}
	
	public UtentePolisportiva getUtenteBy(String email) {
		for(UtentePolisportiva utente : utentePolisportivaRepository.findAll()) {
			if(utente.isSuaQuesta(email)) {
				return utente;
			}
		}
		return null;
	}
}
