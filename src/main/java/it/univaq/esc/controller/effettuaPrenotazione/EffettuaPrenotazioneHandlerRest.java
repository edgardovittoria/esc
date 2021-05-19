package it.univaq.esc.controller.effettuaPrenotazione;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.IFormPrenotabile;

import it.univaq.esc.dtoObjects.IstruttoreDTO;

import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Appuntamento;

import it.univaq.esc.model.prenotazioni.Prenotazione;

import it.univaq.esc.model.RegistroAppuntamenti;

import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;

import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

/**
 * Controller che gestisce la creazione di nuove prenotazioni, così come la partecipazione ad un evento preesistente.
 * @author esc
 *
 */
@RestController
@RequestMapping("/effettuaPrenotazione")
public class EffettuaPrenotazioneHandlerRest {
	
	/**
	 * Factory FactoryStaty utilizzata per ottenere gli stati del controller.
	 */
    @Autowired
    private FactoryStatoEffettuaPrenotazione factoryStati;

    
    /**
     * Registro degli appuntamenti, utilizzato per le operazioni di gestione della lista degli appuntamenti
     * della prenotazione in atto.
     */
    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    
    /**
     * Attributo per l'invio di notifiche tramite WebSocket.
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    
    /**
     * Registro degli utenti della polisportiva, usato per tutte le operazioni di gestione degli 
     * utenti interessati dalla prenotazione in atto
     */
    @Autowired
    private RegistroUtentiPolisportiva registroUtenti;

    
    /**
     * Registro delle prenotazioni, utilizzato per la getsione della prenotazione in atto.
     */
    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    
    /**
     * Catalogo con le descrizioni delle prenotazioni effettuabili, 
     * necessarie per operazioni come il calcolo delle quote di partecipazione della prenotazione in atto.
     */
    @Autowired
    private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;

    
    /**
     * Prenotazione in atto gestita dal controller.
     */
    private Prenotazione prenotazioneInAtto;

    /**
     * Tipo della prenotazione in atto gestita dal controller.
     */
    private String tipoPrenotazioneInAtto;

    
    /**
     * Lista degli appuntamenti associati alla prenotaizione in atto gestita dal controller.
     */
    private List<Appuntamento> listaAppuntamentiPrenotazioneInAtto = new ArrayList<Appuntamento>();

    
    /**
     * Stato del controller, dipendente dal tipo della prenotazione in atto.
     * Incapsula tutte le operazioni le cui implementazioni dipendono dal tipo della prenotazione in atto,
     * disaccoppiando di fatto il controller dalla tipologia di prenotazione.
     */
    private EffettuaPrenotazioneState stato;

    
    /**
     * Costruttore del controller EffettuaPrenotazioneHandlerRest
     */
    public EffettuaPrenotazioneHandlerRest() {
    }

    /**
     * Restituisce la factory FactoryStati, utilizzata per ottenere gli stati del controller
     * @return la factory FactoryStati
     */
    private FactoryStatoEffettuaPrenotazione getFactoryStati() {
        return factoryStati;
    }

    
    /**
     * Restituisce lo Stato attuale del controller
     * @return stato attuale del controller
     */
    private EffettuaPrenotazioneState getStato() {
        return stato;
    }

    
    /**
     * Imposta lo stato passato come parametro nel controller
     * @param stato stato del controller da impostare
     */
    private void setStato(EffettuaPrenotazioneState stato) {
        this.stato = stato;
    }

    /**
     * Restituisce la lista completa degli appuntamenti associati alla prenotazione in atto
     * @return lista di appuntamenti associati alla prenotazione in atto
     */
    public List<Appuntamento> getListaAppuntamentiPrenotazioneInAtto() {
        return listaAppuntamentiPrenotazioneInAtto;
    }

    /**
     * Imposta la tipologia della prenotazione in atto, passata come parametro.
     * In base alla tipologia imposta poi lo Stato corretto nel controller, tramite la factory FactoryStati.
     * @param tipoPrenotazione tipo della prenotazione in atto
     */
    private void setTipoPrenotazioneInAtto(String tipoPrenotazione) {
        this.tipoPrenotazioneInAtto = tipoPrenotazione;
        this.setStato(this.getFactoryStati().getStato(tipoPrenotazione));
    }

    /**
     * Restituisce la tipologia della prenotazione in atto
     * @return tipo della prenotazione in atto
     */
    public String getTipoPrenotazioneInAtto() {
        return this.tipoPrenotazioneInAtto;
    }

