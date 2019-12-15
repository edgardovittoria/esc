package it.univaq.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.SportRepository;

@Service
public class SportService {

	
	@Autowired
	private SportRepository sportRepo;

	public SportService(){

	}

		/**
	 * Associa un impianto allo sport.
	 */
	public void addImpiantoToSport( Sport sport, Impianto impianto) {
		sport.getImpianti().add(impianto);

		//Se non funziona la concatenazione precedente si può fare così
		// List<Impianto> impiantiSport = sport.getImpianti();
		// impiantiSport.add(impianto);
		// sport.setImpianti(impiantiSport);

	}

	/**
	 * Associa un istruttore allo sport.
	 */
	public void addIstruttoreToSport( Sport sport, Istruttore istruttore) {
		sport.getIstruttori().add(istruttore);
	}


	/**
	 * Rimuove un impianto tra quelli associati allo sport.
	 */
	public boolean removeImpiantoFromSport(Sport sport, Impianto impianto) {
		if (sport.getImpianti().contains(impianto)){
			sport.getImpianti().remove(impianto);
			return true;	
		}
		return false;
	}

	/**
	 * Rimuove un istruttore tra quelli associati allo sport.
	 */
	public boolean removeIstruttoreFromSport(Sport sport, Istruttore istruttore) {
		if (sport.getIstruttori().contains(istruttore)){
			sport.getIstruttori().remove(istruttore);
			return true;	
		}
		return false;
	}

	/**
	 * Restituice il numero di impianti associati allo sport passato come parametro.
	 */
	public int numberOfImpianti(Sport sport){
		if(sport.getImpianti().isEmpty()){
			return 0;
		}else{
		return sport.getImpianti().size();
		}
	}


	/**
	 * Restituice il numero di istruttori associati allo sport.
	 */
	public int numberOfIstruttori(Sport sport){
		if(sport.getIstruttori().isEmpty()){
			return 0;
		}else{
		return sport.getIstruttori().size();
		}
	}
	
}
