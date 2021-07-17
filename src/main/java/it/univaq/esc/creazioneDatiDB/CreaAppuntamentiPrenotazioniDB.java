package it.univaq.esc.creazioneDatiDB;

import groovy.lang.Singleton;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Singleton
@Setter(value = AccessLevel.PRIVATE)
public class CreaAppuntamentiPrenotazioniDB {

	private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;
	@Resource(name = "ELEMENTI_PRENOTAZIONE_SINGOLO_UTENTE")
	private ElementiPrenotazioneFactory elementiPrenotazioneFactory;
	private CreaSportsDB creaSportsDB;
	private CreaUtentiDB creaUtentiDB;
	private CreaImpiantiDB creaImpiantiDB;
	private AppuntamentoRepository appuntamentoRepository;
	
	public CreaAppuntamentiPrenotazioniDB(CatalogoPrenotabili catalogoPrenotabili, CreaSportsDB creaSportsDB, CreaUtentiDB creaUtentiDB, CreaImpiantiDB creaImpiantiDB, AppuntamentoRepository appuntamentoRepository) {
		setListinoPrezziDescrizioniPolisportiva(catalogoPrenotabili);
		setCreaSportsDB(creaSportsDB);
		setCreaUtentiDB(creaUtentiDB);
		setAppuntamentoRepository(appuntamentoRepository);
		setCreaImpiantiDB(creaImpiantiDB);
	}
	
	public void creaAppuntamentiEPrenotazioni() {
		
		Sport tennis = creaSportsDB.getSportConNome("tennis");
		
		UtentePolisportiva sportivo1 = creaUtentiDB.getUtenteBy("poppins@bianconiglio.com");
		UtentePolisportiva sportivo2 = creaUtentiDB.getUtenteBy("marsasso@boh.com");
		UtentePolisportiva sportivo3 = creaUtentiDB.getUtenteBy("matita@boh.com");
		
		Impianto impianto1 = creaImpiantiDB.getImpiantoBy("impianto1");
		Impianto impianto3 = creaImpiantiDB.getImpiantoBy("impianto3");
		
		
		
		AppuntamentoImpianto appuntamento1 = (AppuntamentoImpianto) elementiPrenotazioneFactory
				.getAppuntamento(TipoPrenotazione.IMPIANTO.toString());
				LocalDate dataAppuntamento1 = LocalDate.of(2021, 5, 26);
				LocalTime oraInizioAppuntamento1 = LocalTime.of(10, 30);
				LocalTime oraFineAppuntamento1 = LocalTime.of(11, 30);
		appuntamento1.impostaOrario(dataAppuntamento1, oraInizioAppuntamento1, oraFineAppuntamento1);
		appuntamento1.setDescrizioneEventoPrenotato(listinoPrezziDescrizioniPolisportiva
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipoPrenotazione.IMPIANTO.toString(), tennis, ModalitaPrenotazione.SINGOLO_UTENTE));
		appuntamento1.setPending(true);
		appuntamento1.setStrutturaPrenotata(impianto1);
		appuntamento1.setManutentore(sportivo2);

		Prenotazione prenotazione1 = new Prenotazione();
		prenotazione1.setSportivoPrenotante(sportivo1);
		prenotazione1.aggiungi(appuntamento1);

		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
		appuntamento1.setCalcolatoreCosto(calcolatoreCosto);
		appuntamento1.aggiungiPartecipante(sportivo1);
		appuntamento1.aggiungiPartecipante(sportivo2);
		appuntamento1.calcolaCosto();
		Calendario calendarioSpecs1 = new Calendario();
		calendarioSpecs1.aggiungiAppuntamento(appuntamento1);
		impianto1.segnaInCalendarioGliAppuntamentiDel(calendarioSpecs1);

		AppuntamentoImpianto appuntamento2 = (AppuntamentoImpianto) elementiPrenotazioneFactory
				.getAppuntamento(TipoPrenotazione.IMPIANTO.toString());
				LocalDate dataAppuntamento2 = LocalDate.of(2021, 5, 21);
				LocalTime oraInizioAppuntamento2 = LocalTime.of(17, 00);
				LocalTime oraFineAppuntamento2 = LocalTime.of(19, 00);
		appuntamento2.impostaOrario(dataAppuntamento2, oraInizioAppuntamento2, oraFineAppuntamento2);
		appuntamento2.setDescrizioneEventoPrenotato(this.listinoPrezziDescrizioniPolisportiva
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipoPrenotazione.IMPIANTO.toString(), tennis, ModalitaPrenotazione.SINGOLO_UTENTE));
		appuntamento2.setPending(true);
		appuntamento2.setStrutturaPrenotata(impianto3);
		appuntamento2.setManutentore(sportivo2);

		appuntamento2.setCalcolatoreCosto(calcolatoreCosto);
		appuntamento2.aggiungiPartecipante(sportivo2);
		appuntamento2.aggiungiPartecipante(sportivo3);
		appuntamento2.calcolaCosto();
		Calendario calendarioSpecs2 = new Calendario();
		calendarioSpecs2.aggiungiAppuntamento(appuntamento2);

		impianto3.segnaInCalendarioGliAppuntamentiDel(calendarioSpecs2);

		Prenotazione prenotazione2 = new Prenotazione();
		prenotazione2.setSportivoPrenotante(sportivo2);
		prenotazione2.aggiungi(appuntamento2);


		appuntamentoRepository.save(appuntamento1);
		appuntamentoRepository.save(appuntamento2);

	}
}
