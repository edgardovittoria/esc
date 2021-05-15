package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.SportRepository;

@Component
@Singleton
public class RegistroSport {

	@Autowired
	private SportRepository sportRepository;

	private List<Sport> listaSportPolisportiva = new ArrayList<Sport>();

	/**
	 * Costruttore della classe RegistroSport. Non invocabile direttamente poiché
	 * annotato come Singleton
	 */
	public RegistroSport() {
	}

	/**
	 * Metodo invocato immediatamente dopo l'istanziazione del registro, che popola la lista degli sport del registro 
	 * con tutti gli sport ricavati dal database.
	 */
	@PostConstruct
	public void popola() {
		this.getListaSportPolisportiva().addAll(this.getSportRepository().findAll());
	}

	/**
	 * @return il repository degli sport.
	 */
	private SportRepository getSportRepository() {
		return sportRepository;
	}

	/**
	 * @return la lista degli sport della polisportivia.
	 */
	public List<Sport> getListaSportPolisportiva() {
		return listaSportPolisportiva;
	}



	/**
	 * Aggiunge uno sport nel registro sport e nel database, se non già presente.
	 * 
	 * @param sportDaAggiungere sport da aggiugnere alla polisportiva.
	 */
	public void aggiungiSportAllaPolisportiva(Sport sportDaAggiungere) {
		boolean alreadyPresent = false;
		for (Sport sport : this.getListaSportPolisportiva()) {
			if (sport.getNome().equals(sportDaAggiungere.getNome())) {
				alreadyPresent = true;
				break;

			}
		}
		if (alreadyPresent == false) {
			this.getListaSportPolisportiva().add(sportDaAggiungere);
			this.getSportRepository().save(sportDaAggiungere);
		}
	}
	
	
	/**
	 * Ricerca uno sport tra quelli della polisportiva, sulla base del nome passato come parametro.
	 * Restituisce lo sport cercato, se presente, null altrimenti.
	 * @param nomeSport nome dello sport da ricercare.
	 * @return lo sport ricercato in base al nome se presente, null altrimenti.
	 */
	public Sport getSportByNome(String nomeSport) {
		for(Sport sport : this.getListaSportPolisportiva()) {
			if(sport.getNome().equals(nomeSport)) {
				return sport;
			}
		}
		return null;
	}

}
