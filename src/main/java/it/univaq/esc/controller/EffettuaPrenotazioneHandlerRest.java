package it.univaq.esc.controller;



import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import it.univaq.esc.dtoObjects.IFormPrenotabile;


import it.univaq.esc.dtoObjects.IstruttoreDTO;

import it.univaq.esc.dtoObjects.PrenotazioneDTO;

import it.univaq.esc.factory.FactoryStatoEffettuaPrenotazione;
import it.univaq.esc.model.Appuntamento;


import it.univaq.esc.model.prenotazioni.Prenotazione;

import it.univaq.esc.model.RegistroAppuntamenti;

import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;

import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

@RestController
@RequestMapping("/effettuaPrenotazione")
public class EffettuaPrenotazioneHandlerRest {
    @Autowired
    private FactoryStatoEffettuaPrenotazione factoryStati;

    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // @Autowired
    // private RegistroImpianti registroImpianti;

    @Autowired
    private RegistroUtentiPolisportiva registroUtenti;

    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;

    private Prenotazione prenotazioneInAtto;

    private String tipoPrenotazioneInAtto;

    private List<Appuntamento> listaAppuntamentiPrenotazioneInAtto = new ArrayList<Appuntamento>();

    private EffettuaPrenotazioneState stato;

    public EffettuaPrenotazioneHandlerRest() {}

    private FactoryStatoEffettuaPrenotazione getFactoryStati() {
        return factoryStati;
    }



    private EffettuaPrenotazioneState getStato() {
        return stato;
    }

    private void setStato(EffettuaPrenotazioneState stato) {
        this.stato = stato;
    }

    public List<Appuntamento> getListaAppuntamentiPrenotazioneInAtto() {
        return listaAppuntamentiPrenotazioneInAtto;
    }

    private void setTipoPrenotazioneInAtto(String tipoPrenotazione) {
        this.tipoPrenotazioneInAtto = tipoPrenotazione;
        this.setStato(this.getFactoryStati().getStato(tipoPrenotazione));
    }

    public String getTipoPrenotazioneInAtto() {
        return this.tipoPrenotazioneInAtto;
    }

    private void setListaAppuntamentiPrenotazioneInAtto(List<Appuntamento> listaAppuntamentiPrenotazioneInAtto) {
        this.listaAppuntamentiPrenotazioneInAtto = listaAppuntamentiPrenotazioneInAtto;
    }

    public void aggiungiAppuntamento(Appuntamento appuntamento) {
        this.getListaAppuntamentiPrenotazioneInAtto().add(appuntamento);
    }

    public CatalogoPrenotabili getListinoPrezziDescrizioniPolisportiva() {
        return listinoPrezziDescrizioniPolisportiva;
    }

    

    private RegistroAppuntamenti getRegistroAppuntamenti() {
        return this.registroAppuntamenti;
    }

    
   

    // @GetMapping("/impiantiDisponibili")
    // @CrossOrigin
    // private List<ImpiantoDTO> getImpiantiDisponibili() {
    //     Calendario calendario = new Calendario();
    //     List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
    //     for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
    //         if (!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(calendario)) {
    //             listaImpiantiDisponibili.add(impianto);
    //         }
    //     }
    //     List<ImpiantoDTO> listaImpiantiDisponibiliDTO = new ArrayList<ImpiantoDTO>();
    //     for (Impianto impianto : listaImpiantiDisponibili) {
    //         ImpiantoDTO impiantoDTO = new ImpiantoDTO();
    //         impiantoDTO.impostaValoriDTO(impianto);
    //         listaImpiantiDisponibiliDTO.add(impiantoDTO);
    //     }
    //     return listaImpiantiDisponibiliDTO;

    // }

    

