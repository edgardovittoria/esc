package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.QuotaPartecipazioneDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;

import it.univaq.esc.utility.BeanUtil;


public abstract class AppuntamentoMapper extends EntityDTOMapper {

	public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
		AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();

		appuntamentoDTO.setIdAppuntamento(appuntamentoDaConvertire.getIdAppuntamento());
		appuntamentoDTO.setDataAppuntamento(appuntamentoDaConvertire.getDataAppuntamento());
		appuntamentoDTO.setOraInizioAppuntamento(appuntamentoDaConvertire.getOraInizioAppuntamento());
		appuntamentoDTO.setOraFineAppuntamento(appuntamentoDaConvertire.getOraFineAppuntamento());

		impostaMapperFactory(appuntamentoDaConvertire.getModalitaPrenotazione());
//		PrenotazioneSpecsDTO specificaDTO = getMapperFactory()
//				.getPrenotazioneSpecsMapper(appuntamentoDaConvertire.getTipoPrenotazione())
//				.convertiInPrenotazioneSpecsDTO(appuntamentoDaConvertire.getPrenotazioneSpecsAppuntamento());
//
//		appuntamentoDTO.setSpecificaPrenotazione(specificaDTO);
		for (UtentePolisportiva partecipante : appuntamentoDaConvertire.getUtentiPartecipanti()) {

			appuntamentoDTO.aggiungiPartecipante(partecipante.getEmail());
		}
		
		

		List<QuotaPartecipazioneDTO> listaQuote = new ArrayList<QuotaPartecipazioneDTO>();
		for (QuotaPartecipazione quota : appuntamentoDaConvertire.getQuotePartecipazione()) {
			QuotaPartecipazioneDTO quotaDTO = getMapperFactory().getQuotaPartecipazioneMapper()
					.convertiInQuotaPartecipazioneDTO(quota);
			listaQuote.add(quotaDTO);

		}
		appuntamentoDTO.setQuotePartecipazione(listaQuote);

		//appuntamentoDTO.setCreatore((String) appuntamentoDaConvertire.creatoDa().getProprieta().get("email"));

		if (appuntamentoDaConvertire.getManutentore() != null) {
			appuntamentoDTO.setManutentore(appuntamentoDaConvertire.getNominativoManutentore());
		}

		appuntamentoDTO.setModalitaPrenotazione(appuntamentoDaConvertire.getModalitaPrenotazione());

		
		appuntamentoDTO.setTipoPrenotazione(appuntamentoDaConvertire.getTipoPrenotazione());
		appuntamentoDTO.setPending(appuntamentoDaConvertire.isPending());
		appuntamentoDTO.setConfermata(appuntamentoDaConvertire.isConfermato());
		appuntamentoDTO.setCosto(appuntamentoDaConvertire.getCostoAppuntamento());
		SportDTO sportDTO = getMapperFactory().getSportMapper().convertiInSportDTO(appuntamentoDaConvertire.getDescrizioneEventoPrenotato().getSportAssociato());
		appuntamentoDTO.setSportAssociato(sportDTO);
		appuntamentoDTO.setIdImpiantoPrenotato(appuntamentoDaConvertire.getImpiantoPrenotato().getIdImpianto());
		appuntamentoDTO.setPavimentazioneImpianto(appuntamentoDaConvertire.getImpiantoPrenotato().getTipoPavimentazione().toString());

		return appuntamentoDTO;
	}

	

}
