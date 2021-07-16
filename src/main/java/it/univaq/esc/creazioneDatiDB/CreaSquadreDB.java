package it.univaq.esc.creazioneDatiDB;

import groovy.lang.Singleton;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.repository.SquadraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Singleton
@AllArgsConstructor
public class CreaSquadreDB {

	private CreaSportsDB creaSportsDB;
	private CreaUtentiDB creaUtentiDB;
	private SquadraRepository squadraRepository;
	
	public void creaSquadre() {
		Squadra squadra1 = new Squadra();
		squadra1.setNome("I Dugonghi");
		squadra1.setSport(creaSportsDB.getSportConNome("calcetto"));
		squadra1.aggiungiAmministratore(creaUtentiDB.getUtenteBy("matita@boh.com"));
		squadra1.aggiungiMembro(creaUtentiDB.getUtenteBy("poppins@bianconiglio.com"));
		squadra1.aggiungiMembro(creaUtentiDB.getUtenteBy("marsasso@boh.com"));

		Squadra squadra2 = new Squadra();
		squadra2.setNome("Le Nutrie");
		squadra2.setSport(creaSportsDB.getSportConNome("pallavolo"));
		squadra2.aggiungiAmministratore(creaUtentiDB.getUtenteBy("pippofranco@bagaglino.com"));
		squadra2.aggiungiMembro(creaUtentiDB.getUtenteBy("poppins@bianconiglio.com"));
		squadra2.aggiungiMembro(creaUtentiDB.getUtenteBy("matita@boh.com"));
		
		Squadra squadra3 = new Squadra();
		squadra3.setNome("Le Strolaghe");
		squadra3.setSport(creaSportsDB.getSportConNome("calcetto"));
		squadra3.aggiungiAmministratore(creaUtentiDB.getUtenteBy("pippofranco@bagaglino.com"));
		squadra3.aggiungiMembro(creaUtentiDB.getUtenteBy("poppins@bianconiglio.com"));
		squadra3.aggiungiMembro(creaUtentiDB.getUtenteBy("marsasso@boh.com"));

		squadraRepository.save(squadra1);
		squadraRepository.save(squadra2);
		squadraRepository.save(squadra3);
	}
}
