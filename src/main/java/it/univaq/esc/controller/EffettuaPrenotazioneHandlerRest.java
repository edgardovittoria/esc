package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreDTO;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.RegistroAppuntamenti;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroPrenotazioni;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

@RestController
@RequestMapping("/effettuaPrenotazione")
public class EffettuaPrenotazioneHandlerRest {

    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    @Autowired
    private RegistroImpianti registroImpianti;

    @Autowired
    private RegistroUtentiPolisportiva registroUtenti;

    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;

    private Prenotazione prenotazioneInAtto;

    private String tipoPrenotazioneInAtto;

    private List<Appuntamento> listaAppuntamentiPrenotazioneInAtto = new ArrayList<Appuntamento>();

    public EffettuaPrenotazioneHandlerRest() {
    }

    public List<Appuntamento> getListaAppuntamentiPrenotazioneInAtto() {
        return listaAppuntamentiPrenotazioneInAtto;
    }

    private void setTipoPrenotazioneInAtto(String tipoPrenotazione) {
        this.tipoPrenotazioneInAtto = tipoPrenotazione;
    }

    private String getTipoPrenotazioneInAtto() {
        return this.tipoPrenotazioneInAtto;
    }

    private void setListaAppuntamentiPrenotazioneInAtto(List<Appuntamento> listaAppuntamentiPrenotazioneInAtto) {
        this.listaAppuntamentiPrenotazioneInAtto = listaAppuntamentiPrenotazioneInAtto;
    }

    private void aggiungiAppuntamento(Appuntamento appuntamento) {
        this.getListaAppuntamentiPrenotazioneInAtto().add(appuntamento);
    }

    private CatalogoPrenotabili getListinoPrezziDescrizioniPolisportiva() {
        return listinoPrezziDescrizioniPolisportiva;
    }

    

    private RegistroAppuntamenti getRegistroAppuntamenti() {
        return this.registroAppuntamenti;
    }

