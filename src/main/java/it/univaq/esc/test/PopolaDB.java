package it.univaq.esc.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.PrenotazioneRepository;
import it.univaq.esc.repository.SportivoRepository;
@Component
public class PopolaDB {

    @Autowired
    private SportivoRepository sportivoRepository;

    // @Autowired
    // private CalendarioRepository calendarioRepository;

    @Autowired
    private ImpiantoRepository impiantoRepository;

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    
    public PopolaDB(){}

    public  void popola(){
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

        sportivoRepository.save(sportivoPrenotante);
        sportivoRepository.save(sportivo1);
        sportivoRepository.save(sportivo2);
        sportivoRepository.save(sportivo3);

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

        ImpiantoSpecs specificaImpianto1 = new ImpiantoSpecs(Pavimentazione.SINTETICO, calcetto);
        ImpiantoSpecs specificaImpianto12 = new ImpiantoSpecs(Pavimentazione.SINTETICO, tennis);
        ImpiantoSpecs specificaImpianto13 = new ImpiantoSpecs(Pavimentazione.SINTETICO, pallavolo);
        ImpiantoSpecs specificaImpianto2 = new ImpiantoSpecs(Pavimentazione.TERRA_BATTUTA, tennis);
        ImpiantoSpecs specificaImpianto3 = new ImpiantoSpecs(Pavimentazione.CEMENTO, pallavolo);
        ImpiantoSpecs specificaImpianto32 = new ImpiantoSpecs(Pavimentazione.CEMENTO, tennis);

        // impiantoSpecsRepository.save(specificaImpianto1);
        // impiantoSpecsRepository.save(specificaImpianto2);
        // impiantoSpecsRepository.save(specificaImpianto3);


        List<ImpiantoSpecs> specificheImpianto1 = new ArrayList<ImpiantoSpecs>();
        specificheImpianto1.add(specificaImpianto1);
        specificheImpianto1.add(specificaImpianto12);
        specificheImpianto1.add(specificaImpianto13);

        List<ImpiantoSpecs> specificheImpianto2 = new ArrayList<ImpiantoSpecs>();
        specificheImpianto2.add(specificaImpianto2);

        List<ImpiantoSpecs> specificheImpianto3 = new ArrayList<ImpiantoSpecs>();
        specificheImpianto3.add(specificaImpianto3);
        specificheImpianto3.add(specificaImpianto32);

        Impianto impianto1 = new Impianto(1, specificheImpianto1);
        Impianto impianto2 = new Impianto(2, specificheImpianto2);
        Impianto impianto3 = new Impianto(3, specificheImpianto3);
        
        impiantoRepository.save(impianto1);
        impiantoRepository.save(impianto2);
        impiantoRepository.save(impianto3);
       


        Calendario calendarioImpianto1 = new Calendario();
        Calendario calendarioImpianto2 = new Calendario();
        Calendario calendarioImpianto3 = new Calendario();
        

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

        List<Appuntamento> listaAppuntamenti3 = new ArrayList<Appuntamento>();
        listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));
        listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 25, 10, 30), LocalDateTime.of(2020, 5, 25, 11, 30)));
        listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 27, 10, 30), LocalDateTime.of(2020, 5, 27, 11, 30)));
        listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 4, 26, 10, 30), LocalDateTime.of(2020, 4, 26, 11, 30)));
        listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 15, 30), LocalDateTime.of(2020, 5, 26, 17, 30)));


         
       
        // appuntamentoRepository.saveAll(listaAppuntamenti1);
        // appuntamentoRepository.saveAll(listaAppuntamenti2);
        // appuntamentoRepository.saveAll(listaAppuntamenti3);
        

        calendarioImpianto1.setListaAppuntamenti(listaAppuntamenti1);
        calendarioImpianto2.setListaAppuntamenti(listaAppuntamenti2);
        calendarioImpianto3.setListaAppuntamenti(listaAppuntamenti3);
        

        /*calendarioRepository.save(calendarioImpianto1);
        calendarioRepository.save(calendarioImpianto2);
        calendarioRepository.save(calendarioImpianto3);*/

        //prenotazione dell'impianto1 nella stessa data scelta dallo sportivo che sta effettuando la prenotazione
        Calendario calendarioPrenotazione = new Calendario();
        
        PrenotazioneSpecs prenotazioneSpecs = new PrenotazioneImpiantoSpecs();
        Prenotazione prenotazione1 = new Prenotazione(0, prenotazioneSpecs);
        prenotazione1.getPrenotazioneSpecs().setSportivoPrenotante(sportivo1);
        prenotazione1.getPrenotazioneSpecs().aggiungiPartecipante(sportivo1);
        prenotazione1.getPrenotazioneSpecs().aggiungiImpiantoPrenotato(impianto1);
        prenotazione1.setCalendario(calendarioPrenotazione);
        prenotazione1.getCalendarioPrenotazione().aggiungiAppuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30), prenotazione1, impianto1);
        prenotazione1.setConfermata();
        prenotazione1.getPrenotazioneSpecs().setSport(tennis);
        prenotazione1.getPrenotazioneSpecs().aggiungiPartecipante(sportivo2);
        prenotazione1.getPrenotazioneSpecs().setSportivoPrenotante(sportivo2);

        //Prenotazione dell'impianto3 in una data diversa dalla data scelta dallo sportivo che sta effettuando la prenotazione;
        Calendario calendarioPrenotazione2 = new Calendario();
        PrenotazioneSpecs prenotazioneSpecs2 = new PrenotazioneImpiantoSpecs();
        Prenotazione prenotazione2 = new Prenotazione(1, prenotazioneSpecs2);
        prenotazione2.getPrenotazioneSpecs().setSportivoPrenotante(sportivo2);
        prenotazione2.getPrenotazioneSpecs().aggiungiPartecipante(sportivo2);
        prenotazione2.setCalendario(calendarioPrenotazione2);
        prenotazione2.getCalendarioPrenotazione().aggiungiAppuntamento(LocalDateTime.of(2020, 5, 21, 17, 0), LocalDateTime.of(2020, 5, 21, 19,0), prenotazione2, impianto3);
        prenotazione2.getPrenotazioneSpecs().aggiungiImpiantoPrenotato(impianto3);
        prenotazione2.setConfermata();
        prenotazione2.getPrenotazioneSpecs().setSport(tennis);
        prenotazione2.getPrenotazioneSpecs().aggiungiPartecipante(sportivo3);
        prenotazione2.getPrenotazioneSpecs().setSportivoPrenotante(sportivo3);

        /*calendarioRepository.save(calendarioPrenotazione);
        calendarioRepository.save(calendarioPrenotazione2);*/
        prenotazioneRepository.save(prenotazione1);
        prenotazioneRepository.save(prenotazione2);
        appuntamentoRepository.saveAll(prenotazione1.getCalendarioPrenotazione().getListaAppuntamenti());
        appuntamentoRepository.saveAll(prenotazione2.getCalendarioPrenotazione().getListaAppuntamenti());
        
        
    }


}