package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroPrenotazioni;
import it.univaq.esc.model.RegistroSportivi;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.model.UtentePolisportivaAbstract;

@RestController
@RequestMapping("/effettuaPrenotazione")
public class EffettuaPrenotazioneHandlerRest {

    @Autowired
    private RegistroImpianti registroImpianti;

    @Autowired
    private RegistroSportivi registroSportivi;

    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    private Prenotazione prenotazioneInAtto;
    
    public EffettuaPrenotazioneHandlerRest(){}


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
            Appuntamento appuntamento = new Appuntamento();
            appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
            appuntamento.aggiungiPartecipante(sportivoPrenotante);
           
            Map<String, Object> mappaValori = new HashMap<String, Object>();
            mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
            mappaValori.put("impiantiDisponibili", this.getImpiantiDisponibili());
            mappaValori.put("sportiviPolisportiva", this.getSportiviPolisportiva());

            return mappaValori;
        }


        private RegistroSportivi getRegistroSportivi(){
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
    }

