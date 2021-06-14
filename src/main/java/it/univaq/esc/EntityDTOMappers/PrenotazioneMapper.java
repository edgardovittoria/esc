package it.univaq.esc.EntityDTOMappers;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class PrenotazioneMapper extends EntityDTOMapper{
	
	
	
	public PrenotazioneDTO convertiInPrenotazioneDTO(Prenotazione prenotazione, List<Appuntamento> listaAppuntamentiPrenotazione) {
		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		impostaDatiGeneraliPrenotazioneDTO(prenotazioneDTO, prenotazione, listaAppuntamentiPrenotazione);
		
		
		return prenotazioneDTO;
	}
	
	
	public PrenotazioneDTO convertiInPrenotazioneDTO(Prenotazione prenotazione, List<Appuntamento> listaAppuntamentiPrenotazione, Map<String, Object> infoGeneraliEvento) {
		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		impostaDatiGeneraliPrenotazioneDTO(prenotazioneDTO, prenotazione, listaAppuntamentiPrenotazione);
		
		prenotazioneDTO.setInfoGeneraliEvento(infoGeneraliEvento);
		
		return prenotazioneDTO;
	}
	
	
	
	
	private void impostaDatiGeneraliPrenotazioneDTO(PrenotazioneDTO prenotazioneDTO, Prenotazione prenotazione, List<Appuntamento> listaAppuntamentiPrenotazione) {
		prenotazioneDTO.setSportivoPrenotante(getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(prenotazione.getSportivoPrenotante()));
		prenotazioneDTO.setIdPrenotazione(prenotazione.getIdPrenotazione());
		
		for (Appuntamento app : listaAppuntamentiPrenotazione) {
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper().convertiInAppuntamentoDTO(app);
			prenotazioneDTO.aggiungiAppuntamento(appDTO);
		}
		prenotazioneDTO.setTipoEventoNotificabile(prenotazione.getTipoEventoNotificabile());
	}
	
}
