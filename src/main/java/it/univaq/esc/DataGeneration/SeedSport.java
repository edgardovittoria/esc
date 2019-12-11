package it.univaq.esc.DataGeneration;

import java.util.ArrayList;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Calcetto;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Tennis;
import it.univaq.esc.repository.ImpiantoRepository;
import it.univaq.esc.repository.IstruttoreRepository;
import it.univaq.esc.repository.SportRepository;

@Component
public final class SeedSport {
    
    
    
    private  static SportRepository sport;
    private static ImpiantoRepository impianti;
    private static IstruttoreRepository istruttori;

    @Autowired
    private SportRepository sportInit;
    @Autowired
    private ImpiantoRepository impiantiInit;
    @Autowired
    private IstruttoreRepository istuttoriInit;


    private static ArrayList<Sport> sportGenerati = new ArrayList<>();

    private static Logger loggerSeedSport = LoggerFactory.getLogger(seedSportivi.class);

    
    private SeedSport(){
        
    }

    @PostConstruct
    private void initializeSportiviRepo(){
        sport = this.sportInit;
    }
    
    @PostConstruct
    private void initializeImpiantiRepo() {
    	impianti = this.impiantiInit;
    }
    
    @PostConstruct
    private void initializeIstruttoriRepo() {
    	istruttori = this.istuttoriInit;
    }


    private static void deleteGeneratedData(){
        
        sport.deleteAll();
    }

    /**
     * Genera un certo numero di record nella tabella sport.
     * @throws Exception eccezione generata in caso di mancato salvataggio dei record.
     */
    public static void generaDati() throws Exception{

        deleteGeneratedData();
        Sport tennis = Tennis.getInstance();
        Sport calcetto = Calcetto.getInstance();
        
        tennis.addImpianto(impianti.getOne(1));
        tennis.addImpianto(impianti.getOne(3));
        
        calcetto.addImpianto(impianti.getOne(2));
        calcetto.addImpianto(impianti.getOne(3));
        
        for (int i = 1; i < 11; i++) {
			tennis.addIstruttore(istruttori.getOne(i));
		}
        for (int i = 11; i < 21; i++) {
			calcetto.addIstruttore(istruttori.getOne(i));
		}
        
        
        sportGenerati.add(tennis);
        sportGenerati.add(calcetto);
        

        try{
            sport.saveAll(sportGenerati);
            loggerSeedSport.info("...Sport creati");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}