package it.univaq.esc.EntityDTOMappers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class PrenotazioneMapper extends EntityDTOMapper {

	public PrenotazioneDTO convertiInPrenotazioneDTO(Prenotazione prenotazione) {
		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		impostaDatiGeneraliPrenotazioneDTO(prenotazioneDTO, prenotazione);

		return prenotazioneDTO;
	}

	public PrenotazioneDTO convertiCorsoInPrenotazioneDTO(Prenotazione corsoPrenotazione) {
		PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
		Map<String, Object> infoGeneraliCorso = new HashMap<String, Object>();
		infoGeneraliCorso.put("numeroMinimoPartecipanti", corsoPrenotazione.getListaAppuntamenti().get(0)
				.getDescrizioneEventoPrenotato().getMinimoNumeroPartecipanti());
		infoGeneraliCorso.put("numeroMassimoPartecipanti", corsoPrenotazione.getListaAppuntamenti().get(0)
				.getDescrizioneEventoPrenotato().getMassimoNumeroPartecipanti());
		infoGeneraliCorso.put("costoPerPartecipante",
				corsoPrenotazione.getListaAppuntamenti().get(0).getCostoAppuntamento().getAmmontare());
//		List<UtentePolisportivaAbstract> invitati = ((PrenotazioneCorsoSpecs)corsoPrenotazione.getListaSpecifichePrenotazione().get(0)).getInvitati();
//		List<UtentePolisportivaDTO> invitatiDTO = new ArrayList<UtentePolisportivaDTO>();
//		for(UtentePolisportivaAbstract invitato : invitati) {
//			invitatiDTO.add(getMapperFactory().getUtenteMapper().convertiInUtentePolisportivaDTO(invitato));
//		}
//		infoGeneraliCorso.put("invitatiCorso", invitatiDTO);

		impostaDatiGeneraliPrenotazioneDTO(prenotazioneDTO, corsoPrenotazione);

		prenotazioneDTO.setInfoGeneraliEvento(infoGeneraliCorso);

		return prenotazioneDTO;
	}

	private void impostaDatiGeneraliPrenotazioneDTO(PrenotazioneDTO prenotazioneDTO, Prenotazione prenotazione) {
		prenotazioneDTO.setSportivoPrenotante(getMapperFactory().getUtenteMapper()
				.convertiInUtentePolisportivaDTO(prenotazione.getSportivoPrenotante()));
		prenotazioneDTO.setIdPrenotazione(prenotazione.getIdPrenotazione());
		for (Appuntamento app : prenotazione.getListaAppuntamenti()) {
			impostaMapperFactory(app.getModalitaPrenotazione());
			AppuntamentoDTO appDTO = getMapperFactory().getAppuntamentoMapper(app.getTipoPrenotazione())
					.convertiInAppuntamentoDTO(app);
			prenotazioneDTO.aggiungiAppuntamento(appDTO);
		}
		prenotazioneDTO.setTipoEventoNotificabile(prenotazione.getTipoEventoNotificabile());
	}

}
