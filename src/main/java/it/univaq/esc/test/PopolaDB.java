package it.univaq.esc.test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
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
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class PopolaDB {

	@Autowired
	private UtentePolisportivaRepository utentePolisportivaRepository;

	// @Autowired
	// private CalendarioRepository calendarioRepository;

	@Autowired
	private ImpiantoRepository impiantoRepository;

	@Resource(name = "ELEMENTI_PRENOTAZIONE_SINGOLO_UTENTE")
	private ElementiPrenotazioneFactory elementiPrenotazioneFactory;

	@Autowired
	private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;

	@Autowired
	private AppuntamentoRepository appuntamentoRepository;

	@Autowired
	private SportRepository sportRepo;

	@Autowired
	private PrenotazioneRepository prenotazioneRepository;

	@Autowired
	private SquadraRepository squadraRepository;

	public PopolaDB() {
	}

	public void popola() {

		Sport calcetto = new Sport("calcetto", 10, 5);
		Sport tennis = new Sport("tennis", 2, 2);
		Sport pallavolo = new Sport("pallavolo", 12, 6);

		sportRepo.save(calcetto);
		sportRepo.save(tennis);
		sportRepo.save(pallavolo);

		List<Sport> listaSportPraticati = new ArrayList<Sport>();
		listaSportPraticati.add(tennis);
		UtentePolisportiva sportivoPrenotante = new UtentePolisportivaBuilder("Pippo", "Franco",
				"pippofranco@bagaglino.com", "pippo").assegnaRuoloSportivo(listaSportPraticati).build();

		List<Sport> listaSportPraticati1 = new ArrayList<Sport>();
		listaSportPraticati1.add(tennis);
		listaSportPraticati1.add(calcetto);
		listaSportPraticati1.add(pallavolo);
		UtentePolisportiva sportivo1 = new UtentePolisportivaBuilder("Gianni", "cognome", "poppins@bianconiglio.com",
				"poppins").assegnaRuoloSportivo(listaSportPraticati1).build();

		
		
		List<Sport> listaSportPraticati2 = new ArrayList<Sport>();
		listaSportPraticati2.add(tennis);
		listaSportPraticati2.add(calcetto);
		UtentePolisportiva sportivo2 = new UtentePolisportivaBuilder("mariangelo", "sasso", "marsasso@boh.com", "sasso").assegnaRuoloSportivo(listaSportPraticati2)
				.assegnaRuoloIstruttore(listaSportPraticati1).assegnaRuoloManutentore().build();
		

		
		List<Sport> listaSportPraticati3 = new ArrayList<Sport>();
		listaSportPraticati3.add(pallavolo);
		listaSportPraticati3.add(calcetto);
		UtentePolisportiva sportivo3 = new UtentePolisportivaBuilder("tardigrado", "acqua", "matita@boh.com", "matita").assegnaRuoloSportivo(listaSportPraticati3)
				.assegnaRuoloDirettorePolisportiva().build();
		

		utentePolisportivaRepository.save(sportivoPrenotante);
		utentePolisportivaRepository.save(sportivo1);
		utentePolisportivaRepository.save(sportivo2);
		utentePolisportivaRepository.save(sportivo3);

		Squadra squadra1 = new Squadra();
		squadra1.setNome("I Dugonghi");
		squadra1.setSport(calcetto);
		squadra1.aggiungiAmministratore(sportivo3);
		squadra1.aggiungiMembro(sportivo1);
		squadra1.aggiungiMembro(sportivo2);

		Squadra squadra2 = new Squadra();
		squadra2.setNome("Le Nutrie");
		squadra2.setSport(pallavolo);
		squadra2.aggiungiAmministratore(sportivoPrenotante);
		squadra2.aggiungiMembro(sportivo1);
		squadra2.aggiungiMembro(sportivo3);
		
		Squadra squadra3 = new Squadra();
		squadra3.setNome("Le Strolaghe");
		squadra3.setSport(calcetto);
		squadra3.aggiungiAmministratore(sportivoPrenotante);
		squadra3.aggiungiMembro(sportivo1);
		squadra3.aggiungiMembro(sportivo2);

		getSquadraRepository().save(squadra1);
		getSquadraRepository().save(squadra2);
		getSquadraRepository().save(squadra3);

		List<Sport> sportPraticatiImpianto1 = new ArrayList<Sport>();
		sportPraticatiImpianto1.add(calcetto);
		sportPraticatiImpianto1.add(tennis);

		List<Sport> sportPraticatiImpianto2 = new ArrayList<Sport>();
		sportPraticatiImpianto2.add(calcetto);
		sportPraticatiImpianto2.add(pallavolo);

		List<Sport> sportPraticatiImpianto3 = new ArrayList<Sport>();
		sportPraticatiImpianto3.add(calcetto);
		sportPraticatiImpianto3.add(pallavolo);
		sportPraticatiImpianto3.add(tennis);

		ImpiantoSpecs specificaImpianto1 = new ImpiantoSpecs(Pavimentazione.SINTETICO, calcetto);
		ImpiantoSpecs specificaImpianto12 = new ImpiantoSpecs(Pavimentazione.SINTETICO, tennis);
		ImpiantoSpecs specificaImpianto13 = new ImpiantoSpecs(Pavimentazione.SINTETICO, pallavolo);
		ImpiantoSpecs specificaImpianto2 = new ImpiantoSpecs(Pavimentazione.TERRA_BATTUTA, tennis);
		ImpiantoSpecs specificaImpianto3 = new ImpiantoSpecs(Pavimentazione.CEMENTO, pallavolo);
		ImpiantoSpecs specificaImpianto32 = new ImpiantoSpecs(Pavimentazione.CEMENTO, tennis);

		// impiantoSpecsRepository.save(specificaImpianto1);
		// impiantoSpecsRepository.save(specificaImpianto2);
		// impiantoSpecsRepository.save(specificaImpianto3);

		List<ImpiantoSpecs> specificheImpianto1 = new ArrayList<ImpiantoSpecs>();
		specificheImpianto1.add(specificaImpianto1);
		specificheImpianto1.add(specificaImpianto12);
		specificheImpianto1.add(specificaImpianto13);

		List<ImpiantoSpecs> specificheImpianto2 = new ArrayList<ImpiantoSpecs>();
		specificheImpianto2.add(specificaImpianto2);

		List<ImpiantoSpecs> specificheImpianto3 = new ArrayList<ImpiantoSpecs>();
		specificheImpianto3.add(specificaImpianto3);
		specificheImpianto3.add(specificaImpianto32);

		Impianto impianto1 = new Impianto(specificheImpianto1);
		Impianto impianto2 = new Impianto(specificheImpianto2);
		Impianto impianto3 = new Impianto(specificheImpianto3);

		impiantoRepository.save(impianto1);
		impiantoRepository.save(impianto2);
		impiantoRepository.save(impianto3);

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.IMPIANTO.toString(),
						tennis.getNumeroGiocatoriPerIncontro(), tennis.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("50"),
						Pavimentazione.TERRA_BATTUTA.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.IMPIANTO.toString(),
						calcetto.getNumeroGiocatoriPerIncontro(), calcetto.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(pallavolo, TipiPrenotazione.IMPIANTO.toString(),
						pallavolo.getNumeroGiocatoriPerIncontro(), pallavolo.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("30"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.IMPIANTO.toString(),
						tennis.getNumeroGiocatoriPerIncontro(), tennis.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("50"),
						Pavimentazione.TERRA_BATTUTA.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.IMPIANTO.toString(),
						calcetto.getNumeroGiocatoriPerIncontro(), calcetto.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(pallavolo, TipiPrenotazione.IMPIANTO.toString(),
						pallavolo.getNumeroGiocatoriPerIncontro(), pallavolo.getNumeroGiocatoriPerIncontro())
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("30"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.LEZIONE.toString(), 1, 1)
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("50"),
						Pavimentazione.TERRA_BATTUTA.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.LEZIONE.toString(), 1, 5)
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		this.listinoPrezziDescrizioniPolisportiva
				.nuovoPrenotabile_avviaCreazione(pallavolo, TipiPrenotazione.LEZIONE.toString(), 1, 6)
				.nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.CEMENTO.toString())
				.nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("30"),
						Pavimentazione.SINTETICO.toString())
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

		AppuntamentoImpianto appuntamento1 = (AppuntamentoImpianto) getElementiPrenotazioneFactory()
				.getAppuntamento(TipiPrenotazione.IMPIANTO.toString());
				LocalDate dataAppuntamento1 = LocalDate.of(2021, 5, 26);
				LocalTime oraInizioAppuntamento1 = LocalTime.of(10, 30);
				LocalTime oraFineAppuntamento1 = LocalTime.of(11, 30);
		appuntamento1.impostaOrario(dataAppuntamento1, oraInizioAppuntamento1, oraFineAppuntamento1);
		appuntamento1.setDescrizioneEventoPrenotato(this.listinoPrezziDescrizioniPolisportiva
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.IMPIANTO.toString(), tennis, ModalitaPrenotazione.SINGOLO_UTENTE.toString()));
		appuntamento1.setPending(true);
		appuntamento1.setImpiantoPrenotato(impianto1);
		appuntamento1.setManutentore(sportivo2);

		Prenotazione prenotazione1 = new Prenotazione(0);
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
		// prenotazione1.setCalendarioSpecifica(calendarioSpecs1, prenotazioneSpecs);
		impianto1.setCalendarioAppuntamentiImpianto(calendarioSpecs1);
		// prenotazione1.impostaCalendarioPrenotazioneDaSpecifiche();

		// Prenotazione dell'impianto3 in una data diversa dalla data scelta dallo
		// sportivo che sta effettuando la prenotazione;

		AppuntamentoImpianto appuntamento2 = (AppuntamentoImpianto) getElementiPrenotazioneFactory()
				.getAppuntamento(TipiPrenotazione.IMPIANTO.toString());
				LocalDate dataAppuntamento2 = LocalDate.of(2021, 5, 21);
				LocalTime oraInizioAppuntamento2 = LocalTime.of(17, 00);
				LocalTime oraFineAppuntamento2 = LocalTime.of(19, 00);
		appuntamento2.impostaOrario(dataAppuntamento2, oraInizioAppuntamento2, oraFineAppuntamento2);
		appuntamento2.setDescrizioneEventoPrenotato(this.listinoPrezziDescrizioniPolisportiva
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.IMPIANTO.toString(), tennis, ModalitaPrenotazione.SINGOLO_UTENTE.toString()));
		appuntamento2.setPending(true);
		appuntamento2.setImpiantoPrenotato(impianto3);
		appuntamento2.setManutentore(sportivo2);

		appuntamento2.setCalcolatoreCosto(calcolatoreCosto);
		appuntamento2.aggiungiPartecipante(sportivo2);
		appuntamento2.aggiungiPartecipante(sportivo3);
		appuntamento2.calcolaCosto();
		Calendario calendarioSpecs2 = new Calendario();
		calendarioSpecs2.aggiungiAppuntamento(appuntamento2);

		impianto3.setCalendarioAppuntamentiImpianto(calendarioSpecs2);

		Prenotazione prenotazione2 = new Prenotazione(1);
		prenotazione2.setSportivoPrenotante(sportivo2);
		prenotazione2.aggiungi(appuntamento2);

		/*
		 * calendarioRepository.save(calendarioPrenotazione);
		 * calendarioRepository.save(calendarioPrenotazione2);
		 */

		appuntamentoRepository.save(appuntamento1);
		appuntamentoRepository.save(appuntamento2);

	}

}