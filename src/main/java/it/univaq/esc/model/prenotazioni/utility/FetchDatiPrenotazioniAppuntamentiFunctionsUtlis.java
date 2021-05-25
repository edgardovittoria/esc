package it.univaq.esc.model.prenotazioni.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import lombok.NoArgsConstructor;
@Service
@NoArgsConstructor
public class FetchDatiPrenotazioniAppuntamentiFunctionsUtlis {
	

	
	public static void rimuoviDoppioniSpecificheLezioniInPrenotazioneCorso(List<Prenotazione> listaPrenotazioni) {
		/*
         * Poiché nel caso dei corsi le specifiche delle lezioni si trovano dentro la specifica corso come voluto,
         * ma vengono anche inserite direttamente nella prenotazione dal database, 
         * andiamo ad eliminare queste ultime che sono dei doppioni indesiderati.
         */
        for(Prenotazione prenotazione : listaPrenotazioni) {
        	boolean isCorso = false;
        	for(PrenotazioneSpecs specs : prenotazione.getListaSpecifichePrenotazione()) {
        		if (specs.getTipoPrenotazione().equals(TipiPrenotazione.CORSO.toString())) {
        			isCorso = true;
        		}
        	}
        	if(isCorso) {
        		prenotazione.getListaSpecifichePrenotazione().removeIf((spec) -> !spec.getTipoPrenotazione().equals(TipiPrenotazione.CORSO.toString()));
        	}
        }
		
	}
	
	
	
	public static List<Prenotazione> getPrenotazioniAssociateAListaAppuntamenti(List<Appuntamento> listaAppuntamenti){
		Set<Prenotazione> setPrenotazioni = new HashSet<Prenotazione>();
		listaAppuntamenti.forEach((appuntamento) -> setPrenotazioni.add(appuntamento.getPrenotazionePrincipale()));
		List<Prenotazione> prenotazioniList = new ArrayList<Prenotazione>(setPrenotazioni);
		
		return prenotazioniList;
	}
}
