package it.univaq.esc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.esc.test.PopolaDB;
import it.univaq.esc.test.RegistrazioneCorsiTest;
import it.univaq.esc.test.RicercaAppuntamentiSottoscrivibili;
import it.univaq.esc.test.TestFactoryStatoEffettuaPrenotazione;
import it.univaq.esc.test.TestVerificaCalendario;


@SpringBootApplication
public class EscApplication implements CommandLineRunner {

     @Autowired
    private TestVerificaCalendario testCalendarioPrenotazioni;

    @Autowired
    private PopolaDB popolaDB;

    @Autowired
    private RicercaAppuntamentiSottoscrivibili ricercaAppuntamentiSottoscrivibili;
    
    @Autowired
    private TestFactoryStatoEffettuaPrenotazione testFactoryStatoEffettuaPrenotazione;
    
    @Autowired
    private RegistrazioneCorsiTest registrazioneCorsiTest;
	
	public static void main(String[] args) {
				
		SpringApplication.run(EscApplication.class, args);  
		
	}


    @Override
    public void run(String... args) throws Exception {
       
    	//registrazioneCorsiTest.test();
      // popolaDB.popola();
        //testCalendarioPrenotazioni.test();
        //ricercaAppuntamentiSottoscrivibili.test();
    	//testFactoryStatoEffettuaPrenotazione.test();
    	
        
    }

}
