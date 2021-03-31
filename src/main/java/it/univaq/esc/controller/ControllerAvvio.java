package it.univaq.esc.controller;

import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.IPrenotabileDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerAvvio {


    @Autowired
    private EffettuaPrenotazioneHandler effettuaPrenotazioneHandler;

    @Autowired
    private RegistroSportivi registroSportivi;

    
    // @RequestMapping(value = "/test")
    // public ModelAndView avvio(){

    //     return new ModelAndView("newPrenotazione", this.getParametri());
    // }

    @RequestMapping(value = "/profilo")
    public ModelAndView getProfilo(){
    ModelAndView profiloSportivo = new ModelAndView("profiloSportivo", this.getDettagliProfiloSportivo("pippofranco@bagaglino.com"));
        // profiloSportivo.addObject("avvio", this.avvio());
        return profiloSportivo;
        
    }    
    

    private HashMap<String, Object> getOpzioniPrenotazioneImpianto(){
       // EffettuaPrenotazioneHandler controller = effettuaPrenotazioneHandler;
        HashMap<String, Object> opzioniPrenotazioneImpianto = new HashMap<String, Object>();
        opzioniPrenotazioneImpianto.put("sportPraticabili", this.effettuaPrenotazioneHandler.getSportPraticabili());
        HashMap<Integer, String> impiantiDisponibili = new HashMap<Integer, String>();
    
        for(Impianto impianto : this.effettuaPrenotazioneHandler.getImpiantiDisponibili(new Calendario())){
            impiantiDisponibili.put(impianto.getIdImpianto(), impianto.getTipoPavimentazione().toString());
        }
        opzioniPrenotazioneImpianto.put("impiantiDisponibili", impiantiDisponibili);
        opzioniPrenotazioneImpianto.put("sportiviIscrittiPolisportiva", this.effettuaPrenotazioneHandler.getSportivi());
        
        

        return opzioniPrenotazioneImpianto;
    }

    // private HashMap<String, Object> getParametri(){
        
    //     EffettuaPrenotazioneHandler controllerPrenotazioni = effettuaPrenotazioneHandler;

    //     String tipoPrenotazione = TipiPrenotazione.IMPIANTO.toString();
    //     controllerPrenotazioni.avviaNuovaPrenotazione(controllerPrenotazioni.getRegistroSportivi().getListaSportivi().get(0), tipoPrenotazione);
    //     Prenotazione prenotazioneAvviata = controllerPrenotazioni.getPrenotazioneInAtto();

    //     Calendario calendarioPrenotazione = new Calendario();
    //     calendarioPrenotazione.aggiungiAppuntamento(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));

    //     prenotazioneAvviata.setCalendario(calendarioPrenotazione);

    //     List<Sport> sportPrenotabili = controllerPrenotazioni.getSportPraticabili();
        
    //     Sport sportScelto = sportRepository.getOne("tennis");
    //     prenotazioneAvviata.getPrenotazioneSpecs().setSport(sportScelto);

    //     List<Impianto> impiantiDisponibili = controllerPrenotazioni.getImpiantiDisponibili(prenotazioneAvviata.getCalendarioPrenotazione());

    //     HashMap<String, Object> parametri = new HashMap<String, Object>();
    //     parametri.put("sportPraticabili", sportPrenotabili);
    //     parametri.put("sportSelezionato", prenotazioneAvviata.getPrenotazioneSpecs().getSportAssociato().getNome());
    //     parametri.put("impiantiDisponibili", impiantiDisponibili);

    //     return parametri;
    // }

    private HashMap<String, Object> getDettagliProfiloSportivo(String email){
        SportivoDTO sportivoDTO = new SportivoDTO();
        sportivoDTO.impostaValoriDTO(this.registroSportivi.getSportivoDaEmail(email));
        HashMap<String, Object> dettagliProfiloSportivo = new HashMap<String, Object>();
        dettagliProfiloSportivo.put("sportivo", sportivoDTO);
        List<PrenotazioneDTO> prenotazioniDTO = new ArrayList<PrenotazioneDTO>();
        for(Prenotazione prenotazione : this.effettuaPrenotazioneHandler.getPrenotazioniByEmailSportivo(email)){
            IPrenotabileDTO specifichePrenotazione = this.effettuaPrenotazioneHandler.getSpecifichePrenotazioneDTOByTipoPrenotazione(prenotazione.getPrenotazioneSpecs().getTipoSpecifica());
            specifichePrenotazione.impostaValoriSpecifichePrenotazioneDTO(prenotazione.getPrenotazioneSpecs().getValoriSpecifichePrenotazione());
            PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO(specifichePrenotazione);
            prenotazioneDTO.impostaValoriDTO(prenotazione);
            prenotazioniDTO.add(prenotazioneDTO);
        }
        dettagliProfiloSportivo.put("prenotazioniEffettuate", prenotazioniDTO);
        return dettagliProfiloSportivo;
    }

    

   
    @RequestMapping(value = "/nuovaPrenotazione")
    public ModelAndView avviaPrenotazione(@RequestParam(name="email") String emailSportivoPrenotante, @RequestParam(name="tipoPrenotazione") String tipoPrenotazione){
        this.effettuaPrenotazioneHandler.avviaNuovaPrenotazione(this.effettuaPrenotazioneHandler.getRegistroSportivi().getSportivoDaEmail(emailSportivoPrenotante), tipoPrenotazione);
        HashMap<String, Object> opzioniPrenotazione = this.getOpzioniPrenotazioneImpianto();
        opzioniPrenotazione.put("sportivoPrenotante", this.effettuaPrenotazioneHandler.getPrenotazioneInAtto().getSportivoPrenotante());     
        opzioniPrenotazione.put("formPrenotaImpianto", new FormPrenotaImpianto());
        ModelAndView opzioniPrenotazioneImpianto = new ModelAndView("prenotazioneImpianto", opzioniPrenotazione);
        return opzioniPrenotazioneImpianto;
    }

}
