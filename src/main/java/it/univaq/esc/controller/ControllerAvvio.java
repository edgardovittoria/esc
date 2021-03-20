package it.univaq.esc.controller;

import it.univaq.esc.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerAvvio {

    private EffettuaPrenotazioneHandler controllerPrenotazioni = new EffettuaPrenotazioneHandler();
    private RegistroImpianti registroImpianti = RegistroImpianti.getInstance();
    private RegistroPrenotazioni registroPrenotazioni = RegistroPrenotazioni.getInstance();
    private RegistroSportivi registroSportivi = RegistroSportivi.getInstance();
    
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

    
    

    private HashMap<String, Object> getOpzioniPrenotazioneImpianto(){
        HashMap<String, Object> opzioniPrenotazioneImpianto = new HashMap<String, Object>();
        opzioniPrenotazioneImpianto.put("sportPraticabili", getControllerPrenotazioni().getSportPraticabili());
        HashMap<Integer, String> impiantiDisponibili = new HashMap<Integer, String>();
        for(Impianto impianto : getControllerPrenotazioni().getImpiantiDisponibili(new Calendario())){
            impiantiDisponibili.put(impianto.getIdImpianto(), impianto.getTipoPavimentazione().toString());
        }
        opzioniPrenotazioneImpianto.put("impiantiDisponibili", impiantiDisponibili);
        opzioniPrenotazioneImpianto.put("sportiviIscrittiPolisportiva", getControllerPrenotazioni().getSportivi());
        
        

        return opzioniPrenotazioneImpianto;
    }

    private HashMap<String, Object> getParametri(){
        
        

        Sportivo sportivoPrenotante = new Sportivo("Pippo", "Franco", "pippofranco@bagaglino.com");
        Sportivo sportivo1 = new Sportivo("Gianni", "cognome", "poppins@bianconiglio.com");
        Sportivo sportivo2 = new Sportivo("mariangelo", "sasso", "marsasso@boh.com");
        Sportivo sportivo3 = new Sportivo("tardigrado", "acqua", "matita@boh.com");

        Sport calcetto = new Sport("calcetto", 10);
        Sport tennis = new Sport("tennis", 2);
        Sport pallavolo = new Sport("pallavolo", 12);


        sportivoPrenotante.aggiungiSporPraticatoDalloSportivo(tennis);
        sportivo1.aggiungiSporPraticatoDalloSportivo(calcetto);
        sportivo1.aggiungiSporPraticatoDalloSportivo(tennis);
        sportivo1.aggiungiSporPraticatoDalloSportivo(pallavolo);
        sportivo2.aggiungiSporPraticatoDalloSportivo(calcetto);
        sportivo2.aggiungiSporPraticatoDalloSportivo(tennis);
        sportivo3.aggiungiSporPraticatoDalloSportivo(calcetto);
        sportivo3.aggiungiSporPraticatoDalloSportivo(pallavolo);

        registroSportivi.registraSportivo(sportivo1);
        registroSportivi.registraSportivo(sportivo2);
        registroSportivi.registraSportivo(sportivo3);

        List<Sport> sportPraticatiImpianto1 = new ArrayList<Sport>();
        sportPraticatiImpianto1.add(calcetto);
        sportPraticatiImpianto1.add(tennis);

        List<Sport> sportPraticatiImpianto2 = new ArrayList<Sport>();
        sportPraticatiImpianto2.add(calcetto);
        sportPraticatiImpianto2.add(pallavolo);

        List<Sport> sportPraticatiImpianto3 = new ArrayList<Sport>();
        sportPraticatiImpianto3.add(calcetto);
        sportPraticatiImpianto3.add(pallavolo);
        sportPraticatiImpianto3.add(tennis);

        ImpiantoSpecs specificaImpianto1 = new ImpiantoSpecs(Pavimentazione.SINTETICO, sportPraticatiImpianto1.get(0));
        ImpiantoSpecs specificaImpianto2 = new ImpiantoSpecs(Pavimentazione.TERRA_BATTUTA, sportPraticatiImpianto2.get(1));
        ImpiantoSpecs specificaImpianto3 = new ImpiantoSpecs(Pavimentazione.CEMENTO, sportPraticatiImpianto3.get(2));

        List<ImpiantoSpecs> specificheImpianto1 = new ArrayList<ImpiantoSpecs>();
        specificheImpianto1.add(specificaImpianto1);
        specificheImpianto1.add(specificaImpianto2);
        specificheImpianto1.add(specificaImpianto3);

        List<ImpiantoSpecs> specificheImpianto2 = new ArrayList<ImpiantoSpecs>();
        specificheImpianto2.add(specificaImpianto1);
        specificheImpianto2.add(specificaImpianto3);

        List<ImpiantoSpecs> specificheImpianto3 = new ArrayList<ImpiantoSpecs>();
        specificheImpianto3.add(specificaImpianto1);
        specificheImpianto3.add(specificaImpianto2);

        Impianto impianto1 = new Impianto(1, specificheImpianto1);
        Impianto impianto2 = new Impianto(2, specificheImpianto2);
        Impianto impianto3 = new Impianto(3, specificheImpianto3);
        Impianto impianto4 = new Impianto(4, specificheImpianto3);
        Impianto impianto5 = new Impianto(5, specificheImpianto1);

        this.registroImpianti.aggiungiImpianto(impianto1);
        this.registroImpianti.aggiungiImpianto(impianto2);
        this.registroImpianti.aggiungiImpianto(impianto3);
        this.registroImpianti.aggiungiImpianto(impianto4);
        this.registroImpianti.aggiungiImpianto(impianto5);

        controllerPrenotazioni.getRegistroImpianti().aggiungiImpianto(impianto1);
        controllerPrenotazioni.getRegistroImpianti().aggiungiImpianto(impianto2);
        controllerPrenotazioni.getRegistroImpianti().aggiungiImpianto(impianto3);
        controllerPrenotazioni.getRegistroImpianti().aggiungiImpianto(impianto4);
        controllerPrenotazioni.getRegistroImpianti().aggiungiImpianto(impianto5);

        Calendario calendarioImpianto1 = new Calendario();
        Calendario calendarioImpianto2 = new Calendario();
        Calendario calendarioImpianto3 = new Calendario();
        Calendario calendarioImpianto4 = new Calendario();
        Calendario calendarioImpianto5 = new Calendario();

        List<Appuntamento> listaAppuntamenti1 = new ArrayList<Appuntamento>();
        listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));
        listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 25, 10, 30), LocalDateTime.of(2020, 5, 25, 11, 30)));
        listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 27, 10, 30), LocalDateTime.of(2020, 5, 27, 11, 30)));
        listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 4, 26, 10, 30), LocalDateTime.of(2020, 4, 26, 11, 30)));
        listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 15, 30), LocalDateTime.of(2020, 5, 26, 17, 30)));

        List<Appuntamento> listaAppuntamenti2 = new ArrayList<Appuntamento>();
        listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 28, 10, 30), LocalDateTime.of(2020, 5, 28, 11, 30)));
        listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 25, 10, 30), LocalDateTime.of(2020, 5, 25, 11, 30)));
        listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 27, 10, 30), LocalDateTime.of(2020, 5, 27, 11, 30)));
        listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 4, 26, 10, 30), LocalDateTime.of(2020, 4, 26, 11, 30)));
        listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 28, 15, 30), LocalDateTime.of(2020, 5, 28, 17, 30)));

        

      

        calendarioImpianto1.setListaAppuntamenti(listaAppuntamenti1);
        calendarioImpianto2.setListaAppuntamenti(listaAppuntamenti1);
        calendarioImpianto3.setListaAppuntamenti(listaAppuntamenti2);
        calendarioImpianto4.setListaAppuntamenti(listaAppuntamenti2);
        calendarioImpianto5.setListaAppuntamenti(listaAppuntamenti1);




        

        String tipoPrenotazione = TipiPrenotazione.IMPIANTO.toString();
        controllerPrenotazioni.avviaNuovaPrenotazione(sportivoPrenotante, tipoPrenotazione);
        Prenotazione prenotazioneAvviata = controllerPrenotazioni.getPrenotazioneInAtto();

        Calendario calendarioPrenotazione = new Calendario();
        calendarioPrenotazione.aggiungiAppuntamento(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));

        prenotazioneAvviata.getPrenotazioneSpecs().setCalendario(calendarioPrenotazione);

        List<Sport> sportPrenotabili = controllerPrenotazioni.getSportPraticabili();
        
        
        Sport sportScelto = tennis;
        prenotazioneAvviata.getPrenotazioneSpecs().setSport(sportScelto);

        //prenotazione dell'impianto1 nella stessa data scelta dallo sportivo che sta effettuando la prenotazione
        PrenotazioneSpecs prenotazioneSpecs = new PrenotazioneImpiantoSpecs();
        Prenotazione prenotazione1 = new Prenotazione(0, sportivo1, prenotazioneSpecs);
        prenotazione1.getPrenotazioneSpecs().aggiungiPartecipante(sportivo1);
        prenotazione1.getPrenotazioneSpecs().setCalendario(calendarioPrenotazione);
        prenotazione1.getPrenotazioneSpecs().aggiungiImpiantoPrenotato(impianto1);
        prenotazione1.setConfermata();
        prenotazione1.getPrenotazioneSpecs().setSport(tennis);
        prenotazione1.getPrenotazioneSpecs().aggiungiPartecipante(sportivo2);

        //Prenotazione dell'impianto3 in una data diversa dalla data scelta dallo sportivo che sta effettuando la prenotazione;
        Calendario calendarioPrenotazione2 = new Calendario();
        calendarioPrenotazione2.aggiungiAppuntamento(new Appuntamento(LocalDateTime.of(2020, 5, 21, 17, 0), LocalDateTime.of(2020, 5, 21, 19,0)));
        PrenotazioneSpecs prenotazioneSpecs2 = new PrenotazioneImpiantoSpecs();
        Prenotazione prenotazione2 = new Prenotazione(1, sportivo2, prenotazioneSpecs2);
        prenotazione2.getPrenotazioneSpecs().aggiungiPartecipante(sportivo2);
        prenotazione2.getPrenotazioneSpecs().setCalendario(calendarioPrenotazione2);
        prenotazione2.getPrenotazioneSpecs().aggiungiImpiantoPrenotato(impianto3);
        prenotazione2.setConfermata();
        prenotazione2.getPrenotazioneSpecs().setSport(tennis);
        prenotazione2.getPrenotazioneSpecs().aggiungiPartecipante(sportivo3);

        //aggiunta delle prenotazione nel registro delle prenotazioni
        controllerPrenotazioni.getRegistroPrenotazioni().aggiungiPrenotazione(prenotazione1);
        controllerPrenotazioni.getRegistroPrenotazioni().aggiungiPrenotazione(prenotazione2);

        List<Impianto> impiantiDisponibili = controllerPrenotazioni.getImpiantiDisponibili(prenotazioneAvviata.getPrenotazioneSpecs().getCalendarioPrenotazione());

        

        HashMap<String, Object> parametri = new HashMap<String, Object>();
        parametri.put("sportPraticabili", sportPrenotabili);
        parametri.put("sportSelezionato", prenotazioneAvviata.getPrenotazioneSpecs().getSportAssociato().getNome());
        parametri.put("impiantiDisponibili", impiantiDisponibili);

        return parametri;
    }

    private HashMap<String, Object> getDettagliProfiloSportivo(){
        Sportivo sportivo = new Sportivo("Pippo", "Franco", "pippofranco@gmail.com");
        HashMap<String, Object> dettagliProfiloSportivo = new HashMap<String, Object>();
        dettagliProfiloSportivo.put("nome", sportivo.getNome());
        dettagliProfiloSportivo.put("cognome", sportivo.getCognome());
        dettagliProfiloSportivo.put("email", sportivo.getEmail());
        return dettagliProfiloSportivo;
    }

    

    /**
     * @return EffettuaPrenotazioneHandler return the controllerPrenotazioni
     */
    public EffettuaPrenotazioneHandler getControllerPrenotazioni() {
        return controllerPrenotazioni;
    }

    /**
     * @param controllerPrenotazioni the controllerPrenotazioni to set
     */
    public void setControllerPrenotazioni(EffettuaPrenotazioneHandler controllerPrenotazioni) {

        this.controllerPrenotazioni = controllerPrenotazioni;
    }

    @RequestMapping(value = "/nuovaPrenotazione")
    public ModelAndView avviaPrenotazione(@RequestParam(name="email") String emailSportivoPrenotante, @RequestParam(name="tipoPrenotazione") String tipoPrenotazione){
        EffettuaPrenotazioneHandler controllerNuovaPrenotazione = new EffettuaPrenotazioneHandler();
        controllerNuovaPrenotazione.avviaNuovaPrenotazione(this.registroSportivi.getSportivoDaEmail(emailSportivoPrenotante), tipoPrenotazione);
        ModelAndView opzioniPrenotazioneImpianto = new ModelAndView("prenotazioneImpianto", this.getOpzioniPrenotazioneImpianto());
        return opzioniPrenotazioneImpianto;
    }

}
