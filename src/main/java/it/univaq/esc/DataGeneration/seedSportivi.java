package it.univaq.esc.DataGeneration;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.fluttercode.datafactory.impl.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.SportivoRepository;

@Component
public final class seedSportivi {
    
    
    
    private  static SportivoRepository sportivi;

    @Autowired
    private SportivoRepository sportiviInit;

    private static ArrayList<Sportivo> sportiviGenerati = new ArrayList<>();

    private static Logger loggerSeedSportivi = LoggerFactory.getLogger(seedSportivi.class);

    
    private seedSportivi(){
        
    }

    @PostConstruct
    private void initializeSportiviRepo(){
        sportivi = this.sportiviInit;
    }

    private static void deleteGeneratedData(){
        
        sportivi.deleteAll();
    }

    /**
     * Genera un certo numero di record nella tabella sportivo.
     * @param numeroRecord definisce Il numero di record da generare.
     * @throws Exception eccezione generata in caso di mancato salvataggio dei record.
     */
    public static void generaDati(int numeroRecord) throws Exception{

        deleteGeneratedData();

        DataFactory factory = new DataFactory();
        Date minData = factory.getDate(1960, 1, 1);
        Date maxData = factory.getDate(2002, 12, 20);

        for (int i = 0; i < numeroRecord ; i++){
            Sportivo sp = new Sportivo();
            sp.setNome(factory.getFirstName());
            sp.setCognome(factory.getLastName());
            sp.setDataNascita(factory.getDateBetween(minData, maxData));
            sportiviGenerati.add(sp);
        }

        try{
            sportivi.saveAll(sportiviGenerati);
            loggerSeedSportivi.info("...Sportivi creati");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}