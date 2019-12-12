package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.PrenotazioneRepository;
@Component
public class RegistroPrenotazioni {

	private List<Prenotazione> prenotazioni;
	private static RegistroPrenotazioni registroPrenotazioniInstance;

	@Autowired 
	private PrenotazioneRepository pr;
	

	private RegistroPrenotazioni(){
		
	}
	
	public static RegistroPrenotazioni getInstance() {
		if (registroPrenotazioniInstance == null){
			registroPrenotazioniInstance = new RegistroPrenotazioni();
		}

		return registroPrenotazioniInstance;
	}

	@PostConstruct
	private void inizializzaSports(){
		prenotazioni = pr.findAll();
	}


	public List<Prenotazione> getPrenotazioni() {
		return prenotazioni;
	}

	public Prenotazione getPrenotazione(int IDPrenotazione) {
		return this.prenotazioni.get(IDPrenotazione);
	}
	
	public void addPrenotazione(Prenotazione prenotazione) {
		this.prenotazioni.add(prenotazione);
	}
		
	public ArrayList<Prenotazione> getPrenotazioniAperte(Sportivo sportivoPrenotante /*, parametro???*/){
			return null;
	}
	
	public int getLastIDPrenotazione() {
		// Logger loggerPren = LoggerFactory.getLogger(RegistroPrenotazioni.class);
		// loggerPren.info(""+prenotazioni.size());
		if(prenotazioni.size() != 0){
			return this.prenotazioni.get(prenotazioni.size()-1).getIDPrenotazione();
		}
		else {
			return 0 ;
		}
		
	}
	
}
