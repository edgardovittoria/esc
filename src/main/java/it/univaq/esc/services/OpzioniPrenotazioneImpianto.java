package it.univaq.esc.services;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import it.univaq.esc.dto.IOpzioniPrenotazioneDTO;
import it.univaq.esc.dto.OpzioniPrenotazioneImpiantoDTO;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;

@Service
public class OpzioniPrenotazioneImpianto implements IOpzioniPrenotazione{

	
	private Set<Sport> sport; 
	
    private Set<Sportivo> sportivi;
    
    
    private SportService sportService = SportService.getInstance();

    
    private SportivoService sportivoService = SportivoService.getInstance();

	public OpzioniPrenotazioneImpianto(){}

	@Override
	public IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService,
			IstruttoreService istruttoreService, PrenotazioneService prenotazioneService) {
		//Logger logger = LoggerFactory.getLogger(OpzioniPrenotazioneImpianto.class);
		this.sport = sportService.getAllSport();
		//logger.info(this.sport.toString());
		this.sportivi = sportivoService.getSportivi();
		return this;
	}
	

    /**
     * @param sport the sport to set
     */
    public void setSport(Set<Sport> sport) {
        this.sport = sport;
    }

    /**
     * @param sportivi the sportivi to set
     */
    public void setSportivi(Set<Sportivo> sportivi) {
        this.sportivi = sportivi;
    }

    public Set<Sport> getSport(){
        return this.sport;
    }

    public Set<Sportivo> getSportivi(){
        return this.sportivi;
    }
    
    @Override
    public IOpzioniPrenotazioneDTO toDTO(){
        OpzioniPrenotazioneImpiantoDTO opzioniDTO = new OpzioniPrenotazioneImpiantoDTO();
        
        opzioniDTO.setSport(this.sportService.toDTO(this.sport));
        opzioniDTO.setSportivi(this.sportivoService.toDTO(this.sportivi));
        return opzioniDTO;
    }

}
