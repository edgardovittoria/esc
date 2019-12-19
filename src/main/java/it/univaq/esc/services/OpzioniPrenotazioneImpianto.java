package it.univaq.esc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.dto.IOpzioniPrenotazioneDTO;
import it.univaq.esc.dto.OpzioniPrenotazioneImpiantoDTO;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;

@Service
public class OpzioniPrenotazioneImpianto implements IOpzioniPrenotazione{

	
	private List<Sport> sport; 
	
    private List<Sportivo> sportivi;
    
    @Autowired
    private SportService sportService;

    @Autowired
    private SportivoService sportivoService;

	public OpzioniPrenotazioneImpianto(){}

	@Override
	public IOpzioniPrenotazione getOpzioni(ImpiantoService impiantoService, SportivoService sportivoService, SportService sportService,
			IstruttoreService istruttoreService, PrenotazioneService prenotazioneService) {
		sport = sportService.getAllSport();
		sportivi = sportivoService.getSportivi();
		return this;
	}
	

    /**
     * @param sport the sport to set
     */
    public void setSport(List<Sport> sport) {
        this.sport = sport;
    }

    /**
     * @param sportivi the sportivi to set
     */
    public void setSportivi(List<Sportivo> sportivi) {
        this.sportivi = sportivi;
    }

    public List<Sport> getSport(){
        return this.sport;
    }

    public List<Sportivo> getSportivi(){
        return this.sportivi;
    }
    
    @Override
    public IOpzioniPrenotazioneDTO toDTO(){
        OpzioniPrenotazioneImpiantoDTO opzioniDTO = new OpzioniPrenotazioneImpiantoDTO();
        opzioniDTO.setSport(this.sportService.toDTO(sport));
        opzioniDTO.setSportivi(this.sportivoService.toDTO(sportivi));
        return opzioniDTO;
    }

}