    // @GetMapping("/sportPraticabili")
    // @CrossOrigin
    private List<SportDTO> getSportPraticabiliPolisportiva() {
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            for (ImpiantoSpecs specifica : impianto.getSpecificheImpianto()) {
                setSportPraticabili.add(specifica.getSportPraticabile());
            }
        }
        listaSportPraticabili.addAll(setSportPraticabili);
        List<SportDTO> listaSportPraticabiliDTO = new ArrayList<SportDTO>();
        for (Sport sport : listaSportPraticabili) {
            SportDTO sportDTO = new SportDTO();
            sportDTO.impostaValoriDTO(sport);
            listaSportPraticabiliDTO.add(sportDTO);
        }
        return listaSportPraticabiliDTO;
    }

    // @GetMapping("/impiantiDisponibili")
    // @CrossOrigin
    private List<ImpiantoDTO> getImpiantiDisponibili() {
        Calendario calendario = new Calendario();
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            if (!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(calendario)) {
                listaImpiantiDisponibili.add(impianto);
            }
        }
        List<ImpiantoDTO> listaImpiantiDisponibiliDTO = new ArrayList<ImpiantoDTO>();
        for (Impianto impianto : listaImpiantiDisponibili) {
            ImpiantoDTO impiantoDTO = new ImpiantoDTO();
            impiantoDTO.impostaValoriDTO(impianto);
            listaImpiantiDisponibiliDTO.add(impiantoDTO);
        }
        return listaImpiantiDisponibiliDTO;

    }

    // @GetMapping("/sportiviPolisportiva")
    // @CrossOrigin
    private List<SportivoDTO> getSportiviPolisportiva() {
        List<SportivoDTO> listaSportiviDTO = new ArrayList<SportivoDTO>();
        for (UtentePolisportivaAbstract utente : this.registroUtenti.getListaUtenti()) {
            SportivoDTO sportivoDTO = new SportivoDTO();
            sportivoDTO.impostaValoriDTO(utente);
            listaSportiviDTO.add(sportivoDTO);
        }

        return listaSportiviDTO;
    }

    @GetMapping("/avviaNuovaPrenotazione")
    @CrossOrigin
    public @ResponseBody Map<String, Object> avviaNuovaPrenotazioneImpianto(
            @RequestParam(name = "email") String emailSportivoPrenotante,
            @RequestParam(name = "tipoPrenotazione") String tipoPrenotazione) {
        UtentePolisportivaAbstract sportivoPrenotante = this.getRegistroUtenti()
                .getUtenteByEmail(emailSportivoPrenotante);
        int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();

        // PrenotazioneSpecs prenotazioneSpecs =
        // FactorySpecifichePrenotazione.getSpecifichePrenotazione(tipoPrenotazione);
        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione));
        // prenotazioneSpecs.setPrenotazioneAssociata(getPrenotazioneInAtto());
        getPrenotazioneInAtto().setSportivoPrenotante(sportivoPrenotante);

        this.setTipoPrenotazioneInAtto(tipoPrenotazione);

        Map<String, Object> mappaValori = this.getDatiOpzioniPerTipoPrenotazione(tipoPrenotazione);

        return mappaValori;
    }
    

    @PostMapping("/riepilogoPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> getRiepilogoPrenotazioneRicorrenteConCosto(
            @RequestBody FormPrenotaImpianto formPrenotaImpianto) {

        
        for (int i = 0; i < formPrenotaImpianto.getOrariSelezionati().size(); i++) {
            PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione
                    .getSpecifichePrenotazione(this.getTipoPrenotazioneInAtto());
            this.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);
            prenotazioneSpecs.setPrenotazioneAssociata(this.getPrenotazioneInAtto());

            // Creazione calcolatore che poi dovrà finire altrove
            CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
            calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
            // ---------------------------------------------------------------------------------------

            Appuntamento appuntamento = new Appuntamento();
            appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
            appuntamento.setCalcolatoreCosto(calcolatoreCosto);
            appuntamento.aggiungiPartecipante(this.getPrenotazioneInAtto().getSportivoPrenotante());
            this.aggiungiAppuntamento(appuntamento);
        }

        

        PrenotabileDescrizione descrizioneSpecifica = null;
        for (PrenotabileDescrizione desc : this.getListinoPrezziDescrizioniPolisportiva().getCatalogoPrenotabili()) {
            if (desc.getSportAssociato().getNome().equals(formPrenotaImpianto.getSportSelezionato())
                    && desc.getTipoPrenotazione().equals(
                            this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0).getTipoPrenotazione())) {
                descrizioneSpecifica = desc;
            }
        }

        List<UtentePolisportivaAbstract> sportivi = new ArrayList<UtentePolisportivaAbstract>();
        for (String email : formPrenotaImpianto.getSportiviInvitati()) {
            sportivi.add(this.getRegistroUtenti().getUtenteByEmail(email));
        }
        

        for (PrenotazioneSpecs spec : this.getPrenotazioneInAtto().getListaSpecifichePrenotazione()) {
            spec.setSpecificaDescrtiption(descrizioneSpecifica);
        }

        for (OrarioAppuntamento orario : formPrenotaImpianto.getOrariSelezionati()) {
            // Calendario calendarioPrenotazione = new Calendario();
            LocalDateTime dataInizio = LocalDateTime.of(orario.getLocalDataPrenotazione(), orario.getOraInizio());
            LocalDateTime dataFine = LocalDateTime.of(orario.getLocalDataPrenotazione(), orario.getOraFine());

            // calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine ,
            // this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
            // this.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione,
            // this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

            this.getListaAppuntamentiPrenotazioneInAtto().get(formPrenotaImpianto.getOrariSelezionati().indexOf(orario))
                    .setDataOraInizioAppuntamento(dataInizio);
            this.getListaAppuntamentiPrenotazioneInAtto().get(formPrenotaImpianto.getOrariSelezionati().indexOf(orario))
                    .setDataOraFineAppuntamento(dataFine);
            

            HashMap<String, Object> mappaValori = new HashMap<String, Object>();
            mappaValori.put("invitati", sportivi);

        
            Integer idImpianto = 0;
            for(ImpiantoSelezionato impianto : formPrenotaImpianto.getImpianti()){
                if(impianto.getIdSelezione() == orario.getId()){
                    idImpianto = impianto.getIdImpianto();
                }
            }
            
            mappaValori.put("impianto", this.getRegistroImpianti().getImpiantoByID(idImpianto));
                
            this.getListaAppuntamentiPrenotazioneInAtto().get(formPrenotaImpianto.getOrariSelezionati().indexOf(orario)).getPrenotazioneSpecsAppuntamento().impostaValoriSpecificheExtraPrenotazione(mappaValori);
            this.getListaAppuntamentiPrenotazioneInAtto().get(formPrenotaImpianto.getOrariSelezionati().indexOf(orario))
                    .calcolaCosto();
        }


        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        prenDTO.impostaValoriDTO(this.prenotazioneInAtto, this.getListaAppuntamentiPrenotazioneInAtto());
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.OK);

    }

    @PostMapping("/confermaPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> confermaPrenotazione() {

        this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
        this.getRegistroAppuntamenti().salvaListaAppuntamenti(this.getListaAppuntamentiPrenotazioneInAtto());


        for(Appuntamento app : this.getListaAppuntamentiPrenotazioneInAtto()){
            Calendario calendarioDaUnire = new Calendario();
            calendarioDaUnire.aggiungiAppuntamento(app);
            this.registroImpianti
            .aggiornaCalendarioImpianto(
                    (Impianto) this.getPrenotazioneInAtto().getSingolaSpecificaExtra("impianto",
                            app.getPrenotazioneSpecsAppuntamento()),
                    calendarioDaUnire);
        }
        

        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        prenDTO.impostaValoriDTO(this.prenotazioneInAtto, this.getListaAppuntamentiPrenotazioneInAtto());
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.CREATED);
    }

    @PostMapping("/aggiornaImpianti")
    @CrossOrigin
    public @ResponseBody List<ImpiantoDTO> getListaImpianti(@RequestBody HashMap<String, Object> dati) {

        Map<String, String> orario = (HashMap<String, String>) dati.get("orario");

        List<Impianto> impiantiDisponibili = this.getImpiantiDisponibiliByOrario(
                LocalDateTime.parse(orario.get("oraInizio"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
                LocalDateTime.parse(orario.get("oraFine"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")));
        List<ImpiantoDTO> listaImpiantiDTODisponibili = new ArrayList<ImpiantoDTO>();
        for (Impianto impianto : impiantiDisponibili) {
            for (ImpiantoSpecs specifica : impianto.getSpecificheImpianto()) {
                if (specifica.getSportPraticabile().getNome().equals(dati.get("sport"))) {
                    ImpiantoDTO impiantoDTO = new ImpiantoDTO();
                    impiantoDTO.impostaValoriDTO(impianto);
                    listaImpiantiDTODisponibili.add(impiantoDTO);
                }
            }

        }

        return listaImpiantiDTODisponibili;
    }

    @GetMapping("/istruttoriDisponibili")
    @CrossOrigin
    public @ResponseBody List<IstruttoreDTO> getListaIstruttoriPerSport(@RequestBody String sport) {
            List<IstruttoreDTO> listaIstruttori = new ArrayList<IstruttoreDTO>();
            Sport sportRichiesto = null;
            for(Sport sportPolisportiva : this.getSportPraticabili()){
                if(sportPolisportiva.getNome().equals(sport)){
                    sportRichiesto = sportPolisportiva;
                }
            }
            List<UtentePolisportivaAbstract> istruttori = this.getRegistroUtenti().getIstruttoriPerSport(sportRichiesto);
            for(UtentePolisportivaAbstract istruttore : istruttori){
                IstruttoreDTO istDTO = new IstruttoreDTO();
                istDTO.impostaValoriDTO(istruttore);
                listaIstruttori.add(istDTO);
            }

        return listaIstruttori;
    }


    private List<Impianto> getImpiantiDisponibiliByOrario(LocalDateTime oraInizio, LocalDateTime oraFine) {
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
        for (Impianto impianto : this.getRegistroImpianti().getListaImpiantiPolisportiva()) {
            if (!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(oraInizio, oraFine)) {
                listaImpiantiDisponibili.add(impianto);
            }
        }
        return listaImpiantiDisponibili;
    }

    private RegistroUtentiPolisportiva getRegistroUtenti() {
        return this.registroUtenti;
    }

    private RegistroImpianti getRegistroImpianti() {
        return this.registroImpianti;
    }

    private RegistroPrenotazioni getRegistroPrenotazioni() {
        return this.registroPrenotazioni;
    }

    private Prenotazione getPrenotazioneInAtto() {
        return this.prenotazioneInAtto;
    }

    private void setPrenotazioneInAtto(Prenotazione prenotazione) {
        this.prenotazioneInAtto = prenotazione;
    }

    private List<Sport> getSportPraticabili() {
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            for (ImpiantoSpecs specifica : impianto.getSpecificheImpianto()) {
                setSportPraticabili.add(specifica.getSportPraticabile());
            }
        }
        listaSportPraticabili.addAll(setSportPraticabili);
        return listaSportPraticabili;

    }

    private Map<String, Object> getDatiOpzioniPrenotazioneImpianto(){
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        // mappaValori.put("impiantiDisponibili", this.getImpiantiDisponibili());
        mappaValori.put("sportiviPolisportiva", this.getSportiviPolisportiva());

        return mappaValori;
    }

    private Map<String, Object> getDatiOpzioniPrenotazioneLezione(){
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        
        return mappaValori;
    }

    private Map<String, Object> getDatiOpzioniPerTipoPrenotazione(String tipoPrenotazione){
        switch (tipoPrenotazione) {
            case "IMPIANTO":
                return this.getDatiOpzioniPrenotazioneImpianto();
            case "LEZIONE":
                return this.getDatiOpzioniPrenotazioneLezione();
            default:
                return null;
        }
    }
}