    @GetMapping("/avviaNuovaPrenotazione")
    @CrossOrigin
    public @ResponseBody Map<String, Object> avviaNuovaPrenotazioneImpianto(
            @RequestParam(name = "email") String emailSportivoPrenotante,
            @RequestParam(name = "tipoPrenotazione") String tipoPrenotazione) {
        UtentePolisportivaAbstract sportivoPrenotante = this.getRegistroUtenti()
                .getUtenteByEmail(emailSportivoPrenotante);
        int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();

        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione));
        getPrenotazioneInAtto().setSportivoPrenotante(sportivoPrenotante);

        this.setTipoPrenotazioneInAtto(tipoPrenotazione);

        return this.getStato().getDatiOpzioni();
    }
    
    @PostMapping("/aggiornaOpzioniPrenotazione")
    @CrossOrigin
    public @ResponseBody Map<String, Object> aggiornaOpzioniPrenotazione(@RequestBody HashMap<String, Object> dati) {
        return null;
    }

    @PostMapping("/riepilogoPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> getRiepilogoPrenotazioneRicorrenteConCosto(
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

    @PostMapping("/confermaPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> confermaPrenotazione() {

        this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
        this.getRegistroAppuntamenti().salvaListaAppuntamenti(this.getListaAppuntamentiPrenotazioneInAtto());


        this.getStato().aggiornaElementiDopoConfermaPrenotazione(this);

        //simpMessagingTemplate.convertAndSendToUser("pippofranco", "/user", "notifica inviata");
        simpMessagingTemplate.convertAndSendToUser("pippofranco", "/inviti", "notifica inviata");

        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        Map<String, Object> mappa = new HashMap<String, Object>();
        mappa.put("prenotazione", this.getPrenotazioneInAtto());
        mappa.put("appuntamentiPrenotazione", this.getListaAppuntamentiPrenotazioneInAtto());
        prenDTO.impostaValoriDTO(mappa);
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.CREATED);
    }

    // @MessageMapping("/creaNotifica")
    // @SendTo("/inviti")
    // public List<String> send(@Payload String email) {
    //     System.out.println(email);
    //     List<String> lista = new ArrayList<String> ();
    //     lista.add("pippo");
    //     lista.add("gino");
    //     return lista;
    // }


    @PostMapping("/aggiornaDatiOpzioni")
    @CrossOrigin
    public @ResponseBody Map<String, Object> getDatiOpzioniAggiornati(@RequestBody HashMap<String, Object> dati) {
        return this.getStato().aggiornaOpzioniPrenotazione(dati);
    }

    // @PostMapping("/aggiornaImpianti")
    // @CrossOrigin
    // public @ResponseBody List<ImpiantoDTO> getListaImpianti(@RequestBody HashMap<String, Object> dati) {

    //     Map<String, String> orario = (HashMap<String, String>) dati.get("orario");

    //     List<Impianto> impiantiDisponibili = this.getImpiantiDisponibiliByOrario(
    //             LocalDateTime.parse(orario.get("oraInizio"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
    //             LocalDateTime.parse(orario.get("oraFine"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")));
    //     List<ImpiantoDTO> listaImpiantiDTODisponibili = new ArrayList<ImpiantoDTO>();
    //     for (Impianto impianto : impiantiDisponibili) {
    //         for (ImpiantoSpecs specifica : impianto.getSpecificheImpianto()) {
    //             if (specifica.getSportPraticabile().getNome().equals(dati.get("sport"))) {
    //                 ImpiantoDTO impiantoDTO = new ImpiantoDTO();
    //                 impiantoDTO.impostaValoriDTO(impianto);
    //                 listaImpiantiDTODisponibili.add(impiantoDTO);
    //             }
    //         }

    //     }

    //     return listaImpiantiDTODisponibili;
    // }

    @GetMapping("/istruttoriDisponibili")
    @CrossOrigin
    public @ResponseBody List<IstruttoreDTO> getListaIstruttoriPerSport(@RequestParam(name = "sport") String sport) {
            return this.getStato().getIstruttoriPerSport(sport);
    }


    // private List<Impianto> getImpiantiDisponibiliByOrario(LocalDateTime oraInizio, LocalDateTime oraFine) {
    //     List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
    //     for (Impianto impianto : this.getRegistroImpianti().getListaImpiantiPolisportiva()) {
    //         if (!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(oraInizio, oraFine)) {
    //             listaImpiantiDisponibili.add(impianto);
    //         }
    //     }
    //     return listaImpiantiDisponibili;
    // }

    private RegistroUtentiPolisportiva getRegistroUtenti() {
        return this.registroUtenti;
    }

    // private RegistroImpianti getRegistroImpianti() {
    //     return this.registroImpianti;
    // }

    private RegistroPrenotazioni getRegistroPrenotazioni() {
        return this.registroPrenotazioni;
    }

    public Prenotazione getPrenotazioneInAtto() {
        return this.prenotazioneInAtto;
    }

    private void setPrenotazioneInAtto(Prenotazione prenotazione) {
        this.prenotazioneInAtto = prenotazione;
    }


}
