package it.univaq.esc.dto;

import java.util.List;

public class OpzioniPrenotazioneImpiantoDTO implements IOpzioniPrenotazioneDTO{
    
    private List<SportDTO> sport; 
	
	private List<SportivoDTO> sportivi;

    /**
     * @return List<SportDTO> return the sport
     */
    public List<SportDTO> getSport() {
        return sport;
    }

    /**
     * @param sport the sport to set
     */
    public void setSport(List<SportDTO> sport) {
        this.sport = sport;
    }

    /**
     * @return List<SportivoDTO> return the sportivi
     */
    public List<SportivoDTO> getSportivi() {
        return sportivi;
    }

    /**
     * @param sportivi the sportivi to set
     */
    public void setSportivi(List<SportivoDTO> sportivi) {
        this.sportivi = sportivi;
    }

}