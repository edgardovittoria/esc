package it.univaq.esc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.SportRepository;
import it.univaq.esc.repository.SportivoRepository;
import net.bytebuddy.build.HashCodeAndEqualsPlugin.ValueHandling.Sort;




@SpringBootApplication
public class EscApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
				
		SpringApplication.run(EscApplication.class, args);  
		
	}

    @Autowired
    private SportivoRepository sportivoRepository;

    @Autowired
    private SportRepository sportRepository;


    @Override
    public void run(String... args) throws Exception {
        Sportivo sportivoPrenotante = new Sportivo("Pippo", "Franco", "pippofranco@bagaglino.com");
        Sportivo sportivo1 = new Sportivo("Gianni", "cognome", "poppins@bianconiglio.com");
        Sportivo sportivo2 = new Sportivo("mariangelo", "sasso", "marsasso@boh.com");
        Sportivo sportivo3 = new Sportivo("tardigrado", "acqua", "matita@boh.com");

        Sport calcetto = new Sport("calcetto", 10);
        Sport tennis = new Sport("tennis", 2);
        Sport pallavolo = new Sport("pallavolo", 12);
        
        /*sportRepository.save(calcetto);
        sportRepository.save(tennis);
        sportRepository.save(pallavolo);*/


        sportivoPrenotante.aggiungiSporPraticatoDalloSportivo(tennis);
        sportivo1.aggiungiSporPraticatoDalloSportivo(calcetto);
        sportivo1.aggiungiSporPraticatoDalloSportivo(tennis);
        sportivo1.aggiungiSporPraticatoDalloSportivo(pallavolo);
        sportivo2.aggiungiSporPraticatoDalloSportivo(calcetto);
        sportivo2.aggiungiSporPraticatoDalloSportivo(tennis);
        sportivo3.aggiungiSporPraticatoDalloSportivo(calcetto);
        sportivo3.aggiungiSporPraticatoDalloSportivo(pallavolo);

        sportivoRepository.save(sportivoPrenotante);
        sportivoRepository.save(sportivo1);
        sportivoRepository.save(sportivo2);
        sportivoRepository.save(sportivo3);


        
    }

}
