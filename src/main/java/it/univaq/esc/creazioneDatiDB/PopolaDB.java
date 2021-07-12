package it.univaq.esc.creazioneDatiDB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Valuta;
import it.univaq.esc.model.Valute;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaBuilder;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.PrenotazioneRepository;
import it.univaq.esc.repository.SportRepository;
import it.univaq.esc.repository.SquadraRepository;
import it.univaq.esc.repository.UtentePolisportivaRepository;
import it.univaq.esc.repository.ValutaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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