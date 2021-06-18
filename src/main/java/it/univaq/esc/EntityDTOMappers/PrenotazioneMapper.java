package it.univaq.esc.EntityDTOMappers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneCorsoSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
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
	
	
	public PrenotazioneDTO convertiCorsoInPrenotazioneDTO(Prenotazione corsoPrenotazione, List<Appuntamento> listaAppuntamentiPrenotazione) {
		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		Map<String, Object> infoGeneraliCorso = new HashMap<String, Object>();
		infoGeneraliCorso.put("numeroMinimoPartecipanti",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getSogliaPartecipantiPerConferma());
		infoGeneraliCorso.put("numeroMassimoPartecipanti",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getSogliaMassimaPartecipanti());
		infoGeneraliCorso.put("costoPerPartecipante",
				corsoPrenotazione.getListaSpecifichePrenotazione().get(0).getCosto());
		List<UtentePolisportivaAbstract> invitati = ((PrenotazioneCorsoSpecs)corsoPrenotazione.getListaSpecifichePrenotazione().get(0)).getInvitati();
		List<UtentePolisportivaDTO> invitatiDTO = new ArrayList<UtentePolisportivaDTO>();
		for(UtentePolisportivaAbstract invitato : invitati) {
			invitatiDTO.add(getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(invitato));
		}
		infoGeneraliCorso.put("invitatiCorso", invitatiDTO);

		
		impostaDatiGeneraliPrenotazioneDTO(prenotazioneDTO, corsoPrenotazione, listaAppuntamentiPrenotazione);
		
		prenotazioneDTO.setInfoGeneraliEvento(infoGeneraliCorso);
		
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
