package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe astratta che definisce l'interfaccia comune per gli stati del
 * controller EffettuaPrenotazioneHandler. Grazie ad essa il controller viene
 * disaccoppiato da specifiche implementazioni legate alla tipologia di
 * prenotazione. Quando viene creato un nuovo tipo di prenotazione, basterà
 * aggiungere una sottoclasse con l'implementazione specifica per quel tipo.
 * 
 * Ogni classe di stato poi ha un blocco static, invocato la prima volta che
 * viene caricata la classe, nel quale va a registrarsi alla FactoryStati
 * 
 * @author esc
 *
 */
@Getter(value = AccessLevel.PUBLIC) @Setter(value = AccessLevel.PRIVATE) 
public abstract class EffettuaPrenotazioneState {
	
	/**
	 * Registro delle notifiche. Utilizzato per invio e gestione generale delle notifiche relative alle prenotazioni.
	 */
	
	private RegistroNotifiche registroNotifiche;

	/**
	 * Registro degli sport della polisportiva. Utilizzato per la ricerca e la
	 * visualizzazione degli sport in fase di prenotazione.
	 */
	
	private RegistroSport registroSport;

	/**
	 * Registro degli impianti della polisportiva. Utilizzato per la gestione degli
	 * impianti selezionati in fase di prenotazione.
	 */
	
	private RegistroImpianti registroImpianti;

	/**
	 * Registro degli utenti della polisportiva. Utilizzato per la gestione di tutti
	 * gli utenti inerenti la prenotazione che si sta gestendo.
	 */
	
	private RegistroUtentiPolisportiva registroUtenti;

	/**
	 * Registro degli appuntamenti. Utilizzato per la gestione degli appuntamenti
	 * associati alla prenotazione che si sta gestendo.
	 */
	
	private RegistroAppuntamenti registroAppuntamenti;
	
	
	
	private RegistroPrenotazioni registroPrenotazioni;
	
	
	
	private CatalogoPrenotabili catalogoPrenotabili;



	private RegistroSquadre registroSquadre;
	
	@Setter(value = AccessLevel.PUBLIC)
	private ElementiPrenotazioneFactory elementiPrenotazioneFactory;
	
	@Setter(value = AccessLevel.PUBLIC)
	private MapperFactory mapperFactory;
	
	
	
	public EffettuaPrenotazioneState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {
		setRegistroAppuntamenti(registroAppuntamenti);
		setRegistroImpianti(registroImpianti);
		setCatalogoPrenotabili(catalogoPrenotabili);
		setRegistroNotifiche(registroNotifiche);
		setRegistroPrenotazioni(registroPrenotazioni);
		setRegistroSport(registroSport);
		setRegistroSquadre(registroSquadre);
		setRegistroUtenti(registroUtenti);
	}
	
