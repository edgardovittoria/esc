package it.univaq.esc.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.FactoryAppuntamenti;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneCorsoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneLezioneSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.model.utenti.UtentePolisportivaBuilder;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.PrenotazioneRepository;
import it.univaq.esc.repository.SportRepository;
import it.univaq.esc.repository.SquadraRepository;
import it.univaq.esc.repository.UtentePolisportivaAbstractRepository;
import javassist.expr.NewArray;
import lombok.Getter;
import lombok.Setter;
@Component
@Getter @Setter
public class PopolaDB {

    @Autowired
    private UtentePolisportivaAbstractRepository utentePolisportivaRepository;

    // @Autowired
    // private CalendarioRepository calendarioRepository;

    @Autowired
    private ImpiantoRepository impiantoRepository;
    

    
    @Autowired
    private CatalogoPrenotabili listinoPrezziDescrizioniPolisportiva;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private SportRepository sportRepo;
    
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    
    @Autowired
    private FactoryAppuntamenti factoryAppuntamenti;
    
    @Autowired
    private SquadraRepository squadraRepository;

    
    public PopolaDB(){}

    public  void popola(){

        Sport calcetto = new Sport("calcetto", 10, 5);
        Sport tennis = new Sport("tennis", 2, 2);
        Sport pallavolo = new Sport("pallavolo", 12, 6);

        sportRepo.save(calcetto);
        sportRepo.save(tennis);
        sportRepo.save(pallavolo);

        
        UtentePolisportivaAbstract sportivoPrenotante = new UtentePolisportivaBuilder().assegnaRuoloSportivo().build();
        Map<String, Object> mappaProprieta = new HashMap<String, Object>();
        mappaProprieta.put("nome", "Pippo");
        mappaProprieta.put("cognome", "Franco");
        mappaProprieta.put("email", "pippofranco@bagaglino.com");
        mappaProprieta.put("password", "pippo");
        List<Sport> listaSportPraticati = new ArrayList<Sport>();
        listaSportPraticati.add(tennis);
        mappaProprieta.put("sportPraticati", listaSportPraticati);
        sportivoPrenotante.setProprieta(mappaProprieta);

        UtentePolisportivaAbstract sportivo1 = new UtentePolisportivaBuilder().assegnaRuoloSportivo().build();
        Map<String, Object> mappaProprieta1 = new HashMap<String, Object>();
        mappaProprieta1.put("nome", "Gianni");
        mappaProprieta1.put("cognome", "cognome");
        mappaProprieta1.put("email", "poppins@bianconiglio.com");
        mappaProprieta1.put("password", "poppins");
        List<Sport> listaSportPraticati1 = new ArrayList<Sport>();
        listaSportPraticati1.add(tennis);
        listaSportPraticati1.add(calcetto);
        listaSportPraticati1.add(pallavolo);
        mappaProprieta1.put("sportPraticati", listaSportPraticati1);
        sportivo1.setProprieta(mappaProprieta1);

        UtentePolisportivaAbstract sportivo2 = new UtentePolisportivaBuilder().assegnaRuoloSportivo().assegnaRuoloIstruttore(listaSportPraticati1).assegnaRuoloManutentore().build();
        Map<String, Object> mappaProprieta2 = new HashMap<String, Object>();
        mappaProprieta2.put("nome", "mariangelo");
        mappaProprieta2.put("cognome", "sasso");
        mappaProprieta2.put("email", "marsasso@boh.com");
        mappaProprieta2.put("password", "sasso");
        List<Sport> listaSportPraticati2 = new ArrayList<Sport>();
        listaSportPraticati2.add(tennis);
        listaSportPraticati2.add(calcetto);
        mappaProprieta2.put("sportPraticati", listaSportPraticati2);
        mappaProprieta2.put("calendarioManutentore", new Calendario()); 
        sportivo2.setProprieta(mappaProprieta2);

        UtentePolisportivaAbstract sportivo3 = new UtentePolisportivaBuilder().assegnaRuoloSportivo().assegnaRuoloDirettorePolisportiva().build();
        Map<String, Object> mappaProprieta3 = new HashMap<String, Object>();
        mappaProprieta3.put("nome", "tardigrado");
        mappaProprieta3.put("cognome", "acqua");
        mappaProprieta3.put("email", "matita@boh.com");
        mappaProprieta3.put("password", "matita");
        List<Sport> listaSportPraticati3 = new ArrayList<Sport>();
        listaSportPraticati3.add(pallavolo);
        listaSportPraticati3.add(calcetto);
        mappaProprieta3.put("sportPraticati", listaSportPraticati3);
        sportivo3.setProprieta(mappaProprieta3);


        utentePolisportivaRepository.save(sportivoPrenotante);
        utentePolisportivaRepository.save(sportivo1);
        utentePolisportivaRepository.save(sportivo2);
        utentePolisportivaRepository.save(sportivo3);
        
        
        
        Squadra squadra1 = new Squadra();
        squadra1.setNome("I Dugonghi");
        squadra1.setSport(calcetto);
        squadra1.aggiungiAmministratore(sportivo3);
        squadra1.aggiungiMembro(sportivo1);
        squadra1.aggiungiMembro(sportivo2);
        

        Squadra squadra2 = new Squadra();
        squadra2.setNome("Le Nutrie");
        squadra2.setSport(pallavolo);
        squadra2.aggiungiAmministratore(sportivoPrenotante);
        squadra2.aggiungiMembro(sportivo1);
        squadra2.aggiungiMembro(sportivo3);
        
        getSquadraRepository().save(squadra1);
        getSquadraRepository().save(squadra2);
        
        

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

        Impianto impianto1 = new Impianto(specificheImpianto1);
        Impianto impianto2 = new Impianto(specificheImpianto2);
        Impianto impianto3 = new Impianto(specificheImpianto3);
        
        impiantoRepository.save(impianto1);
        impiantoRepository.save(impianto2);
        impiantoRepository.save(impianto3);
       


        this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.IMPIANTO.toString(), tennis.getNumeroGiocatoriPerIncontro(), tennis.getNumeroGiocatoriPerIncontro())
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10") ,Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("50") ,Pavimentazione.TERRA_BATTUTA.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20") ,Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.IMPIANTO.toString(), calcetto.getNumeroGiocatoriPerIncontro(), calcetto.getNumeroGiocatoriPerIncontro())
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10") ,Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(pallavolo,TipiPrenotazione.IMPIANTO.toString(), pallavolo.getNumeroGiocatoriPerIncontro(), pallavolo.getNumeroGiocatoriPerIncontro())
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("30"), Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();
            
            
            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.IMPIANTO.toString(), tennis.getNumeroGiocatoriPerIncontro(), tennis.getNumeroGiocatoriPerIncontro())
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10") ,Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("50") ,Pavimentazione.TERRA_BATTUTA.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20") ,Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.IMPIANTO.toString(), calcetto.getNumeroGiocatoriPerIncontro(), calcetto.getNumeroGiocatoriPerIncontro())
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10") ,Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(pallavolo,TipiPrenotazione.IMPIANTO.toString(), pallavolo.getNumeroGiocatoriPerIncontro(), pallavolo.getNumeroGiocatoriPerIncontro())
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("30"), Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSquadra()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();



            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(tennis, TipiPrenotazione.LEZIONE.toString(), 1, 1)
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10") ,Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("50") ,Pavimentazione.TERRA_BATTUTA.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20") ,Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(calcetto, TipiPrenotazione.LEZIONE.toString(), 1, 5)
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("10") ,Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            this.listinoPrezziDescrizioniPolisportiva
            .nuovoPrenotabile_avviaCreazione(pallavolo,TipiPrenotazione.LEZIONE.toString(),1,6)
            .nuovoPrenotabile_impostaCostoOrario(Float.parseFloat("10"))
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("20"), Pavimentazione.CEMENTO.toString())
            .nuovoPrenotabile_impostaCostoPavimentazione(Float.parseFloat("30"), Pavimentazione.SINTETICO.toString())
            .nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
            .nuovoPrenotabile_salvaPrenotabileInCreazione();

            
            

            
      
               
        PrenotazioneSpecs prenotazioneSpecs = new PrenotazioneImpiantoSpecs();
        prenotazioneSpecs.setPending(true);
        Prenotazione prenotazione1 = new Prenotazione(0, prenotazioneSpecs);
        //prenotazioneSpecs.setPrenotazioneAssociata(prenotazione1);
        prenotazione1.getListaSpecifichePrenotazione().get(0).setPrenotazioneAssociata(prenotazione1);
        prenotazione1.setSportivoPrenotante(sportivo1);
        
        prenotazioneSpecs.setSpecificaDescription(
            this.listinoPrezziDescrizioniPolisportiva.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(TipiPrenotazione.IMPIANTO.toString(), tennis, ModalitaPrenotazione.SINGOLO_UTENTE.toString()));
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("impianto", impianto1);
        prenotazione1.getListaSpecifichePrenotazione().get(0).impostaValoriSpecificheExtraPrenotazione(mappaValori);
        
        Appuntamento appuntamento1 = getFactoryAppuntamenti().getAppuntamento(ModalitaPrenotazione.SINGOLO_UTENTE.toString());
        appuntamento1.setDataOraInizioAppuntamento(LocalDateTime.of(2021, 5, 26, 10, 30));
        appuntamento1.setDataOraFineAppuntamento(LocalDateTime.of(2021, 5, 26, 11, 30));
        appuntamento1.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
        		
        CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
            calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
        appuntamento1.setCalcolatoreCosto(calcolatoreCosto);
        appuntamento1.aggiungiPartecipante(sportivo1);
        appuntamento1.aggiungiPartecipante(sportivo2);
        appuntamento1.calcolaCosto();
        Calendario calendarioSpecs1 = new Calendario();
        calendarioSpecs1.aggiungiAppuntamento(appuntamento1);
        //prenotazione1.setCalendarioSpecifica(calendarioSpecs1, prenotazioneSpecs);
        impianto1.setCalendarioAppuntamentiImpianto(calendarioSpecs1);
        //prenotazione1.impostaCalendarioPrenotazioneDaSpecifiche();
        
       appuntamento1.setManutentore(sportivo2);

        //Prenotazione dell'impianto3 in una data diversa dalla data scelta dallo sportivo che sta effettuando la prenotazione;
        
        PrenotazioneSpecs prenotazioneSpecs2 = new PrenotazioneImpiantoSpecs();
        prenotazioneSpecs2.setPending(true);
        Prenotazione prenotazione2 = new Prenotazione(1, prenotazioneSpecs2);
        //prenotazioneSpecs2.setPrenotazioneAssociata(prenotazione2);
        prenotazione2.getListaSpecifichePrenotazione().get(0).setPrenotazioneAssociata(prenotazione2);
        prenotazione2.setSportivoPrenotante(sportivo2);
        
        
        prenotazioneSpecs2.setSpecificaDescription(
            this.listinoPrezziDescrizioniPolisportiva.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(TipiPrenotazione.IMPIANTO.toString(), tennis, ModalitaPrenotazione.SINGOLO_UTENTE.toString()));
        Map<String, Object> mappaValori2 = new HashMap<String, Object>();
        mappaValori2.put("impianto", impianto3);
        prenotazione2.getListaSpecifichePrenotazione().get(0).impostaValoriSpecificheExtraPrenotazione(mappaValori);
        
        Appuntamento appuntamento2 = getFactoryAppuntamenti().getAppuntamento(ModalitaPrenotazione.SINGOLO_UTENTE.toString());
        appuntamento2.setDataOraInizioAppuntamento(LocalDateTime.of(2021, 5, 21, 17, 00));
        appuntamento2.setDataOraFineAppuntamento(LocalDateTime.of(2021, 5, 21, 19,00));
        appuntamento2.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs2);
        		
        appuntamento2.setCalcolatoreCosto(calcolatoreCosto);
        appuntamento2.aggiungiPartecipante(sportivo2);
        appuntamento2.aggiungiPartecipante(sportivo3);
        appuntamento2.calcolaCosto();
        Calendario calendarioSpecs2 = new Calendario();
        calendarioSpecs2.aggiungiAppuntamento(appuntamento2);
        
        impianto3.setCalendarioAppuntamentiImpianto(calendarioSpecs2);
        // prenotazione2.setCalendarioSpecifica(calendarioSpecs2, prenotazioneSpecs2);
        // prenotazione2.impostaCalendarioPrenotazioneDaSpecifiche();
        
        appuntamento2.setManutentore(sportivo2);
        
        
        /*calendarioRepository.save(calendarioPrenotazione);
        calendarioRepository.save(calendarioPrenotazione2);*/
        
       // prenotazioneRepository.save(prenotazione1);
       // prenotazioneRepository.save(prenotazione2);
        appuntamentoRepository.saveAll(calendarioSpecs1.getListaAppuntamenti());
        appuntamentoRepository.saveAll(calendarioSpecs2.getListaAppuntamenti());
        		
        
    }


}