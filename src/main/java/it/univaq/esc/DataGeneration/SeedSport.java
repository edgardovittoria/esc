package it.univaq.esc.DataGeneration;

import java.util.ArrayList;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Calcetto;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Tennis;
import it.univaq.esc.repository.SportRepository;

@Component
public final class SeedSport {
    
    
    
    private  static SportRepository sport;

    @Autowired
    private SportRepository sportInit;

    private static ArrayList<Sport> sportGenerati = new ArrayList<>();

    private static Logger loggerSeedSport = LoggerFactory.getLogger(seedSportivi.class);

    
    private SeedSport(){
        
    }

    @PostConstruct
    private void initializeSportiviRepo(){
        sport = this.sportInit;
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
        sportGenerati.add(Tennis.getInstance());
        sportGenerati.add(Calcetto.getInstance());
        

        try{
            sport.saveAll(sportGenerati);
            loggerSeedSport.info("...Sport creati");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}