    /**
     * Imposta l'intera lista degli appuntamenti da associare alla prenotazione in atto
     * @param listaAppuntamentiPrenotazioneInAtto  lista completa degli appuntamenti da associare alla prenotazione in atto
     */
    private void setListaAppuntamentiPrenotazioneInAtto(List<Appuntamento> listaAppuntamentiPrenotazioneInAtto) {
        this.listaAppuntamentiPrenotazioneInAtto = listaAppuntamentiPrenotazioneInAtto;
    }

    /**
     * Aggiunge l'appuntamento passato come parametro alla lista degli appuntamenti associati alla prenotazione in atto
     * @param appuntamento da associare alla prenotazione in atto
     */
    public void aggiungiAppuntamento(Appuntamento appuntamento) {
        this.getListaAppuntamentiPrenotazioneInAtto().add(appuntamento);
    }

    /**
     * Restituisce il catalogo con le descrizioni (comprensive di prezzi) delle prenotazioni effettuabili
     * @return il catalogo delle descrizioni delle prenotazioni effettuabili
     */
    public CatalogoPrenotabili getListinoPrezziDescrizioniPolisportiva() {
        return listinoPrezziDescrizioniPolisportiva;
    }

    /**
     * Restituisce il registro degli appuntamenti
     * @return il registro appuntamenti
     */
    private RegistroAppuntamenti getRegistroAppuntamenti() {
        return this.registroAppuntamenti;
    }

   

    /**
     * Avvia una nuova prenotazione.
     * Crea una prenotazione e imposta l'utente che la effettua.
     * Salva poi la tipologia della prenotazione per utilizzarla in operazioni successive e, in base a questa, imposta lo Stato corretto.
     * Infine, tramite lo Stato, restituisce una mappa di dati per la fase di compilazione.
     * 
     * @param emailSportivoPrenotante email dell'utente che effettua la prenotazione.
     * @param tipoPrenotazione tipologia di prenotazione selezionata
     * @return mappa di dati per la fase di compilazione della prenotazione.
     */
    @GetMapping("/avviaNuovaPrenotazione")
    @CrossOrigin
    public @ResponseBody Map<String, Object> avviaNuovaPrenotazione(
            @RequestParam(name = "email") String emailSportivoPrenotante,
            @RequestParam(name = "tipoPrenotazione") String tipoPrenotazione) {
        
    	this.inizializzaNuovaPrenotazione(emailSportivoPrenotante, tipoPrenotazione);
        return this.getStato().getDatiOpzioni(this);
    }

    /**
     * Avvia una nuova prenotazione di eventi creabili solo dal direttore, o che in modalità direttore devono offrire opzioni differenti.
     * Crea una prenotazione e imposta l'utente che la effettua, il direttore.
     * Salva poi la tipologia della prenotazione per utilizzarla in operazioni successive e, in base a questa, imposta lo Stato corretto.
     * Infine, tramite lo Stato, restituisce una mappa di dati per la fase di compilazione.
     * 
     * @param emailDirettore email del direttore che effettua la prenotazione.
     * @param tipoPrenotazione tipologia di prenotazione selezionata
     * @return mappa di dati per la fase di compilazione della prenotazione.
     */
    @GetMapping("/avviaNuovaPrenotazioneEventoDirettore")
    @CrossOrigin
    public @ResponseBody Map<String, Object> avviaNuovaPrenotazioneEventoDirettore(@RequestParam(name = "email") String emailDirettore, @RequestParam(name = "tipoPrenotazione") String tipoPrenotazione){
    	this.inizializzaNuovaPrenotazione(emailDirettore, tipoPrenotazione);
    	return this.getStato().getDatiOpzioniModalitaDirettore(this);
    }
    
