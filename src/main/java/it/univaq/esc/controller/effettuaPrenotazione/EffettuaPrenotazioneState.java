package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.esc.EntityDTOMappers.MapperFactory;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.RegistroQuotePartecipazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
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
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PRIVATE)
public abstract class EffettuaPrenotazioneState {

	private RegistroNotifiche registroNotifiche;
	private RegistroSport registroSport;
	private RegistroImpianti registroImpianti;
	private RegistroUtentiPolisportiva registroUtenti;
	private RegistroAppuntamenti registroAppuntamenti;
	private RegistroPrenotazioni registroPrenotazioni;
	private CatalogoPrenotabili catalogoPrenotabili;
	private RegistroSquadre registroSquadre;
	private RegistroQuotePartecipazione registroQuotePartecipazione;

	@Setter(value = AccessLevel.PUBLIC)
	private ElementiPrenotazioneFactory elementiPrenotazioneFactory;

	@Setter(value = AccessLevel.PUBLIC)
	private MapperFactory mapperFactory;

	public EffettuaPrenotazioneState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre, RegistroQuotePartecipazione registroQuotePartecipazione) {
		setRegistroAppuntamenti(registroAppuntamenti);
		setRegistroImpianti(registroImpianti);
		setCatalogoPrenotabili(catalogoPrenotabili);
		setRegistroNotifiche(registroNotifiche);
		setRegistroPrenotazioni(registroPrenotazioni);
		setRegistroSport(registroSport);
		setRegistroSquadre(registroSquadre);
		setRegistroUtenti(registroUtenti);
		setRegistroQuotePartecipazione(registroQuotePartecipazione);
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
	public abstract Map<String, Object> getDatiInizialiPerLeOpzioniDiPrenotazioneSfruttandoIl(
			EffettuaPrenotazioneHandler effettuaPrenotazioneHandler);

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
	public abstract PrenotazioneDTO impostaPrenotazioneConDatiDellaFormPerRiepilogo(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller);

	/**
	 * Definisce l'interfaccia del metodo che aggiorna gli oggetti intererssati
	 * dalla prenotazione effettuata, una volta che questa è stata salvata nel
	 * registro delle prenotazioni e sul database, in fase di conferma della
	 * prenotazione.
	 * 
	 * @param controller istanza del controller cui lo stato è associato, per
	 *                   poterne aggiornare i dati.
	 */
	public abstract void aggiornaElementiLegatiAllaPrenotazioneConfermata(EffettuaPrenotazioneHandler controller);

	/**
	 * Definisce l'interfaccia del metodo che aggiorna i dati usati per popolare le
	 * opzioni di compilazione della prenotazione, sulla base di quelle già
	 * impostate. Invocato in fase di compilazione delle opzioni di prenotazione.
	 * Prende in input una mappa con i dati delle opzioni già compilate. Restituisce
	 * una mappa con i dati aggiornati per ripopolare le rimanenti opzioni di
	 * prenotazione da impostare.
	 * 
	 * @param datiGiaCompilati mappa con i dati delle opzioni di prenotazione già
	 *                         compilate.
	 * @return mappa con i dati per ripopolare le opzioni di prenotazione ancora da
	 *         compilare.
	 */
	public abstract Map<String, Object> getDatiOpzioniPrenotazioneAggiornatiInBaseAllaMappa(
			Map<String, Object> datiGiaCompilati);

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
	 * prenotazione di un evento che può essere creato solo dal Direttore, o che nel
	 * caso del Direttore necessita di opzioni differenti.
	 * 
	 * @param controller istanza del controller a cui lo stato è associato, in modo
	 *                   tale da poter aggiornarne i dati all'occorrenza.
	 * @return mappa con i dati iniziali delle varie opzioni selezionabili in fase
	 *         di prenotazione.
	 */
	public abstract Map<String, Object> getDatiOpzioniPerPrenotazioneInModalitaDirettore(
			EffettuaPrenotazioneHandler controller);
	
	/**
	 * Metodo di utilità, utilizzato in quelli principali, che resituisce la lista
	 * di tutti gli sport praticabili nella polisportiva, in formato DTO.
	 * 
	 * @return lista degli sport praticabili nella polispsortiva, in formato DTO
	 */
	protected List<SportDTO> getSportPraticabiliNellaPolisportivaInFormatoDTO() {
		List<Sport> listaSportPraticabili = this.getRegistroSport().getListaSportPolisportiva();
		List<SportDTO> listaSportPraticabiliDTO = convertiInDTOGliSportDella(listaSportPraticabili);
		return listaSportPraticabiliDTO;
	}

	private List<SportDTO> convertiInDTOGliSportDella(List<Sport> listaSport) {
		List<SportDTO> listaSportPraticabiliDTO = new ArrayList<SportDTO>();
		for (Sport sport : listaSport) {
			SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(sport);
			listaSportPraticabiliDTO.add(sportDTO);
		}
		return listaSportPraticabiliDTO;
	}

	/**
	 * Metodo di utilità. Restituisce tutti gli utenti registrati nel sistema della
	 * polisportiva che sono SPORTIVI, in formato DTO.
	 * 
	 * @return lista di tutti gli utenti registrati nella polisportiva che hanno il
	 *         ruolo di sportivi in formato DTO.
	 */
	protected List<UtentePolisportivaDTO> getSportiviPolisportivaInFormatoDTO() {
		List<UtentePolisportiva> listaSportiviPolisportiva = getRegistroUtenti()
				.getListaDegliUtentiCheHanno(TipoRuolo.SPORTIVO);
		List<UtentePolisportivaDTO> listaSportiviDTO = convertiInDTOGliUtentiDella(listaSportiviPolisportiva);
		return listaSportiviDTO;
	}

	private List<UtentePolisportivaDTO> convertiInDTOGliUtentiDella(List<UtentePolisportiva> listaUtentiPolisportiva) {
		List<UtentePolisportivaDTO> listaUtentiPolisportivaDTO = new ArrayList<UtentePolisportivaDTO>();
		for (UtentePolisportiva utente : listaUtentiPolisportiva) {
			UtentePolisportivaDTO istruttoreDTO = getMapperFactory().getUtenteMapper()
					.convertiInUtentePolisportivaDTO(utente);
			listaUtentiPolisportivaDTO.add(istruttoreDTO);
		}
		return listaUtentiPolisportivaDTO;
	}

	protected List<UtentePolisportivaDTO> getSportiviDTOLiberiNegliOrariDel(Calendario calendario) {
		List<UtentePolisportiva> listaSportiviLiberi = trovaSportiviLiberiNegliOrariDel(calendario);
		List<UtentePolisportivaDTO> listaSportiviLiberiInDTO = convertiInDTOGliUtentiDella(listaSportiviLiberi);
		return listaSportiviLiberiInDTO;
	}

	private List<UtentePolisportiva> trovaSportiviLiberiNegliOrariDel(Calendario calendario) {
		List<UtentePolisportiva> listaSportivi = getRegistroUtenti().getListaDegliUtentiCheHanno(TipoRuolo.SPORTIVO);
		listaSportivi = getRegistroUtenti().trovaNellaListaGliSportiviLiberiNelleDateDelCalendario(listaSportivi,
				calendario);
		return listaSportivi;
	}

	protected List<UtentePolisportivaDTO> getSportiviDTOLiberiNell(OrarioAppuntamento orarioAppuntamento) {
		List<UtentePolisportiva> listaSportiviLiberiNellOrario = trovaSportiviLiberiNell(orarioAppuntamento);
		List<UtentePolisportivaDTO> listaDTOSportiviLiberiNellOrario = convertiInDTOGliUtentiDella(
				listaSportiviLiberiNellOrario);
		return listaDTOSportiviLiberiNellOrario;
	}

	private List<UtentePolisportiva> trovaSportiviLiberiNell(OrarioAppuntamento orarioAppuntamento) {
		List<UtentePolisportiva> listaSportivi = getRegistroUtenti().getListaDegliUtentiCheHanno(TipoRuolo.SPORTIVO);
		listaSportivi = getRegistroUtenti().trovaNellaListaGliSportiviLiberiNellOrario(listaSportivi,
				orarioAppuntamento);
		return listaSportivi;
	}

	
	protected List<UtentePolisportivaDTO> getTuttiGliUtentiDellaPolisportivaInDTO() {
		List<UtentePolisportiva> listaUtentiPolisportiva = getRegistroUtenti().getListaUtentiPolisportiva();
		List<UtentePolisportivaDTO> listaSportiviDTO = convertiInDTOGliUtentiDella(listaUtentiPolisportiva);
		return listaSportiviDTO;
	}

	
	protected List<UtentePolisportivaDTO> getListaDTODegliIstruttoriCheInsegnanoLo(String sport) {
		List<UtentePolisportiva> listaIstruttoriDelloSportRichiesto = trovaIstruttoriCheInsegnanoLo(sport);
		List<UtentePolisportivaDTO> listaIstruttoriConvertitaInDTO = convertiInDTOGliUtentiDella(
				listaIstruttoriDelloSportRichiesto);
		return listaIstruttoriConvertitaInDTO;
	}

	private List<UtentePolisportiva> trovaIstruttoriCheInsegnanoLo(String sport) {
		Sport sportRichiesto = this.getRegistroSport().getSportByNome(sport);
		List<UtentePolisportiva> listaIstruttoriDelloSportRichiesto = getRegistroUtenti()
				.getListaDegliIstruttoriDello(sportRichiesto);
		return listaIstruttoriDelloSportRichiesto;
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
	protected List<ImpiantoDTO> getListaDTOImpiantiPrenotabiliInBaseAMappa(
			Map<String, Object> datiInseritiInCompilazione) {
		List<Impianto> listaImpiantiDisponibili = trovaImpiantiPrenotabiliInBaseAMappa(datiInseritiInCompilazione);
		List<ImpiantoDTO> listaImpiantiDisponibiliInFormatoDTO = convertiInDTOGliImpiantiDella(
				listaImpiantiDisponibili);
		return listaImpiantiDisponibiliInFormatoDTO;
	}

	private List<Impianto> trovaImpiantiPrenotabiliInBaseAMappa(Map<String, Object> datiInseritiInCompilazione) {
		List<Impianto> listaImpiantiDisponibili = getRegistroImpianti().getListaImpiantiPolisportiva();
		if (datiInseritiInCompilazione.containsKey("orario")) {
			listaImpiantiDisponibili = filtraListaImpiantiInBaseAMappaOrario(listaImpiantiDisponibili,
					(Map<String, String>) datiInseritiInCompilazione.get("orario"));
		}
		if (datiInseritiInCompilazione.containsKey("sport")) {
			listaImpiantiDisponibili = filtraImpiantiPerSportPraticabile(listaImpiantiDisponibili,
					(String) datiInseritiInCompilazione.get("sport"));
		}
		return listaImpiantiDisponibili;
	}

	private List<Impianto> filtraListaImpiantiInBaseAMappaOrario(List<Impianto> listaImpianti,
			Map<String, String> mappaOrario) {
		OrarioAppuntamento orarioAppuntamento = creaOrarioAppuntamentoDa(mappaOrario);
		List<Impianto> listaImpiantiFiltrata = getRegistroImpianti()
				.filtraImpiantiDisponibiliPerOrario(orarioAppuntamento, listaImpianti);
		return listaImpiantiFiltrata;
	}

	private List<Impianto> filtraImpiantiPerSportPraticabile(List<Impianto> listaImpianti, String nomeSport) {
		return getRegistroImpianti().filtraImpiantiPerSport(getRegistroSport().getSportByNome(nomeSport),
				listaImpianti);
	}

	private List<ImpiantoDTO> convertiInDTOGliImpiantiDella(List<Impianto> listaImpianti) {
		List<ImpiantoDTO> listaImpiantiDTODisponibili = new ArrayList<ImpiantoDTO>();
		for (Impianto impianto : listaImpianti) {
			ImpiantoDTO impiantoDTO = getMapperFactory().getImpiantoMapper().convertiInImpiantoDTO(impianto);
			listaImpiantiDTODisponibili.add(impiantoDTO);
		}
		return listaImpiantiDTODisponibili;
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
	protected List<UtentePolisportivaDTO> getIstruttoriDTODisponibili(Map<String, Object> datiCompilati) {
		List<UtentePolisportiva> istruttoriDisponibili = trovaIstruttoriDisponibiliInBaseAllaMappaDei(datiCompilati);
		List<UtentePolisportivaDTO> istruttoriDisponibiliInFormatoDTO = convertiInDTOGliUtentiDella(
				istruttoriDisponibili);
		return istruttoriDisponibiliInFormatoDTO;
	}

	private List<UtentePolisportiva> trovaIstruttoriDisponibiliInBaseAllaMappaDei(Map<String, Object> datiCompilati) {
		List<UtentePolisportiva> istruttoriDisponibili = getRegistroUtenti()
				.getListaDegliUtentiCheHanno(TipoRuolo.ISTRUTTORE);
		if (datiCompilati.containsKey("orario")) {
			istruttoriDisponibili = trovaNellaListaGliIstruttoriLiberiNellOrario(istruttoriDisponibili,
					(Map<String, String>) datiCompilati.get("orario"));
		}
		if (datiCompilati.containsKey("sport")) {
			istruttoriDisponibili = trovaNellaListaGliIstruttoriCheInsegnanoLoSport((String) datiCompilati.get("sport"),
					istruttoriDisponibili);
		}
		return istruttoriDisponibili;
	}

	private List<UtentePolisportiva> trovaNellaListaGliIstruttoriLiberiNellOrario(
			List<UtentePolisportiva> listaIstruttori, Map<String, String> mappaOrario) {
		OrarioAppuntamento orarioAppuntamento = creaOrarioAppuntamentoDa(mappaOrario);
		List<UtentePolisportiva> listaIstruttoriFiltrataPerOrario = getRegistroUtenti()
				.trovaNellaListaIstruttoriQuelliLiberiNellOrario(listaIstruttori, orarioAppuntamento);
		return listaIstruttoriFiltrataPerOrario;
	}

	private List<UtentePolisportiva> trovaNellaListaGliIstruttoriCheInsegnanoLoSport(String nomeSport,
			List<UtentePolisportiva> listaIstruttori) {
		Sport sportPerCuiFiltrare = this.getRegistroSport().getSportByNome(nomeSport);
		return this.getRegistroUtenti().filtraListaIstruttoriPerSportInsegnato(listaIstruttori, sportPerCuiFiltrare);

	}

	protected OrarioAppuntamento creaOrarioAppuntamentoDa(Map<String, String> mappaOrario) {
		OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
		orarioAppuntamento.imposta(mappaOrario.get("oraInizio"), mappaOrario.get("oraFine"));
		return orarioAppuntamento;
	}

}
