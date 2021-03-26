package it.univaq.esc.controller;

import it.univaq.esc.model.*;

import it.univaq.esc.repository.SportRepository;
import java.time.LocalDateTime;
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
    private SportRepository sportRepository;

    @Autowired
    private EffettuaPrenotazioneHandler effettuaPrenotazioneHandler;

    
    @RequestMapping(value = "/test")
    public ModelAndView avvio(){

        return new ModelAndView("newPrenotazione", this.getParametri());
    }

    @RequestMapping(value = "/profilo")
    public ModelAndView getProfilo(){
    ModelAndView profiloSportivo = new ModelAndView("profiloSportivo", this.getDettagliProfiloSportivo());
        profiloSportivo.addObject("avvio", this.avvio());
        return profiloSportivo;
        
    }

    
    

    private HashMap<String, Object> getOpzioniPrenotazioneImpianto(EffettuaPrenotazioneHandler controller){
       // EffettuaPrenotazioneHandler controller = effettuaPrenotazioneHandler;
        HashMap<String, Object> opzioniPrenotazioneImpianto = new HashMap<String, Object>();
        opzioniPrenotazioneImpianto.put("sportPraticabili", controller.getSportPraticabili());
        HashMap<Integer, String> impiantiDisponibili = new HashMap<Integer, String>();
    
        for(Impianto impianto : controller.getImpiantiDisponibili(new Calendario())){
            impiantiDisponibili.put(impianto.getIdImpianto(), impianto.getTipoPavimentazione().toString());
        }
        opzioniPrenotazioneImpianto.put("impiantiDisponibili", impiantiDisponibili);
        opzioniPrenotazioneImpianto.put("sportiviIscrittiPolisportiva", controller.getSportivi());
        
        

        return opzioniPrenotazioneImpianto;
    }

    private HashMap<String, Object> getParametri(){
        
        EffettuaPrenotazioneHandler controllerPrenotazioni = effettuaPrenotazioneHandler;

        String tipoPrenotazione = TipiPrenotazione.IMPIANTO.toString();
        controllerPrenotazioni.avviaNuovaPrenotazione(controllerPrenotazioni.getRegistroSportivi().getListaSportivi().get(0), tipoPrenotazione);
        Prenotazione prenotazioneAvviata = controllerPrenotazioni.getPrenotazioneInAtto();

        Calendario calendarioPrenotazione = new Calendario();
        calendarioPrenotazione.aggiungiAppuntamento(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));

        prenotazioneAvviata.setCalendario(calendarioPrenotazione);

        List<Sport> sportPrenotabili = controllerPrenotazioni.getSportPraticabili();
        
        Sport sportScelto = sportRepository.getOne("tennis");
        prenotazioneAvviata.getPrenotazioneSpecs().setSport(sportScelto);

        List<Impianto> impiantiDisponibili = controllerPrenotazioni.getImpiantiDisponibili(prenotazioneAvviata.getCalendarioPrenotazione());

        HashMap<String, Object> parametri = new HashMap<String, Object>();
        parametri.put("sportPraticabili", sportPrenotabili);
        parametri.put("sportSelezionato", prenotazioneAvviata.getPrenotazioneSpecs().getSportAssociato().getNome());
        parametri.put("impiantiDisponibili", impiantiDisponibili);

        return parametri;
    }

    private HashMap<String, Object> getDettagliProfiloSportivo(){
        Sportivo sportivo = new Sportivo("Pippo", "Franco", "pippofranco@bagaglino.com");
        HashMap<String, Object> dettagliProfiloSportivo = new HashMap<String, Object>();
        dettagliProfiloSportivo.put("nome", sportivo.getNome());
        dettagliProfiloSportivo.put("cognome", sportivo.getCognome());
        dettagliProfiloSportivo.put("email", sportivo.getEmail());
        return dettagliProfiloSportivo;
    }

    

   
    @RequestMapping(value = "/nuovaPrenotazione")
    public ModelAndView avviaPrenotazione(@RequestParam(name="email") String emailSportivoPrenotante, @RequestParam(name="tipoPrenotazione") String tipoPrenotazione){
        EffettuaPrenotazioneHandler controllerNuovaPrenotazione = effettuaPrenotazioneHandler;
        controllerNuovaPrenotazione.avviaNuovaPrenotazione(controllerNuovaPrenotazione.getRegistroSportivi().getSportivoDaEmail(emailSportivoPrenotante), tipoPrenotazione);
        HashMap<String, Object> opzioniPrenotazione = this.getOpzioniPrenotazioneImpianto(controllerNuovaPrenotazione);
        opzioniPrenotazione.put("sportivoPrenotante", controllerNuovaPrenotazione.getPrenotazioneInAtto().getPrenotazioneSpecs().getSportivoPrenotante());        
        ModelAndView opzioniPrenotazioneImpianto = new ModelAndView("prenotazioneImpianto", opzioniPrenotazione);
        return opzioniPrenotazioneImpianto;
    }

}