	/**
	 * Definisce l'interfaccia per il metodo che restituisce i dati necessari per
	 * popolare le opzioni di prenotazione, nella fase di avvio di una nuova
	 * prenotazione.
	 * 
	 * @param controller istanza del controller a cui lo stato è associato, in modo
	 *                   tale da poter aggiornarne i dati all'occorrenza.
	 * @return mappa con i dati iniziali delle varie opzioni selezionabili in fase
	 *         di prenotazione.
	 */
	public abstract Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandler controller);

	/**
	 * Definisce l'interfaccia per il metodo che imposta tutti i valori selezionati
	 * per la prenotazione nella controparte software lato server. Invocato in fase
	 * di riepilogo della prenotazione. I dati selezionati lato client vengono
	 * mappati automaticamente (riferimento per nome) su una form DTO che implementa
	 * l'interfaccia IFormPrenotabile. La specifica implementazione della form su
	 * cui mappare i dati viene stabilita in fase di deserializzazione del payload
	 * json ricevuto dal client, tramite un attributo che identifica la specifica
	 * implementazione (vedere la definizione di IFormPrenotabile per maggiori
	 * dettagli).
	 * 
	 * @param formDati   form DTO sulla quale vengono mappati i dati ricevuti dal
	 *                   client
	 * @param controller istanza del controller cui lo stato è associato, per
	 *                   poterne eventualmente aggiornare i dati.
	 */
	public abstract PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller);

	/**
	 * Definisce l'interfaccia del metodo che aggiorna gli oggetti intererssati
	 * dalla prenotazione effettuata, una volta che questa è stata salvata nel
	 * registro delle prenotazioni e sul database, in fase di conferma della
	 * prenotazione.
	 * 
	 * @param controller istanza del controller cui lo stato è associato, per
	 *                   poterne aggiornare i dati.
	 */
	public abstract void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller);

	/**
	 * Definisce l'interfaccia del metodo che aggiorna i dati usati per popolare le
	 * opzioni di compilazione della prenotazione, sulla base di quelle già
	 * impostate. Invocato in fase di compilazione delle opzioni di prenotazione.
	 * Prende in input una mappa con i dati delle opzioni già compilate. Restituisce
	 * una mappa con i dati aggiornati per ripopolare le rimanenti opzioni di
	 * prenotazione da impostare.
	 * 
	 * @param dati mappa con i dati delle opzioni di prenotazione già compilate.
	 * @return mappa con i dati per ripopolare le opzioni di prenotazione ancora da
	 *         compilare.
	 */
	public abstract Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati);

	/**
	 * Definisce l'interfaccia del metodo che si occupa di assegnare un nuovo
	 * partecipante ad un evento preesistente, aggiornando tutti i dati relativi.
	 * Invocato quando si sceglie di partecipare ad un evento già esistente.
	 * 
	 * @param idEvento          identificativo dell'evento a cui si vuole
	 *                          partecipare.
	 * @param emailPartecipante email associata all'utente che vuole partecipare
	 *                          all'evento.
	 * @return l'oggeto che rappresenta l'evento a cui si vuole partecipare,
	 *         aggiornato con il nuovo partecipante.
	 */
	public abstract Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante);

	
	
	/**
	 * Definisce l'interfaccia per il metodo che restituisce i dati necessari per
	 * popolare le opzioni di prenotazione, nella fase di avvio di una nuova
	 * prenotazione di un evento che può essere creato solo dal Direttore, o che 
	 * nel caso del Direttore necessita di opzioni differenti.
	 * 
	 * @param controller istanza del controller a cui lo stato è associato, in modo
	 *                   tale da poter aggiornarne i dati all'occorrenza.
	 * @return mappa con i dati iniziali delle varie opzioni selezionabili in fase
	 *         di prenotazione.
	 */
	public abstract Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandler controller);
	
	/**
	 * Metodo di utilità, utilizzato in quelli principali, che resituisce la lista
	 * di tutti gli sport praticabili nella polisportiva, in formato DTO.
	 * 
	 * @return lista degli sport praticabili nella polispsortiva, in formato DTO
	 */
	protected List<SportDTO> getSportPraticabiliPolisportiva() {
		List<Sport> listaSportPraticabili = this.getRegistroSport().getListaSportPolisportiva();

		List<SportDTO> listaSportPraticabiliDTO = new ArrayList<SportDTO>();
		for (Sport sport : listaSportPraticabili) {
			SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(sport);
			listaSportPraticabiliDTO.add(sportDTO);
		}
		return listaSportPraticabiliDTO;
	}

	/**
	 * Metodo di utilità. Restituisce tutti gli utenti registrati nel sistema della
	 * polisportiva che sono SPORTIVI, in formato DTO.
	 * 
	 * @return lista di tutti gli utenti registrati nella polisportiva che hanno il ruolo di sportivi in formato DTO.
	 */
	protected List<UtentePolisportivaDTO> getSportiviPolisportiva() {
		List<UtentePolisportivaDTO> listaSportiviDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportivaAbstract utente : getRegistroUtenti().getListaUtentiByRuolo(TipoRuolo.SPORTIVO)) {
			UtentePolisportivaDTO sportivoDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(utente);
			listaSportiviDTO.add(sportivoDTO);
		}

		return listaSportiviDTO;
	}
	
	protected List<UtentePolisportivaDTO> getSportiviLiberiInBaseACalendario(Calendario calendario) {
		List<UtentePolisportivaDTO> listaSportivi = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportivaAbstract utente : getRegistroUtenti().filtraUtentiLiberiInBaseACalendarioSportivo(getRegistroUtenti().getListaUtentiByRuolo(TipoRuolo.SPORTIVO), calendario)) {
			UtentePolisportivaDTO sportivoDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(utente);
			listaSportivi.add(sportivoDTO);
		}
		return listaSportivi;
	}
	
	protected List<UtentePolisportivaDTO> getSportiviLiberiInBaseAOrario(LocalDateTime oraInizio, LocalDateTime oraFine) {
		List<UtentePolisportivaDTO> listaSportivi = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportivaAbstract utente : getRegistroUtenti().filtraUtentiLiberiInBaseACalendarioSportivo(getRegistroUtenti().getListaUtentiByRuolo(TipoRuolo.SPORTIVO), oraInizio, oraFine)) {
			UtentePolisportivaDTO sportivoDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(utente);
			listaSportivi.add(sportivoDTO);
		}
		return listaSportivi;
	}
	
	
	/**
	 * Metodo di utilità. Restituisce tutti gli utenti registrati nel sistema della
	 * polisportiva, in formato DTO
	 * 
	 * @return lista di tutti gli utenti registrati nella polisportiva in formato DTO.
	 */
	protected List<UtentePolisportivaDTO> getUtentiPolisportiva() {
		List<UtentePolisportivaDTO> listaSportiviDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportivaAbstract utente : getRegistroUtenti().getListaUtentiPolisportiva()) {
			UtentePolisportivaDTO sportivoDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(utente);
			listaSportiviDTO.add(sportivoDTO);
		}

		return listaSportiviDTO;
	}

	
		

	/**
	 * Metodo di utilità. Resituisce la lista degli istruttori, in formato DTO,
	 * relativi allo sport passato come parametro.
	 * 
	 * @param sport nome dello sport di cui trovare gli istruttori
	 * @return lista degli istruttori associati allo sport passato come parametro,
	 *         in formato DTO.
	 */
	protected List<UtentePolisportivaDTO> getIstruttoriPerSport(String sport) {
		List<UtentePolisportivaDTO> listaIstruttori = new ArrayList<UtentePolisportivaDTO>();
		Sport sportRichiesto = this.getRegistroSport().getSportByNome(sport);

		List<UtentePolisportivaAbstract> istruttori = this.getRegistroUtenti().getIstruttoriPerSport(sportRichiesto);
		for (UtentePolisportivaAbstract istruttore : istruttori) {
			UtentePolisportivaDTO istDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(istruttore);
			listaIstruttori.add(istDTO);
		}

		return listaIstruttori;
	}

	/**
	 * Metodo di utilità. Restituisce la lista, in formato DTO, degli impianti
	 * disponibili ad un certo orario e per un determinato sport, passati come
	 * parametri in una mappa.
	 * 
	 * @param dati mappa con l'orario e lo sport relativamente ai quali trovare gli
	 *             impianti disponibili.
	 * @return lista, in formato DTO, degli impianti disponibili sulla base di sport
	 *         e orario.
	 */
	protected List<ImpiantoDTO> getImpiantiDTODisponibili(Map<String, Object> dati) {
		List<Impianto> listaImpiantiDisponibili = this.getRegistroImpianti().getListaImpiantiPolisportiva();
		if (dati.containsKey("orario")) {
			Map<String, String> orario = (HashMap<String, String>) dati.get("orario");
			listaImpiantiDisponibili = this.getRegistroImpianti().filtraImpiantiDisponibiliPerOrario(
					LocalDateTime.parse(orario.get("oraInizio"),
							DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
					LocalDateTime.parse(orario.get("oraFine"),
							DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
					listaImpiantiDisponibili);

			if (dati.containsKey("sport")) {
				listaImpiantiDisponibili = this.filtraImpiantiPerSport((String) dati.get("sport"),
						listaImpiantiDisponibili);

			}
		} else if (dati.containsKey("sport")) {
			listaImpiantiDisponibili = this.filtraImpiantiPerSport((String) dati.get("sport"),
					listaImpiantiDisponibili);
		}

		List<ImpiantoDTO> listaImpiantiDTODisponibili = new ArrayList<ImpiantoDTO>();
		for (Impianto impianto : listaImpiantiDisponibili) {

			ImpiantoDTO impiantoDTO = getMapperFactory().getImpiantoMapper().convertiInImpiantoDTO(impianto);
			listaImpiantiDTODisponibili.add(impiantoDTO);

		}

		return listaImpiantiDTODisponibili;
	}

	

	/**
	 * Metodo di utilità. Filtra una lista di impianti passata come parametro, in
	 * base al fatto che un determinato sport, anch'esso passato come parametro, sia
	 * o meno praticabile su di essi.
	 * 
	 * @param nomeSport     nome dello sport per cui filtrare.
	 * @param listaImpianti lista degli impianti da filtrare in base allo sport.
	 * @return lista dei soli impianti della lista passata come parametro, in cui
	 *         sia praticabile lo sport dato come parametro.
	 */
	private List<Impianto> filtraImpiantiPerSport(String nomeSport, List<Impianto> listaImpianti) {
		return getRegistroImpianti().filtraImpiantiPerSport(getRegistroSport().getSportByNome(nomeSport), listaImpianti);
	}

	/**
	 * Metodo di utilità. Filtra una lista di istruttori passata come parametro,
	 * sulla base di uno sport il cui nome è passato come parametro.
	 * 
	 * @param nomeSport       nome dello sport per cui filtrare gli istruttori.
	 * @param listaIstruttori lista degli istruttori da filtrare.
	 * @return lista dei soli istruttori della lista data, associati allo sport
	 *         passato come parametro.
	 */
	private List<UtentePolisportivaAbstract> filtraIstruttoriPerSport(String nomeSport,
			List<UtentePolisportivaAbstract> listaIstruttori) {

		Sport sportPerCuiFiltrare = this.getRegistroSport().getSportByNome(nomeSport);
		return this.getRegistroUtenti().filtraIstruttoriPerSport(listaIstruttori, sportPerCuiFiltrare);

	}

	

	/**
	 * Metodo di utilità. Resituisce la lista, in formato DTO, degli istruttori
	 * della polisportiva, filtrati sulla base dello sport e dell'orario selezionati
	 * in fase di compilazione della prenotazione e passati in una mappa come
	 * parametro. Il filtraggio comincia a partire dalla lista completa degli
	 * istruttori, filtrando per orario e poi per sport, se questi parametri sono
	 * presenti. Non è necessario che siano presenti entrambi.
	 * 
	 * @param dati mappa con orario e sport per cui filtrare gli istruttori.
	 * @return lista dei soli istruttori associati allo sport scelto, liberi
	 *         nell'orario selezionato in fase di prenotazione.
	 */
	protected List<UtentePolisportivaDTO> getIstruttoriDTODisponibili(Map<String, Object> dati) {
		List<UtentePolisportivaAbstract> istruttoriDisponibili = this.getRegistroUtenti()
				.getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE);
		if (dati.containsKey("orario")) {
			Map<String, String> orario = (HashMap<String, String>) dati.get("orario");
			istruttoriDisponibili = getRegistroUtenti().filtraIstruttorePerOrario(
					istruttoriDisponibili,
					LocalDateTime.parse(orario.get("oraInizio"),
							DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
					LocalDateTime.parse(orario.get("oraFine"),
							DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"))
					);

			if (dati.containsKey("sport")) {
				istruttoriDisponibili = this.filtraIstruttoriPerSport((String) dati.get("sport"),
						istruttoriDisponibili);

			}
		} else if (dati.containsKey("sport")) {
			istruttoriDisponibili = this.filtraIstruttoriPerSport((String) dati.get("sport"), istruttoriDisponibili);
		}
		List<UtentePolisportivaDTO> listaIstruttoriDTODisponibili = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportivaAbstract istruttore : istruttoriDisponibili) {

			UtentePolisportivaDTO istruttoreDTO = getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(istruttore);
			listaIstruttoriDTODisponibili.add(istruttoreDTO);

		}
		return listaIstruttoriDTODisponibili;
	}

	

	protected QuotaPartecipazione creaQuotaPartecipazione(Appuntamento appuntamento,
			UtentePolisportivaAbstract sportivo) {
		boolean quotaGiaPresente = false;
		for (QuotaPartecipazione quotaPartecipazione : appuntamento.getQuotePartecipazione()) {
			if (quotaPartecipazione.getSportivoAssociato().isEqual(sportivo)) {
				quotaGiaPresente = true;
			}
		}
		if (!quotaGiaPresente) {
			QuotaPartecipazione quota = new QuotaPartecipazione();
			quota.setCosto(appuntamento.getCalcolatoreCosto().calcolaQuotaPartecipazione(appuntamento));
			quota.setSportivoAssociato(sportivo);
			quota.setPagata(false);
			return quota;
		}
		return null;
	}
	
	protected boolean aggiungiPartecipante(Object utente, Appuntamento appuntamento) {
		boolean partecipanteAggiunto = false;
		if (appuntamento.getPartecipantiAppuntamento().size() < appuntamento.getNumeroPartecipantiMassimo()) {
			appuntamento.aggiungiPartecipante(utente);
			partecipanteAggiunto = true;
			if (appuntamento.getPartecipantiAppuntamento().size() >= appuntamento.getSogliaMinimaPartecipantiPerConferma()) {
				appuntamento.confermaAppuntamento();
				appuntamento.getUtentiPartecipanti().forEach((partecipante) -> appuntamento.aggiungiQuotaPartecipazione(
						this.creaQuotaPartecipazione(appuntamento, partecipante)));
			}
			this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
		}
		return partecipanteAggiunto;
	}

	
}
