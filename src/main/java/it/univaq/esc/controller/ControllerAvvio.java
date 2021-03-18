package it.univaq.esc.controller;

import it.univaq.esc.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerAvvio {
    
    @RequestMapping(value = "/test")
    public ModelAndView avvio(){

        return new ModelAndView("newPrenotazione", this.getParametri());
    }

    private HashMap<String, Object> getParametri(){
        EffettuaPrenotazioneHandler controller = new EffettuaPrenotazioneHandler();

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

        ImpiantoSpecs specificheImpianto1 = new ImpiantoSpecs(true, 10, Pavimentazione.SINTETICO, sportPraticatiImpianto1);
        ImpiantoSpecs specificheImpianto2 = new ImpiantoSpecs(true, 10, Pavimentazione.TERRA_BATTUTA, sportPraticatiImpianto2);
        ImpiantoSpecs specificheImpianto3 = new ImpiantoSpecs(false, 20, Pavimentazione.CEMENTO, sportPraticatiImpianto3);

        Impianto impianto1 = new Impianto(1, specificheImpianto1);
        Impianto impianto2 = new Impianto(2, specificheImpianto2);
        Impianto impianto3 = new Impianto(3, specificheImpianto3);
        Impianto impianto4 = new Impianto(4, specificheImpianto3);
        Impianto impianto5 = new Impianto(5, specificheImpianto1);

        controller.getRegistroImpianti().aggiungiImpianto(impianto1);
        controller.getRegistroImpianti().aggiungiImpianto(impianto2);
        controller.getRegistroImpianti().aggiungiImpianto(impianto3);
        controller.getRegistroImpianti().aggiungiImpianto(impianto4);
        controller.getRegistroImpianti().aggiungiImpianto(impianto5);

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




        

        TipiPrenotazione tipoPrenotazione = TipiPrenotazione.IMPIANTO;
        controller.avviaNuovaPrenotazione(sportivoPrenotante, tipoPrenotazione);
        Prenotazione prenotazioneAvviata = controller.getPrenotazioneInAtto();

        Calendario calendarioPrenotazione = new Calendario();
        calendarioPrenotazione.aggiungiAppuntamento(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));

        prenotazioneAvviata.getPrenotazioneSpecs().setCalendario(calendarioPrenotazione);

        List<Sport> sportPrenotabili = controller.getSportPraticabili();
        
        
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
        controller.getRegistroPrenotazioni().aggiungiPrenotazione(prenotazione1);
        controller.getRegistroPrenotazioni().aggiungiPrenotazione(prenotazione2);

        List<Impianto> impiantiDisponibili = controller.getImpiantiDisponibili(prenotazioneAvviata.getPrenotazioneSpecs().getCalendarioPrenotazione());

        

        HashMap<String, Object> parametri = new HashMap<String, Object>();
        parametri.put("sportPraticabili", sportPrenotabili);
        parametri.put("sportSelezionato", prenotazioneAvviata.getPrenotazioneSpecs().getSportAssociato().getNome());
        parametri.put("impiantiDisponibili", impiantiDisponibili);

        return parametri;
    }
}
