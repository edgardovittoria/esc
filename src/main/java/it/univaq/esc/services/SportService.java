package it.univaq.esc.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.dto.SportDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.SportRepository;

@Service
public class SportService {

	@Autowired
	private SportRepository sportRepo;

	
	private Set<Sport> listaSport;


	private static SportService sportServiceInstance;



	private SportService() {

	}

	@PostConstruct
	private void inizializzaListaSport() {
		this.listaSport = new HashSet<>(sportRepo.findAll()); 
	}

	public static SportService getInstance(){
		if (sportServiceInstance == null) {
			sportServiceInstance = new SportService();
		}
		return sportServiceInstance;
	}

	/**
	 * Associa un impianto allo sport.
	 */
	public void addImpianto(Sport sport, Impianto impianto) {
		sport.getImpianti().add(impianto);

		// Se non funziona la concatenazione precedente si può fare così
		// List<Impianto> impiantiSport = sport.getImpianti();
		// impiantiSport.add(impianto);
		// sport.setImpianti(impiantiSport);

	}

	/**
	 * Associa un istruttore allo sport.
	 */
	public void addIstruttore(Sport sport, Istruttore istruttore) {
		sport.getIstruttori().add(istruttore);
	}

	/**
	 * Rimuove un impianto tra quelli associati allo sport.
	 */
	public boolean removeImpianto(Sport sport, Impianto impianto) {
		if (sport.getImpianti().contains(impianto)) {
			sport.getImpianti().remove(impianto);
			return true;
		}
		return false;
	}

	/**
	 * Rimuove un istruttore tra quelli associati allo sport.
	 */
	public boolean removeIstruttore(Sport sport, Istruttore istruttore) {
		if (sport.getIstruttori().contains(istruttore)) {
			sport.getIstruttori().remove(istruttore);
			return true;
		}
		return false;
	}

	/**
	 * Restituice il numero di impianti associati allo sport passato come parametro.
	 */
	public int numberOfImpianti(int sportID) {
		Sport sport = getSport(sportID);
		if (sport.getImpianti().isEmpty()) {
			return 0;
		} else {
			return sport.getImpianti().size();
		}
	}

	/**
	 * Restituice il numero di istruttori associati allo sport.
	 */
	public int numberOfIstruttori(int sportID) {
		Sport sport = getSport(sportID);
		if (sport.getIstruttori().isEmpty()) {
			return 0;
		} else {
			return sport.getIstruttori().size();
		}
	}

	public SportDTO toDTO(int sportID) {
		Sport sport = getSport(sportID);
		SportDTO sportDTO = new SportDTO();
		sportDTO.setNome(sport.getNome());
		sportDTO.setDescrizione(sport.getSportDescription());

		return sportDTO;
		
	}

	public SportDTO toDTO(Sport sport) {
		SportDTO sportDTO = new SportDTO();
		sportDTO.setNome(sport.getNome());
		sportDTO.setDescrizione(sport.getSportDescription());

		return sportDTO;
		
	}

	public Set<SportDTO> toDTO(Set<Sport> listaSport) {
		Logger logger = LoggerFactory.getLogger(SportivoService.class);
		Set<SportDTO> listaSportDTO = new HashSet<SportDTO>(); 
		for (Sport sport : listaSport) {
			SportDTO sportDTO = new SportDTO();
			sportDTO.setNome(sport.getNome());
			sportDTO.setDescrizione(sport.getSportDescription());
			sportDTO.setImpianti((ImpiantoService.toDTO(sport.getImpianti())));
			listaSportDTO.add(sportDTO);
			logger.info(listaSportDTO.toString());
		}

		return listaSportDTO;
		
	}

	public Sport getSport(int sportID){
		return sportRepo.findById(sportID).get();
	}

	public Sport getSport(String description){
		return sportRepo.findBySportDescription(description);
	}

	public Set<Sport> getAllSport(){
		return this.listaSport;
	}
	
}
