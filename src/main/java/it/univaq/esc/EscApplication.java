package it.univaq.esc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.esc.test.PopolaDB;
import it.univaq.esc.test.TestVerificaCalendario;







@SpringBootApplication
public class EscApplication implements CommandLineRunner {

     @Autowired
    private TestVerificaCalendario testCalendarioPrenotazioni;

    @Autowired
    private PopolaDB popolaDB;
	
	public static void main(String[] args) {
				
		SpringApplication.run(EscApplication.class, args);  
		
	}

    

    


    @Override
    public void run(String... args) throws Exception {
        
       // popolaDB.popola();
        //testCalendarioPrenotazioni.test();

        
    }

}
