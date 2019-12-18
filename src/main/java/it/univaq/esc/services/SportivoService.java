package it.univaq.esc.services;

import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.SportivoRepository;
import it.univaq.utility.SimpleFactory;

@Service
public class SportivoService {

	@Autowired
	private SportivoRepository sportivoRepo;
	private List<Sportivo> sportivi;
	//private SimpleFactory simpleFactory = SimpleFactory.getInstance();
	
	private static SportivoService sportivoServiceInstance;
    

    private SportivoService(){

    }

    public static SportivoService getInstance(){
        if (sportivoServiceInstance == null) {
			sportivoServiceInstance = new SportivoService();
		}
		return  sportivoServiceInstance;
    }



//	public Prenotazione creaNuovaPrenotazione(int lastIDPrenotazione, Sportivo sportivo) {
//		Prenotazione nuovaPrenotazione = this.simpleFactory.getNuovaPrenotazione(lastIDPrenotazione, sportivo); 
//		return nuovaPrenotazione;
//	}
//	
	public void gestisciPagamento(Prenotazione prenotazione) {
		
	}
	
	public void addPrenotazione(Prenotazione prenotazione, Sportivo sportivo) {
		sportivo.getPrenotazioni().add(prenotazione);
		
	}
	
	@PostConstruct
	private void inizializzaSports(){
		sportivi = sportivoRepo.findAll();
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
