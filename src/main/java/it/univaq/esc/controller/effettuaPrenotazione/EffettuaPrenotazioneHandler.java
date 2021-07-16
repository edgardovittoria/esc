package it.univaq.esc.controller.effettuaPrenotazione;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller che gestisce la creazione di nuove prenotazioni, così come la
 * partecipazione ad un evento preesistente.
 * 
 * @author esc
 *
 */
@RestController
@RequestMapping("/effettuaPrenotazione")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class EffettuaPrenotazioneHandler {

	/**
	 * Factory FactoryStaty utilizzata per ottenere gli stati del controller.
	 */
	private ElementiPrenotazioneFactory factoryStati;

	@Getter(value = AccessLevel.PRIVATE)
	private MapperFactory mapperFactory;
	/**
	 * Registro degli appuntamenti, utilizzato per le operazioni di gestione della
	 * lista degli appuntamenti della prenotazione in atto.
	 */
	@Autowired
	private RegistroAppuntamenti registroAppuntamenti;

	/**
	 * Registro degli utenti della polisportiva, usato per tutte le operazioni di
	 * gestione degli utenti interessati dalla prenotazione in atto
	 */
	@Autowired
	private RegistroUtentiPolisportiva registroUtenti;

	/**
	 * Registro delle prenotazioni, utilizzato per la getsione della prenotazione in
	 * atto.
	 */
	@Autowired
	private RegistroPrenotazioni registroPrenotazioni;
	
	@Autowired
	private RegistroSquadre registroSquadre;
	

	/**
	 * Prenotazione in atto gestita dal controller.
	 */
	@Getter(value = AccessLevel.PUBLIC)
	private Prenotazione prenotazioneInAtto;

	/**
	 * Tipo della prenotazione in atto gestita dal controller.
	 */
	@Getter(value = AccessLevel.PUBLIC)
	private String tipoPrenotazioneInAtto;

	/**
	 * Stato del controller, dipendente dal tipo della prenotazione in atto.
	 * Incapsula tutte le operazioni le cui implementazioni dipendono dal tipo della
	 * prenotazione in atto, disaccoppiando di fatto il controller dalla tipologia
	 * di prenotazione.
	 */
	private EffettuaPrenotazioneState stato;

	

	/**
	 * Imposta la tipologia della prenotazione in atto, passata come parametro. In
	 * base alla tipologia imposta poi lo Stato corretto nel controller, tramite la
	 * factory FactoryStati.
	 * 
	 * @param tipoPrenotazione tipo della prenotazione in atto
	 */
	private void setTipoPrenotazioneInAtto(String tipoPrenotazione) {
		this.tipoPrenotazioneInAtto = tipoPrenotazione;
		this.setStato(this.getFactoryStati().getStatoEffettuaPrenotazioneHandler(tipoPrenotazione));
		getStato().setElementiPrenotazioneFactory(getFactoryStati());
		getStato().setMapperFactory(getMapperFactory());
	}

	/**
	 * Avvia una nuova prenotazione. Crea una prenotazione e imposta l'utente che la
	 * effettua. Salva poi la tipologia della prenotazione per utilizzarla in
	 * operazioni successive e, in base a questa, imposta lo Stato corretto. Infine,
	 * tramite lo Stato, restituisce una mappa di dati per la fase di compilazione.
	 * 
	 * @param emailSportivoPrenotante email dell'utente che effettua la
	 *                                prenotazione.
	 * @param tipoPrenotazione        tipologia di prenotazione selezionata
	 * @return mappa di dati per la fase di compilazione della prenotazione.
	 */
	@GetMapping("/avviaNuovaPrenotazione")
	@CrossOrigin
	public @ResponseBody Map<String, Object> avviaNuovaPrenotazione(
			@RequestParam(name = "email") String emailSportivoPrenotante,
			@RequestParam(name = "idSquadra") Integer idSquadra,
			@RequestParam(name = "tipoPrenotazione") String tipoPrenotazione,
			@RequestParam(name = "modalitaPrenotazione") String modalitaPrenotazione) {

		
		UtentePolisportiva sportivoPrenotante = this.getRegistroUtenti()
				.trovaUtenteInBaseAllaSua(emailSportivoPrenotante);
		if (!sportivoPrenotante.comeSportivo().isMoroso()) {
			impostaAttributiControllerDipendentiDa(modalitaPrenotazione, tipoPrenotazione);
			this.inizializzaNuovaPrenotazione(sportivoPrenotante, modalitaPrenotazione, idSquadra);
			return this.getStato().getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(this);
		} else {
			/*
			 * In futuro possiamo ritornare la lista dei debiti dello sportivo.
			 */
			return null;
		}
	}

	/**
	 * Avvia una nuova prenotazione di eventi creabili solo dal direttore, o che in
	 * modalità direttore devono offrire opzioni differenti. Crea una prenotazione e
	 * imposta l'utente che la effettua, il direttore. Salva poi la tipologia della
	 * prenotazione per utilizzarla in operazioni successive e, in base a questa,
	 * imposta lo Stato corretto. Infine, tramite lo Stato, restituisce una mappa di
	 * dati per la fase di compilazione.
	 * 
	 * @param emailDirettore   email del direttore che effettua la prenotazione.
	 * @param tipoPrenotazione tipologia di prenotazione selezionata
	 * @return mappa di dati per la fase di compilazione della prenotazione.
	 */
	// @PreAuthorize("hasRole('ROLE_DIRETTORE')")
	@RolesAllowed("ROLE_DIRETTORE")
	@GetMapping("/avviaNuovaPrenotazioneEventoDirettore")
	@CrossOrigin
	public @ResponseBody Map<String, Object> avviaNuovaPrenotazioneEventoDirettore(
			@RequestParam(name = "email") String emailDirettore,
			@RequestParam(name = "tipoPrenotazione") String tipoPrenotazione,
			@RequestParam(name = "modalitaPrenotazione") String modalitaPrenotazione) {

		UtentePolisportiva direttore = this.getRegistroUtenti().trovaUtenteInBaseAllaSua(emailDirettore);
		impostaAttributiControllerDipendentiDa(modalitaPrenotazione, tipoPrenotazione);
		this.inizializzaNuovaPrenotazione(direttore, modalitaPrenotazione, null);

		return this.getStato().getDatiOpzioniPerPrenotazioneInModalitaDirettore(this);
	}

	private void inizializzaNuovaPrenotazione(UtentePolisportiva sportivoPrenotante, String modalitaPrenotazione, Integer idSquadraPrenotante) {
		setPrenotazioneInAtto(getFactoryStati().getPrenotazione());
		getPrenotazioneInAtto().setSportivoPrenotante(sportivoPrenotante);
		if(modalitaPrenotazione.equals(ModalitaPrenotazione.SQUADRA.toString())) {
			PrenotazioneSquadra prenotazioneSquadraInAtto = (PrenotazioneSquadra) getPrenotazioneInAtto();
			prenotazioneSquadraInAtto.setSquadraPrenotante(getRegistroSquadre().getSquadraById(idSquadraPrenotante));
		}
	}

	private void impostaAttributiControllerDipendentiDa(String modalitaPrenotazione, String tipoPrenotazione) {
		setFactoryStati(this.chiedeAlContainerLaFactoryStatiRelativaAlla(modalitaPrenotazione));
		setMapperFactory(chiedeAlContainerLaMapperFactoryiRelativaAlla(modalitaPrenotazione));
		this.setTipoPrenotazioneInAtto(tipoPrenotazione);
	}

	private ElementiPrenotazioneFactory chiedeAlContainerLaFactoryStatiRelativaAlla(String modalitaPrenotazione) {
		return BeanUtil.getBean("ELEMENTI_PRENOTAZIONE_" + modalitaPrenotazione, ElementiPrenotazioneFactory.class);
	}

	private MapperFactory chiedeAlContainerLaMapperFactoryiRelativaAlla(String modalitaPrenotazione) {
		return BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class);
	}

	/**
	 * Imposta i valori della prenotazione passati come parametro, delegando tutte
	 * le operazioni di assegnazione allo Stato. Restituisce la prenotazione in
	 * formato DTO per l'utilizzo lato client.
	 * 
	 * @param formPrenotaImpianto oggetto DTO su cui vengono mappati i dati
	 *                            selezionati in fase di compilazione.
	 * @return prenotazione compilata in formato DTO.
	 */
	@PostMapping("/riepilogoPrenotazione")
	@CrossOrigin
	public ResponseEntity<PrenotazioneDTO> getRiepilogoPrenotazione(@RequestBody FormPrenotabile formPrenotaImpianto) {

		PrenotazioneDTO prenDTO = this.getStato().impostaPrenotazioneConDatiDellaFormPerRiepilogo(formPrenotaImpianto,
				this);

		return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.OK);

	}

	/**
	 * Salva la prenotazione compilata nel RegistroPrenotazione e sul database.
	 * Tramite lo Stato aggiorna poi eventuali oggetti software legati alla
	 * prenotazione Restituisce la prenotazione DTO con tutti i campi aggiornati,
	 * compresi gli id generati automaticamente dal database.
	 * 
	 * @return prenotazione effettuata, in formato DTO.
	 */
	@PostMapping("/confermaPrenotazione")
	@CrossOrigin
	public ResponseEntity<PrenotazioneDTO> confermaPrenotazione() {

		assegnaManutentoriAgliAppuntamentiDellaPrenotazioneInAtto();
		aggiornaCalendariDeiManutentoriAssegnati();
		registraNelSistemaLaPrenotazioneInAtto();
		this.getStato().aggiornaElementiLegatiAllaPrenotazioneConfermata(this);

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiInPrenotazioneDTO(getPrenotazioneInAtto());

		return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.CREATED);
	}

	private void assegnaManutentoriAgliAppuntamentiDellaPrenotazioneInAtto() {
		for (Appuntamento appuntamento : getPrenotazioneInAtto().getListaAppuntamenti()) {
			assegnaManutentoreA(appuntamento);
		}

	}

	private void assegnaManutentoreA(Appuntamento appuntamento) {
		UtentePolisportiva manutentore = getRegistroUtenti().getManutentoreLiberoNellOrarioDi(appuntamento);
		appuntamento.setManutentore(manutentore);
	}

	private void aggiornaCalendariDeiManutentoriAssegnati() {
		for (Appuntamento appuntamento : getPrenotazioneInAtto().getListaAppuntamenti()) {
			appuntamento.siAggiungeAlCalendarioDelProprioManutentore();
		}
	}

	private void registraNelSistemaLaPrenotazioneInAtto() {
		this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
		this.getRegistroAppuntamenti().salvaListaAppuntamenti(getPrenotazioneInAtto().getListaAppuntamenti());

	}

	/**
	 * Metodo che restituisce una mappa di dati per la compilazione della
	 * prenotazione, aggiornata in base ad opzioni già compilate e passate in una
	 * mappa come parametro.
	 * 
	 * @param dati mappa con i dati delle opzioni già compilate.
	 * @return mappa con dati aggiornati per le opzioni da compilare.
	 */
	@PostMapping("/aggiornaDatiOpzioni")
	@CrossOrigin
	public @ResponseBody Map<String, Object> getDatiOpzioniAggiornati(@RequestBody HashMap<String, Object> dati) {
		return this.getStato().getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(dati);
	}

	/*
	 * TODO Verificare se questo metodo è effettivamente usato, ma meglio sarebbe
	 * eliminarlo.
	 */
	@GetMapping("/istruttoriDisponibili")
	@CrossOrigin
	public @ResponseBody List<UtentePolisportivaDTO> getListaIstruttoriPerSport(
			@RequestParam(name = "sport") String sport) {
		return this.getStato().getListaDTODegliIstruttoriCheInsegnanoLo(sport);
	}

	/**
	 * Aggiunge l'utente ad un evento preesistente, associandogli una relativa quota
	 * di prenotazione
	 * 
	 * @param mappaDati mappa con l'id dell'evento a cui si vuole partecipare e
	 *                  l'email dell'utente.
	 * @return Restituisce l'evento asggiornato, in formato DTO.
	 */
	@PatchMapping("/partecipazioneEventoEsistente")
	@CrossOrigin
	public @ResponseBody Object aggiungiPartecipanteAEventoEsistente(@RequestBody Map<String, Object> mappaDati) {
		Integer idEvento = (Integer) mappaDati.get("idEvento");
		Object identificativoPartecipante = (Object) mappaDati.get("identificativoPartecipante");
		String modalitaPrenotazione = (String) mappaDati.get("modalitaPrenotazione");
		String tipoPrenotazione = (String) mappaDati.get("tipoPrenotazione");

		impostaAttributiControllerDipendentiDa(modalitaPrenotazione, tipoPrenotazione);

		return this.getStato().aggiungiPartecipanteAEventoEsistente(idEvento, identificativoPartecipante);
	}

	/**
	 * Restituisce l'utente che sta effettuando la prenotazione in atto
	 * 
	 * @return utente prenotante associato alla prenotazione in atto
	 */
	public UtentePolisportiva getSportivoPrenotante() {
		return this.getPrenotazioneInAtto().getSportivoPrenotante();
	}

	@DeleteMapping("/annullaCreazioneCorso")
	@CrossOrigin
	public ResponseEntity eliminaDescrizioneCorsoInCreazione() {
		((EffettuaPrenotazioneCorsoState) getStato()).eliminaDescrizioneCorsoDaCatalogoEDatabase();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
