package it.univaq.esc.creazioneDatiDB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor 
public class PopolaDB {

	private CreaSportsDB creaSportsDB;
	private CreaUtentiDB creaUtentiDB;
	private CreaSquadreDB creaSquadreDB;
	private CreaImpiantiDB creaImpiantiDB;
	private CreaPrenotabiliDescrizioneDB creaPrenotabiliDescrizioneDB;
	private CreaAppuntamentiPrenotazioniDB creaAppuntamentiPrenotazioniDB; 
	

	public void popola() {
		creaSportsDB.creaSports();
		creaUtentiDB.creaUtenti();
		creaSquadreDB.creaSquadre();
		creaImpiantiDB.creaImpianti();
		creaPrenotabiliDescrizioneDB.creaPrenotabiliDescrizione();
		creaAppuntamentiPrenotazioniDB.creaAppuntamentiEPrenotazioni();
	}

}