    @PostMapping("/aggiornaOpzioniPrenotazione")
    @CrossOrigin
    public @ResponseBody Map<String, Object> aggiornaOpzioniPrenotazione(@RequestBody HashMap<String, Object> dati) {
        return null;
    }

    
    private void inizializzaNuovaPrenotazione(String emailPrenotante, String tipoPrenotazione) {
    	UtentePolisportivaAbstract sportivoPrenotante = this.getRegistroUtenti()
                .getUtenteByEmail(emailPrenotante);
        int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();

        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione));
        getPrenotazioneInAtto().setSportivoPrenotante(sportivoPrenotante);

        this.setTipoPrenotazioneInAtto(tipoPrenotazione);
    }
    
    /**
     * Imposta i valori della prenotazione passati come parametro, delegando tutte le operazioni di assegnazione allo Stato.
     * Restituisce la prenotazione in formato DTO per l'utilizzo lato client.
     * @param formPrenotaImpianto oggetto DTO su cui vengono mappati i dati selezionati in fase di compilazione.
     * @return prenotazione compilata in formato DTO.
     */
    @PostMapping("/riepilogoPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> getRiepilogoPrenotazione(
            @RequestBody IFormPrenotabile formPrenotaImpianto) {
        this.getListaAppuntamentiPrenotazioneInAtto().clear();

        this.getStato().impostaDatiPrenotazione(formPrenotaImpianto, this);

        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        Map<String, Object> mappa = new HashMap<String, Object>();
        mappa.put("prenotazione", this.getPrenotazioneInAtto());
        mappa.put("appuntamentiPrenotazione", this.getListaAppuntamentiPrenotazioneInAtto());
        prenDTO.impostaValoriDTO(mappa);
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.OK);

    }

    
    /**
     * Salva la prenotazione compilata nel RegistroPrenotazione e sul database.
     * Tramite lo Stato aggiorna poi eventuali oggetti software legati alla prenotazione
     * Restituisce la prenotazione DTO con tutti i campi aggiornati, compresi gli id generati automaticamente dal database.
     * @return prenotazione effettuata, in formato DTO.
     */
    @PostMapping("/confermaPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> confermaPrenotazione() {

        this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
        this.getRegistroAppuntamenti().salvaListaAppuntamenti(this.getListaAppuntamentiPrenotazioneInAtto());

        this.getStato().aggiornaElementiDopoConfermaPrenotazione(this);

        // simpMessagingTemplate.convertAndSendToUser("pippofranco", "/user", "notifica
        // inviata");
        simpMessagingTemplate.convertAndSendToUser("pippofranco", "/inviti", "notifica inviata");

        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        Map<String, Object> mappa = new HashMap<String, Object>();
        mappa.put("prenotazione", this.getPrenotazioneInAtto());
        mappa.put("appuntamentiPrenotazione", this.getListaAppuntamentiPrenotazioneInAtto());
        prenDTO.impostaValoriDTO(mappa);
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.CREATED);
    }

    
    /**
     * Metodo che restituisce una mappa di dati per la compilazione della prenotazione, aggiornata in base ad opzioni già compilate
     * e passate in una mappa come parametro.
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
    public @ResponseBody List<IstruttoreDTO> getListaIstruttoriPerSport(@RequestParam(name = "sport") String sport) {
        return this.getStato().getIstruttoriPerSport(sport);
    }

    
    
    /**
     * Aggiunge l'utente ad un evento preesistente, associandogli una relativa quota di prenotazione
     * @param mappaDati mappa con l'id dell'evento a cui si vuole partecipare e l'email dell'utente.
     * @return Restituisce l'evento asggiornato, in formato DTO.
     */
    @PatchMapping("/partecipazioneEventoEsistente")
    @CrossOrigin
    public @ResponseBody Object aggiungiPartecipanteAEventoEsistente(
            @RequestBody Map<String, Object> mappaDati) {
        
        
        Integer idEvento = (Integer) mappaDati.get("idEvento");
        String emailPartecipante = (String) mappaDati.get("emailPartecipante");
        return this.getStato().aggiungiPartecipanteAEventoEsistente(idEvento, emailPartecipante);
    }

   

    /**
     * Restituisce il registro degli utenti della polisportiva
     * @return registro utenti della polisportiva
     */
    private RegistroUtentiPolisportiva getRegistroUtenti() {
        return this.registroUtenti;
    }

    

    /**
     * Restituisce il registro delle prenotazioni
     * @return registro delle prenotazioni
     */
    private RegistroPrenotazioni getRegistroPrenotazioni() {
        return this.registroPrenotazioni;
    }

    /**
     * Restituisce la prenotazione in atto, ovvero quella in fase di compilazione
     * @return la prenotazione in atto.
     */
    public Prenotazione getPrenotazioneInAtto() {
        return this.prenotazioneInAtto;
    }

    
    /**
     * Imposta la prenotazione in atto nel controller
     * @param prenotazione in atto da impostare
     */
    private void setPrenotazioneInAtto(Prenotazione prenotazione) {
        this.prenotazioneInAtto = prenotazione;
    }

    /**
     * Restituisce l'utente che sta effettuando la prenotazione in atto
     * @return utente prenotante associato alla prenotazione in atto
     */
    public UtentePolisportivaAbstract getSportivoPrenotante() {
        return this.getPrenotazioneInAtto().getSportivoPrenotante();
    }

}
