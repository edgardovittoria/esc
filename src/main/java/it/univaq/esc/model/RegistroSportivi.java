package it.univaq.esc.model;

import java.util.List;

import javax.annotation.PostConstruct;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.SportivoRepository;
@Component
public class RegistroSportivi {

	private List<Sportivo> sportivi;
	private static RegistroSportivi RegistroSportiviInstance;
	
	@Autowired
	private SportivoRepository sp;
		
	// private Logger log = LoggerFactory.getLogger(RegistroSportivi.class);

	private RegistroSportivi(){
		
	}

	public static RegistroSportivi getInstance() {
		if(RegistroSportiviInstance == null){
			RegistroSportiviInstance = new RegistroSportivi();
		}
		return RegistroSportiviInstance;
		
	}

	@PostConstruct
	private void inizializzaSports(){
		sportivi = sp.findAll();
		// log.info(""+sportivi.size());
	}

	
	public Sportivo getSportivo(int IDSportivo){
		// log.info(sportivi.get(IDSportivo).getNome()+" "+sportivi.get(IDSportivo).getCognome());
		for(Sportivo sp : sportivi){
			if(sp.getIDSportivo() == IDSportivo){
				return sp;
			}
		}
		return null;
	}

	public void addSportivo(Sportivo sportivo) {
		sportivi.add(sportivo);
		
	}


    /**
     * @return List<Sportivo> return the sportivi
     */
    public List<Sportivo> getSportivi() {
        return sportivi;
    }

    /**
     * @param sportivi the sportivi to set
     */
    public void setSportivi(List<Sportivo> sportivi) {
        this.sportivi = sportivi;
    }

}
