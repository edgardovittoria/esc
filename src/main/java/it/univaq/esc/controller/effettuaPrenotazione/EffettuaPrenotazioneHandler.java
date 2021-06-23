package it.univaq.esc.controller.effettuaPrenotazione;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.FormPrenotabile;

import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Controller che gestisce la creazione di nuove prenotazioni, così come la
 * partecipazione ad un evento preesistente.
 * 
 * @author esc
 *
 */
@RestController
@RequestMapping("/effettuaPrenotazione")
public class EffettuaPrenotazioneHandler {

	/**
	 * Factory FactoryStaty utilizzata per ottenere gli stati del controller.
	 */
	@Setter(value = AccessLevel.PRIVATE)
	private ElementiPrenotazioneFactory factoryStati;

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
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

	
	/**
	 * Prenotazione in atto gestita dal controller.
	 */
	private Prenotazione prenotazioneInAtto;

	/**
	 * Tipo della prenotazione in atto gestita dal controller.
	 */
	private String tipoPrenotazioneInAtto;


	/**
	 * Stato del controller, dipendente dal tipo della prenotazione in atto.
	 * Incapsula tutte le operazioni le cui implementazioni dipendono dal tipo della
	 * prenotazione in atto, disaccoppiando di fatto il controller dalla tipologia
	 * di prenotazione.
	 */
	private EffettuaPrenotazioneState stato;

	@Getter()
	@Setter(value = AccessLevel.PRIVATE)
	private Integer idSquadraPrenotante;

	/**
	 * Costruttore del controller EffettuaPrenotazioneHandlerRest
	 */
	public EffettuaPrenotazioneHandler() {
	}

	/**
	 * Restituisce la factory FactoryStati, utilizzata per ottenere gli stati del
	 * controller
	 * 
	 * @return la factory FactoryStati
	 */
	private ElementiPrenotazioneFactory getFactoryStati() {
		return factoryStati;
	}

	/**
	 * Restituisce lo Stato attuale del controller
	 * 
	 * @return stato attuale del controller
	 */
	private EffettuaPrenotazioneState getStato() {
		return stato;
	}

	/**
	 * Imposta lo stato passato come parametro nel controller
	 * 
	 * @param stato stato del controller da impostare
	 */
	private void setStato(EffettuaPrenotazioneState stato) {
		this.stato = stato;
	}

	

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
	 * Restituisce la tipologia della prenotazione in atto
	 * 
	 * @return tipo della prenotazione in atto
	 */
	public String getTipoPrenotazioneInAtto() {
		return this.tipoPrenotazioneInAtto;
	}


	


	/**
	 * Restituisce il registro degli appuntamenti
	 * 
	 * @return il registro appuntamenti
	 */
	private RegistroAppuntamenti getRegistroAppuntamenti() {
		return this.registroAppuntamenti;
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

		this.setIdSquadraPrenotante(idSquadra);
		UtentePolisportiva sportivoPrenotante = this.getRegistroUtenti()
				.getUtenteByEmail(emailSportivoPrenotante);
		if (!sportivoPrenotante.comeSportivo().isMoroso()) {
			this.inizializzaNuovaPrenotazione(sportivoPrenotante, tipoPrenotazione, modalitaPrenotazione);
			return this.getStato().getDatiOpzioni(this);
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
		UtentePolisportiva direttore = this.getRegistroUtenti().getUtenteByEmail(emailDirettore);
		this.inizializzaNuovaPrenotazione(direttore, tipoPrenotazione, modalitaPrenotazione);
		return this.getStato().getDatiOpzioniModalitaDirettore(this);
	}

	
	private void inizializzaNuovaPrenotazione(UtentePolisportiva sportivoPrenotante, String tipoPrenotazione,
			String modalitaPrenotazione) {

		Integer lastIdPrenotazione = this.getRegistroPrenotazioni().getLastIdPrenotazione();

		setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione));
		getPrenotazioneInAtto().setSportivoPrenotante(sportivoPrenotante);

		setFactoryStati(this.creaFactoryStati(modalitaPrenotazione));
		setMapperFactory(creaMapperFactory(modalitaPrenotazione));
		this.setTipoPrenotazioneInAtto(tipoPrenotazione);
	}

	private ElementiPrenotazioneFactory creaFactoryStati(String modalitaPrenotazione) {
		return BeanUtil.getBean("ELEMENTI_PRENOTAZIONE_" + modalitaPrenotazione, ElementiPrenotazioneFactory.class);
	}

	private MapperFactory creaMapperFactory(String modalitaPrenotazione) {
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
		
		PrenotazioneDTO prenDTO = this.getStato().impostaDatiPrenotazione(formPrenotaImpianto, this);

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

		for (Appuntamento appuntamento : getPrenotazioneInAtto().getListaAppuntamenti()) {
			Calendario calendarioAppuntamento = new Calendario();
			calendarioAppuntamento.aggiungiAppuntamento(appuntamento);
			UtentePolisportiva manutentore = getRegistroUtenti().getManutentoreLibero(calendarioAppuntamento);
			appuntamento.setManutentore(manutentore);
			getRegistroUtenti().aggiornaCalendarioManutentore(calendarioAppuntamento, manutentore);

		}
		this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
		this.getRegistroAppuntamenti().salvaListaAppuntamenti(getPrenotazioneInAtto().getListaAppuntamenti());

		this.getStato().aggiornaElementiDopoConfermaPrenotazione(this);

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiInPrenotazioneDTO(getPrenotazioneInAtto());

		return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.CREATED);
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
		return this.getStato().aggiornaOpzioniPrenotazione(dati);
	}

	@GetMapping("/istruttoriDisponibili")
	@CrossOrigin
	public @ResponseBody List<UtentePolisportivaDTO> getListaIstruttoriPerSport(
			@RequestParam(name = "sport") String sport) {
		return this.getStato().getIstruttoriPerSport(sport);
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
		
		setFactoryStati(creaFactoryStati(modalitaPrenotazione));
		setMapperFactory(creaMapperFactory(modalitaPrenotazione));
		
		setTipoPrenotazioneInAtto(tipoPrenotazione);
		
		
		return this.getStato().aggiungiPartecipanteAEventoEsistente(idEvento, identificativoPartecipante);
	}

	/**
	 * Restituisce il registro degli utenti della polisportiva
	 * 
	 * @return registro utenti della polisportiva
	 */
	private RegistroUtentiPolisportiva getRegistroUtenti() {
		return this.registroUtenti;
	}

	/**
	 * Restituisce il registro delle prenotazioni
	 * 
	 * @return registro delle prenotazioni
	 */
	private RegistroPrenotazioni getRegistroPrenotazioni() {
		return this.registroPrenotazioni;
	}

	/**
	 * Restituisce la prenotazione in atto, ovvero quella in fase di compilazione
	 * 
	 * @return la prenotazione in atto.
	 */
	public Prenotazione getPrenotazioneInAtto() {
		return this.prenotazioneInAtto;
	}

	/**
	 * Imposta la prenotazione in atto nel controller
	 * 
	 * @param prenotazione in atto da impostare
	 */
	private void setPrenotazioneInAtto(Prenotazione prenotazione) {
		this.prenotazioneInAtto = prenotazione;
	}

	/**
	 * Restituisce l'utente che sta effettuando la prenotazione in atto
	 * 
	 * @return utente prenotante associato alla prenotazione in atto
	 */
	public UtentePolisportiva getSportivoPrenotante() {
		return this.getPrenotazioneInAtto().getSportivoPrenotante();
	}

}
