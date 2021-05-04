package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import it.univaq.esc.dtoObjects.ImpiantoDTO;
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
    private RegistroUtentiPolisportiva registroSportivi;

    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;

    private Prenotazione prenotazioneInAtto;

    private List<Appuntamento> listaAppuntamentiPrenotazioneInAtto = new ArrayList<Appuntamento>();
    
    public EffettuaPrenotazioneHandlerRest(){}


    public List<Appuntamento> getListaAppuntamentiPrenotazioneInAtto() {
        return listaAppuntamentiPrenotazioneInAtto;
    }


    private void setListaAppuntamentiPrenotazioneInAtto(List<Appuntamento> listaAppuntamentiPrenotazioneInAtto) {
        this.listaAppuntamentiPrenotazioneInAtto = listaAppuntamentiPrenotazioneInAtto;
    }

    private void aggiungiAppuntamento(Appuntamento appuntamento){
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
    private List<SportDTO> getSportPraticabiliPolisportiva(){
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            for(ImpiantoSpecs specifica : impianto.getSpecificheImpianto()){
                setSportPraticabili.add(specifica.getSportPraticabile());
            }
        }
        listaSportPraticabili.addAll(setSportPraticabili);
        List<SportDTO> listaSportPraticabiliDTO = new ArrayList<SportDTO>();
        for(Sport sport : listaSportPraticabili){
            SportDTO sportDTO = new SportDTO();
            sportDTO.impostaValoriDTO(sport);
            listaSportPraticabiliDTO.add(sportDTO);
        }
        return listaSportPraticabiliDTO;
    }


    // @GetMapping("/impiantiDisponibili")
    // @CrossOrigin
    private List<ImpiantoDTO> getImpiantiDisponibili(){
        Calendario calendario = new Calendario();
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();        
        for(Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()){
            if(!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(calendario)){
                listaImpiantiDisponibili.add(impianto);
            }
        }
        List<ImpiantoDTO> listaImpiantiDisponibiliDTO = new ArrayList<ImpiantoDTO>();  
        for(Impianto impianto : listaImpiantiDisponibili){
            ImpiantoDTO impiantoDTO = new ImpiantoDTO();
            impiantoDTO.impostaValoriDTO(impianto);
            listaImpiantiDisponibiliDTO.add(impiantoDTO);
        }
        return listaImpiantiDisponibiliDTO;
        
    }

    // @GetMapping("/sportiviPolisportiva")
    // @CrossOrigin
    private List<SportivoDTO> getSportiviPolisportiva(){
        List<SportivoDTO> listaSportiviDTO = new ArrayList<SportivoDTO>();
        for(UtentePolisportivaAbstract utente : this.registroSportivi.getListaSportivi()){
            SportivoDTO sportivoDTO = new SportivoDTO();
            sportivoDTO.impostaValoriDTO(utente);
            listaSportiviDTO.add(sportivoDTO);
        }

        return listaSportiviDTO;
    }

        @GetMapping("/avviaNuovaPrenotazione")
        @CrossOrigin
        public @ResponseBody Map<String, Object> avviaNuovaPrenotazioneImpianto(@RequestParam(name="email") String emailSportivoPrenotante, @RequestParam(name="tipoPrenotazione") String tipoPrenotazione){
            UtentePolisportivaAbstract sportivoPrenotante = this.getRegistroSportivi().getSportivoDaEmail(emailSportivoPrenotante);
            int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();
            
            PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione.getSpecifichePrenotazione(tipoPrenotazione);
            setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione, prenotazioneSpecs));
            prenotazioneSpecs.setPrenotazioneAssociata(getPrenotazioneInAtto());
            getPrenotazioneInAtto().setSportivoPrenotante(sportivoPrenotante);

            // Creazione calcolatore che poi dovrà finire altrove
            CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
            calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
            //---------------------------------------------------------------------------------------

            Appuntamento appuntamento = new Appuntamento();
            appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
            appuntamento.setCalcolatoreCosto(calcolatoreCosto);
            appuntamento.aggiungiPartecipante(sportivoPrenotante);

            this.getListaAppuntamentiPrenotazioneInAtto().clear();

            this.aggiungiAppuntamento(appuntamento);
           
            Map<String, Object> mappaValori = new HashMap<String, Object>();
            mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
            //mappaValori.put("impiantiDisponibili", this.getImpiantiDisponibili());
            mappaValori.put("sportiviPolisportiva", this.getSportiviPolisportiva());


            


            return mappaValori;
        }

        
        @PostMapping("/riepilogoPrenotazione")
        @CrossOrigin
        public ResponseEntity<PrenotazioneDTO> getRiepilogoPrenotazioneConCosto(@RequestBody FormPrenotaImpianto formPrenotaImpianto){

            HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        
        
        
        for(PrenotabileDescrizione desc : this.getListinoPrezziDescrizioniPolisportiva().getCatalogoPrenotabili()){
            if(desc.getSportAssociato().getNome().equals(formPrenotaImpianto.getSportSelezionato()) && desc.getTipoPrenotazione().equals(this.prenotazioneInAtto.getListaSpecifichePrenotazione().get(0).getTipoPrenotazione())){
                this.prenotazioneInAtto.getListaSpecifichePrenotazione().get(0).setSpecificaDescrtiption(desc);
            }
        }

        
        
        
        
        // Calendario calendarioPrenotazione = new Calendario();
        LocalDateTime dataInizio = LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraInizio());
        LocalDateTime dataFine = LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraFine());

        // calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine , this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
        //this.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione, this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

        Appuntamento appuntamento = this.getListaAppuntamentiPrenotazioneInAtto().get(0);
        appuntamento.setDataOraInizioAppuntamento(dataInizio);
        appuntamento.setDataOraFineAppuntamento(dataFine);

        List<UtentePolisportivaAbstract> sportivi = new ArrayList<UtentePolisportivaAbstract>();
        for(String email : formPrenotaImpianto.getSportiviInvitati()){
            sportivi.add(this.getRegistroSportivi().getSportivoDaEmail(email));
        }

        mappaValori.put("invitati", sportivi);
        mappaValori.put("impianto", this.registroImpianti.getImpiantoByID(formPrenotaImpianto.getImpianto()));

        this.getPrenotazioneInAtto().impostaValoriSpecificheExtraSingolaPrenotazioneSpecs(mappaValori, this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

        //Calcolo costo prenotazioneSpecs
        appuntamento.calcolaCosto();
        //----------------------------------

        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        prenDTO.impostaValoriDTO(this.prenotazioneInAtto, this.getListaAppuntamentiPrenotazioneInAtto());
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.OK);
       
    }







        @PostMapping("/confermaPrenotazione")
    @CrossOrigin
    public ResponseEntity<PrenotazioneDTO> confermaPrenotazione(){
        
        
        
        Calendario calendarioDaUnire = new Calendario(this.getListaAppuntamentiPrenotazioneInAtto());

        this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
        this.getRegistroAppuntamenti().salvaListaAppuntamenti(this.getListaAppuntamentiPrenotazioneInAtto());
        this.registroImpianti.aggiornaCalendarioImpianto((Impianto)this.getPrenotazioneInAtto().getSingolaSpecificaExtra("impianto", this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0)), calendarioDaUnire);

        
        PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        prenDTO.impostaValoriDTO(this.prenotazioneInAtto, this.getListaAppuntamentiPrenotazioneInAtto());
        return new ResponseEntity<PrenotazioneDTO>(prenDTO, HttpStatus.CREATED);
    }



        @PostMapping("/aggiornaImpianti")
        @CrossOrigin
        public @ResponseBody List<ImpiantoDTO> getListaImpianti(@RequestBody HashMap<String, Object> dati){        

        
        Map<String, String> orario = (HashMap<String, String>)dati.get("orario");
        
        List<Impianto> impiantiDisponibili =  this.getImpiantiDisponibiliByOrario(LocalDateTime.parse(orario.get("dataOraInizio"), DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" )), LocalDateTime.parse(orario.get("dataOraFine"), DateTimeFormatter.ofPattern ( "yyyy-MM-dd'T'HH:mm:ss.SSSX" )));
        List<ImpiantoDTO> listaImpiantiDTODisponibili = new ArrayList<ImpiantoDTO>();
        for(Impianto impianto : impiantiDisponibili){
            for(ImpiantoSpecs specifica : impianto.getSpecificheImpianto()){
                if(specifica.getSportPraticabile().getNome().equals(dati.get("sport"))){
                    ImpiantoDTO impiantoDTO = new ImpiantoDTO();
                    impiantoDTO.impostaValoriDTO(impianto);
                    listaImpiantiDTODisponibili.add(impiantoDTO);
                }
            }
            
        } 

        return listaImpiantiDTODisponibili;
    }

    private List<Impianto> getImpiantiDisponibiliByOrario(LocalDateTime oraInizio, LocalDateTime oraFine){
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
        for(Impianto impianto : this.getRegistroImpianti().getListaImpiantiPolisportiva()){
            if(!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(oraInizio, oraFine)){
                listaImpiantiDisponibili.add(impianto);
            }
        }
        return listaImpiantiDisponibili;
    }



        private RegistroUtentiPolisportiva getRegistroSportivi(){
            return this.registroSportivi;
        }

        private RegistroImpianti getRegistroImpianti(){
            return this.registroImpianti;
        }

        private RegistroPrenotazioni getRegistroPrenotazioni(){
            return this.registroPrenotazioni;
        }

        private Prenotazione getPrenotazioneInAtto(){
            return this.prenotazioneInAtto;
        }

        private void setPrenotazioneInAtto(Prenotazione prenotazione){
            this.prenotazioneInAtto = prenotazione;
        }
        
        private List<Sport> getSportPraticabili() {
            List<Sport> listaSportPraticabili = new ArrayList<Sport>();
            Set<Sport> setSportPraticabili = new HashSet<Sport>();
            for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
                for(ImpiantoSpecs specifica : impianto.getSpecificheImpianto()){
                    setSportPraticabili.add(specifica.getSportPraticabile());
                }
            }
            listaSportPraticabili.addAll(setSportPraticabili);
            return listaSportPraticabili;
    
        }
    }

