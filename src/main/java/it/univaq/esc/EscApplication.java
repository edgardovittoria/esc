package it.univaq.esc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;







@SpringBootApplication
public class EscApplication implements CommandLineRunner {

    @Autowired
    private PopolaDB popolaDB;
	
	public static void main(String[] args) {
				
		SpringApplication.run(EscApplication.class, args);  
		
	}

    

    


    @Override
    public void run(String... args) throws Exception {
        
        
       popolaDB.popola();

        
    }

}
