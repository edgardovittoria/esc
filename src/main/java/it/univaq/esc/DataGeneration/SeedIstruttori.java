package it.univaq.esc.DataGeneration;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.fluttercode.datafactory.impl.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.IstruttoreRepository;
import it.univaq.esc.repository.SportRepository;

@Component
public final class SeedIstruttori {
    
    
    
	private  static IstruttoreRepository istruttori;
	private  static SportRepository sport;

    @Autowired
    private IstruttoreRepository istruttoriInit;
    @Autowired
    private SportRepository SportInit;
    

    private static ArrayList<Istruttore> istruttoriGenerati = new ArrayList<>();

    private static Logger loggerSeedIstruttori = LoggerFactory.getLogger(seedSportivi.class);

    
    private SeedIstruttori(){
        
    }

    @PostConstruct
    private void initializeIstruttoriRepo(){
        istruttori = this.istruttoriInit;
    }
    
    @PostConstruct
    private void initializeSportRepo(){
        sport = this.SportInit;
    }

    private static void deleteGeneratedData(){
        
        istruttori.deleteAll();
    }

    /**
     * Genera un certo numero di record nella tabella istruttori.
     * @param numeroRecord definisce Il numero di istruttori da generare per ogni sport.
     * @throws Exception eccezione generata in caso di mancato salvataggio dei record.
     */
    public static void generaDati(int numeroRecord) throws Exception{

        deleteGeneratedData();

        DataFactory factory = new DataFactory();
        Date minData = factory.getDate(1970, 1, 1);
        Date maxData = factory.getDate(1995, 12, 20);
        
        

        for (Sport s : sport.findAll()) {
			
        	for (int i = 0; i < numeroRecord ; i++){
            Istruttore is = new Istruttore();
            is.setNome(factory.getFirstName());
            is.setCognome(factory.getLastName());
            is.setDataNascita(factory.getDateBetween(minData, maxData));
            is.setSportInsegnato(s);
            istruttoriGenerati.add(is);
        	}
        }

        try{
            istruttori.saveAll(istruttoriGenerati);
            loggerSeedIstruttori.info("...Istruttori creati");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}