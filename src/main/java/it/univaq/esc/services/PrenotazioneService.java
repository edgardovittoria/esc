package it.univaq.esc.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {

	@Autowired
	private PrenotazioneRepository prenotazioneRepo;
	
	private List<Prenotazione> prenotazioni;
	
	public void aggiornaNumPostiLiberi(Prenotazione prenotazione) {
		prenotazione.setNumPostiLiberi(prenotazione.getNumPostiLiberi() - 1);
	}
	public void aggiornaNumPartecipanti(Prenotazione prenotazione) {
		prenotazione.setNumPartecipanti(prenotazione.getNumPartecipanti() - 1);
    }

    public void generaQuotaPartecipazione() {

    }

    public boolean confermaPrenotazione(List<Object> parametri) {
        return false;
    }
    
    public int getLastIDPrenotazione() {
		// Logger loggerPren = LoggerFactory.getLogger(RegistroPrenotazioni.class);
		// loggerPren.info(""+prenotazioni.size());
		if(prenotazioni.size() != 0){
			return prenotazioni.get(prenotazioni.size()-1).getIDPrenotazione();
		}
		else {
			return 0 ;
		}
		
	}
    
    @PostConstruct
	private void inizializzaListaPrenotazioni(){
		prenotazioni = prenotazioneRepo.findAll();
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

}
