package it.univaq.esc.services;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.dto.SportivoDTO;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.repository.SportivoRepository;


@Service
public class SportivoService {

	@Autowired
	private SportivoRepository sportivoRepo;
	private Set<Sportivo> sportivi;
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
	private void inizializzaSportivi(){
		sportivi =  new HashSet<Sportivo>(sportivoRepo.findAll());
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
    public Set<Sportivo> getSportivi() {
        return sportivi;
    }

    /**
     * @param sportivi the sportivi to set
     */
    public void setSportivi(Set<Sportivo> sportivi) {
        this.sportivi = sportivi;
	}
	

	public SportivoDTO toDTO(Sportivo sportivo){
		SportivoDTO sportivoDTO = new SportivoDTO();
		setOptionDTO(sportivo, sportivoDTO);

		return sportivoDTO;
		
	}

	public SportivoDTO toDTO(int IDSportivo){
		Sportivo sportivo = sportivoRepo.findById(IDSportivo).get();
		SportivoDTO sportivoDTO = new SportivoDTO();
		setOptionDTO(sportivo, sportivoDTO);

		return sportivoDTO;
		
	}

	public Set<SportivoDTO> toDTO(Set<Sportivo> sportivi){
		Set<SportivoDTO> listaSportivi = new HashSet<SportivoDTO>();
		for (Sportivo sportivo : sportivi) {
			SportivoDTO sportivoDTO = new SportivoDTO();
			setOptionDTO(sportivo, sportivoDTO);
			listaSportivi.add(sportivoDTO);
		}
		return listaSportivi;
		
	}




	

	private void setOptionDTO(Sportivo sportivo, SportivoDTO sportivoDTO){

		sportivoDTO.setNome(sportivo.getNome());
		sportivoDTO.setCognome(sportivo.getCognome());
		sportivoDTO.setDataNascita(sportivo.getDataNascita());
		sportivoDTO.setCarteCredito(sportivo.getCarteCredito());
		sportivoDTO.setPartecipazioni(sportivo.getPartecipazioni());
		sportivoDTO.setPrenotazioni(sportivo.getPrenotazioni());
		sportivoDTO.setQuotePartecipazione(sportivo.getQuotePartecipazione());
		sportivoDTO.setSconti(sportivo.getSconti());
	}

}
