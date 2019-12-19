package it.univaq.esc.dto;

import java.util.List;
import java.util.Set;


public class OpzioniPrenotazioneImpiantoDTO implements IOpzioniPrenotazioneDTO{
    
    private Set<SportDTO> sport; 
	
	private Set<SportivoDTO> sportivi;

    /**
     * @return List<SportDTO> return the sport
     */
    public Set<SportDTO> getSport() {
        return sport;
    }

    /**
     * @param sport the sport to set
     */
    public void setSport(Set<SportDTO> sport) {
        this.sport = sport;
        //Logger logger = LoggerFactory.getLogger(OpzioniPrenotazioneImpiantoDTO.class);
        //logger.info(this.sport.get(0).getNome());
    }

    /**
     * @return List<SportivoDTO> return the sportivi
     */
    public Set<SportivoDTO> getSportivi() {
        return sportivi;
    }

    /**
     * @param sportivi the sportivi to set
     */
    public void setSportivi(Set<SportivoDTO> sportivi) {
        this.sportivi = sportivi;
    }

}