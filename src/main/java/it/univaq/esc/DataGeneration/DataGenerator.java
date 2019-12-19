package it.univaq.esc.DataGeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class DataGenerator {
    
    private final Logger logger = LoggerFactory.getLogger(DataGenerator.class);

    
    public DataGenerator(){

    }
    /**
     * Popola il database richiamando seeds specifici per ogni model.
     * @param evento
     * @throws Exception
     */
//     @EventListener
//     public void popola(ContextRefreshedEvent evento) throws Exception {
//         try{
//             logger.info("Generazione dati...");
//            
//
//             /* 
//             Inserire qui i vari seed da richiamare.
//             */
//             SeedImpianti.generaDati();
//             SeedIstruttori.generaDati(20);
//             SeedSport.generaDati();
//             seedSportivi.generaDati(50);
//            
//            
//
//             logger.info("...generazione totale completata!");
//         }
//         catch(Exception e){
//             e.printStackTrace();
//         }
//     }
}


    
        
    

    
    
    
