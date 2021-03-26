package it.univaq.esc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.esc.test.TestVerificaCalendario;







@SpringBootApplication
public class EscApplication implements CommandLineRunner {

     @Autowired
    private TestVerificaCalendario testCalendarioPrenotazioni;
	
	public static void main(String[] args) {
				
		SpringApplication.run(EscApplication.class, args);  
		
	}

    

    


    @Override
    public void run(String... args) throws Exception {
        
        
        testCalendarioPrenotazioni.test();

        
    }

}
