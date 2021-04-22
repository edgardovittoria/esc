package it.univaq.esc.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import it.univaq.esc.model.TipiPrenotazione;
import it.univaq.esc.model.costi.ListinoPrezziDescrizioniPolisportiva;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.model.utenti.UtentePolisportivaBuilder;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;

import it.univaq.esc.repository.SportRepository;
import it.univaq.esc.repository.UtentePolisportivaAbstractRepository;
@Component
public class PopolaDB {

    @Autowired
    private UtentePolisportivaAbstractRepository utentePolisportivaRepository;

    // @Autowired
    // private CalendarioRepository calendarioRepository;

    @Autowired
    private ImpiantoRepository impiantoRepository;

    
    @Autowired
    private ListinoPrezziDescrizioniPolisportiva listinoPrezziDescrizioniPolisportiva;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private SportRepository sportRepo;

    
    public PopolaDB(){}

    public  void popola(){

        Sport calcetto = new Sport("calcetto", 10);
        Sport tennis = new Sport("tennis", 2);
        Sport pallavolo = new Sport("pallavolo", 12);

        sportRepo.save(calcetto);
        sportRepo.save(tennis);
        sportRepo.save(pallavolo);

        
        UtentePolisportivaAbstract sportivoPrenotante = new UtentePolisportivaBuilder().assegnaRuoloSportivo().build();
        Map<String, Object> mappaProprieta = new HashMap<String, Object>();
        mappaProprieta.put("nome", "Pippo");
        mappaProprieta.put("cognome", "Franco");
        mappaProprieta.put("email", "pippofranco@bagaglino.com");
        List<Sport> listaSportPraticati = new ArrayList<Sport>();
        listaSportPraticati.add(tennis);
        mappaProprieta.put("sportPraticati", listaSportPraticati);
        sportivoPrenotante.setProprieta(mappaProprieta);

        UtentePolisportivaAbstract sportivo1 = new UtentePolisportivaBuilder().assegnaRuoloSportivo().build();
        Map<String, Object> mappaProprieta1 = new HashMap<String, Object>();
        mappaProprieta1.put("nome", "Gianni");
        mappaProprieta1.put("cognome", "cognome");
        mappaProprieta1.put("email", "poppins@bianconiglio.com");
        List<Sport> listaSportPraticati1 = new ArrayList<Sport>();
        listaSportPraticati1.add(tennis);
        listaSportPraticati1.add(calcetto);
        listaSportPraticati1.add(pallavolo);
        mappaProprieta1.put("sportPraticati", listaSportPraticati1);
        sportivo1.setProprieta(mappaProprieta1);

        UtentePolisportivaAbstract sportivo2 = new UtentePolisportivaBuilder().assegnaRuoloSportivo().assegnaRuoloIstruttore(listaSportPraticati1).build();
        Map<String, Object> mappaProprieta2 = new HashMap<String, Object>();
        mappaProprieta2.put("nome", "mariangelo");
        mappaProprieta2.put("cognome", "sasso");
        mappaProprieta2.put("email", "marsasso@boh.com");
        List<Sport> listaSportPraticati2 = new ArrayList<Sport>();
        listaSportPraticati2.add(tennis);
        listaSportPraticati2.add(calcetto);
        mappaProprieta2.put("sportPraticati", listaSportPraticati2);
        sportivo2.setProprieta(mappaProprieta2);

        UtentePolisportivaAbstract sportivo3 = new UtentePolisportivaBuilder().assegnaRuoloSportivo().build();
        Map<String, Object> mappaProprieta3 = new HashMap<String, Object>();
        mappaProprieta3.put("nome", "tardigrado");
        mappaProprieta3.put("cognome", "acqua");
        mappaProprieta3.put("email", "matita@boh.com");
        List<Sport> listaSportPraticati3 = new ArrayList<Sport>();
        listaSportPraticati3.add(pallavolo);
        listaSportPraticati3.add(calcetto);
        mappaProprieta3.put("sportPraticati", listaSportPraticati3);
        sportivo3.setProprieta(mappaProprieta3);


        utentePolisportivaRepository.save(sportivoPrenotante);
        utentePolisportivaRepository.save(sportivo1);
        utentePolisportivaRepository.save(sportivo2);
        utentePolisportivaRepository.save(sportivo3);

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
       


        this.listinoPrezziDescrizioniPolisportiva
            .aggiunginuovaSpecificaDesc(TipiPrenotazione.IMPIANTO.toString(), tennis)
            .impostaPrezzoOrario(10)
            .impostaPrezzoPavimentazione(Pavimentazione.CEMENTO.toString(), 30)
            .impostaPrezzoPavimentazione(Pavimentazione.TERRA_BATTUTA.toString(), 50)
            .impostaPrezzoPavimentazione(Pavimentazione.SINTETICO.toString(), 20)
            .ritornaSpecificaCreata();

            this.listinoPrezziDescrizioniPolisportiva
            .aggiunginuovaSpecificaDesc(TipiPrenotazione.IMPIANTO.toString(), calcetto)
            .impostaPrezzoOrario(10)
            .impostaPrezzoPavimentazione(Pavimentazione.CEMENTO.toString(), 10)
            .impostaPrezzoPavimentazione(Pavimentazione.SINTETICO.toString(), 20)
            .ritornaSpecificaCreata();

            this.listinoPrezziDescrizioniPolisportiva
            .aggiunginuovaSpecificaDesc(TipiPrenotazione.IMPIANTO.toString(), pallavolo)
            .impostaPrezzoOrario(10)
            .impostaPrezzoPavimentazione(Pavimentazione.CEMENTO.toString(), 20)
            .impostaPrezzoPavimentazione(Pavimentazione.SINTETICO.toString(), 30)
            .ritornaSpecificaCreata();

            
            

            
        // Calendario calendarioImpianto2 = new Calendario();
        // Calendario calendarioImpianto3 = new Calendario();
        

        // List<Appuntamento> listaAppuntamenti1 = new ArrayList<Appuntamento>();
        // listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));
        // listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 25, 10, 30), LocalDateTime.of(2020, 5, 25, 11, 30)));
        // listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 27, 10, 30), LocalDateTime.of(2020, 5, 27, 11, 30)));
        // listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 4, 26, 10, 30), LocalDateTime.of(2020, 4, 26, 11, 30)));
        // listaAppuntamenti1.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 15, 30), LocalDateTime.of(2020, 5, 26, 17, 30)));

        // List<Appuntamento> listaAppuntamenti2 = new ArrayList<Appuntamento>();
        // listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 28, 10, 30), LocalDateTime.of(2020, 5, 28, 11, 30)));
        // listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 25, 10, 30), LocalDateTime.of(2020, 5, 25, 11, 30)));
        // listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 27, 10, 30), LocalDateTime.of(2020, 5, 27, 11, 30)));
        // listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 4, 26, 10, 30), LocalDateTime.of(2020, 4, 26, 11, 30)));
        // listaAppuntamenti2.add(new Appuntamento(LocalDateTime.of(2020, 5, 28, 15, 30), LocalDateTime.of(2020, 5, 28, 17, 30)));

        // List<Appuntamento> listaAppuntamenti3 = new ArrayList<Appuntamento>();
        // listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 10, 30), LocalDateTime.of(2020, 5, 26, 11, 30)));
        // listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 25, 10, 30), LocalDateTime.of(2020, 5, 25, 11, 30)));
        // listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 27, 10, 30), LocalDateTime.of(2020, 5, 27, 11, 30)));
        // listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 4, 26, 10, 30), LocalDateTime.of(2020, 4, 26, 11, 30)));
        // listaAppuntamenti3.add(new Appuntamento(LocalDateTime.of(2020, 5, 26, 15, 30), LocalDateTime.of(2020, 5, 26, 17, 30)));


         
       
        // appuntamentoRepository.saveAll(listaAppuntamenti1);
        // appuntamentoRepository.saveAll(listaAppuntamenti2);
        // appuntamentoRepository.saveAll(listaAppuntamenti3);
        

        // calendarioImpianto1.setListaAppuntamenti(listaAppuntamenti1);
        // calendarioImpianto2.setListaAppuntamenti(listaAppuntamenti2);
        // calendarioImpianto3.setListaAppuntamenti(listaAppuntamenti3);
        

        /*calendarioRepository.save(calendarioImpianto1);
        calendarioRepository.save(calendarioImpianto2);
        calendarioRepository.save(calendarioImpianto3);*/

        //prenotazione dell'impianto1 nella stessa data scelta dallo sportivo che sta effettuando la prenotazione
               
        PrenotazioneSpecs prenotazioneSpecs = new PrenotazioneImpiantoSpecs();
        Prenotazione prenotazione1 = new Prenotazione(0, prenotazioneSpecs);
        //prenotazioneSpecs.setPrenotazioneAssociata(prenotazione1);
        prenotazione1.getListaSpecifichePrenotazione().get(0).setPrenotazioneAssociata(prenotazione1);
        prenotazione1.setSportivoPrenotante(sportivo1);
        
        prenotazione1.getListaSpecifichePrenotazione().get(0).setSpecificaDescrtiption(
            this.listinoPrezziDescrizioniPolisportiva.getSpecificaDescByTipoPrenotazioneESport(TipiPrenotazione.IMPIANTO.toString(), tennis));
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("impianto", impianto1);
        prenotazione1.getListaSpecifichePrenotazione().get(0).impostaValoriSpecificheExtraPrenotazione(mappaValori);
        
        Appuntamento appuntamento1 = new Appuntamento(LocalDateTime.of(2021, 5, 26, 10, 30), LocalDateTime.of(2021, 5, 26, 11, 30), prenotazioneSpecs);
        appuntamento1.aggiungiPartecipante(sportivo1);
        appuntamento1.aggiungiPartecipante(sportivo2);
        Calendario calendarioSpecs1 = new Calendario();
        calendarioSpecs1.aggiungiAppuntamento(appuntamento1);
        //prenotazione1.setCalendarioSpecifica(calendarioSpecs1, prenotazioneSpecs);
        impianto1.setCalendarioAppuntamentiImpianto(calendarioSpecs1);
        //prenotazione1.impostaCalendarioPrenotazioneDaSpecifiche();
       

        //Prenotazione dell'impianto3 in una data diversa dalla data scelta dallo sportivo che sta effettuando la prenotazione;
        
        PrenotazioneSpecs prenotazioneSpecs2 = new PrenotazioneImpiantoSpecs();
        Prenotazione prenotazione2 = new Prenotazione(1, prenotazioneSpecs2);
        //prenotazioneSpecs2.setPrenotazioneAssociata(prenotazione2);
        prenotazione2.getListaSpecifichePrenotazione().get(0).setPrenotazioneAssociata(prenotazione2);
        prenotazione2.setSportivoPrenotante(sportivo2);
        
        
        Appuntamento appuntamento2 = new Appuntamento(LocalDateTime.of(2021, 5, 21, 17, 00), LocalDateTime.of(2021, 5, 21, 19,00), prenotazioneSpecs2);
        appuntamento2.aggiungiPartecipante(sportivo2);
        appuntamento2.aggiungiPartecipante(sportivo3);
        Calendario calendarioSpecs2 = new Calendario();
        calendarioSpecs2.aggiungiAppuntamento(appuntamento2);
        
        impianto3.setCalendarioAppuntamentiImpianto(calendarioSpecs2);
        // prenotazione2.setCalendarioSpecifica(calendarioSpecs2, prenotazioneSpecs2);
        // prenotazione2.impostaCalendarioPrenotazioneDaSpecifiche();
        
        prenotazione2.getListaSpecifichePrenotazione().get(0).setSpecificaDescrtiption(
            this.listinoPrezziDescrizioniPolisportiva.getSpecificaDescByTipoPrenotazioneESport(TipiPrenotazione.IMPIANTO.toString(), tennis));
        Map<String, Object> mappaValori2 = new HashMap<String, Object>();
        mappaValori2.put("impianto", impianto3);
        prenotazione2.getListaSpecifichePrenotazione().get(0).impostaValoriSpecificheExtraPrenotazione(mappaValori);
        
        
        /*calendarioRepository.save(calendarioPrenotazione);
        calendarioRepository.save(calendarioPrenotazione2);*/
        
        // prenotazioneRepository.save(prenotazione1);
        // prenotazioneRepository.save(prenotazione2);
        appuntamentoRepository.saveAll(calendarioSpecs1.getListaAppuntamenti());
        appuntamentoRepository.saveAll(calendarioSpecs2.getListaAppuntamenti());

        
        
    }


}