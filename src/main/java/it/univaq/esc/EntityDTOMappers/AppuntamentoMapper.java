package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.dtoObjects.QuotaPartecipazioneDTO;
import it.univaq.esc.dtoObjects.UtentePolisportivaDTO;
import it.univaq.esc.factory.ElementiPrenotazioneFactory;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.utility.BeanUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@Singleton
@NoArgsConstructor
public class AppuntamentoMapper extends EntityDTOMapper {

	

	public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
		AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();

		appuntamentoDTO.setIdAppuntamento(appuntamentoDaConvertire.getIdAppuntamento());
		appuntamentoDTO.setDataAppuntamento(appuntamentoDaConvertire.getDataAppuntamento());
		appuntamentoDTO.setOraInizioAppuntamento(appuntamentoDaConvertire.getOraInizioAppuntamento());
		appuntamentoDTO.setOraFineAppuntamento(appuntamentoDaConvertire.getOraFineAppuntamento());

		impostaMapperFactory(appuntamentoDaConvertire.getModalitaPrenotazione());
		PrenotazioneSpecsDTO specificaDTO = getMapperFactory()
				.getPrenotazioneSpecsMapper(appuntamentoDaConvertire.getTipoPrenotazione())
				.convertiInPrenotazioneSpecsDTO(appuntamentoDaConvertire.getPrenotazioneSpecsAppuntamento());

		appuntamentoDTO.setSpecificaPrenotazione(specificaDTO);
		for (UtentePolisportivaAbstract partecipante : appuntamentoDaConvertire.getUtentiPartecipanti()) {
			UtentePolisportivaDTO partecipanteDTO = getMapperFactory().getUtenteMapper()
					.convertiInUtentePolisportivaDTO(partecipante);

			appuntamentoDTO.aggiungiPartecipante(partecipanteDTO);
		}
		List<QuotaPartecipazioneDTO> listaQuote = new ArrayList<QuotaPartecipazioneDTO>();
		for (QuotaPartecipazione quota : appuntamentoDaConvertire.getQuotePartecipazione()) {
			QuotaPartecipazioneDTO quotaDTO = getMapperFactory().getQuotaPartecipazioneMapper()
					.convertiInQuotaPartecipazioneDTO(quota);
			listaQuote.add(quotaDTO);

		}
		appuntamentoDTO.setQuotePartecipazione(listaQuote);

		UtentePolisportivaDTO creatore = getMapperFactory().getUtenteMapper()
				.convertiInUtentePolisportivaDTO(appuntamentoDaConvertire.creatoDa());
		appuntamentoDTO.setCreatore(creatore);

		if (appuntamentoDaConvertire.getManutentore() != null) {
			appuntamentoDTO.setIdManutentore(appuntamentoDaConvertire.getManutentore().getId());
		}

		appuntamentoDTO.setModalitaPrenotazione(appuntamentoDaConvertire.getModalitaPrenotazione());

		if (appuntamentoDaConvertire.getModalitaPrenotazione().equals(ModalitaPrenotazione.SQUADRA.toString())) {
			List<Integer> squadrePartecipanti = new ArrayList<Integer>();
			for (Object squadra : appuntamentoDaConvertire.getPartecipantiAppuntamento()) {
				Squadra squadraPartecipante = (Squadra) squadra;
				squadrePartecipanti.add(squadraPartecipante.getIdSquadra());
			}
			appuntamentoDTO.setSquadrePartecipanti(squadrePartecipanti);

		}

		return appuntamentoDTO;
	}
	
	
	private void impostaMapperFactory(String modalitaPrenotazione) {
		setMapperFactory(BeanUtil.getBean("MAPPER_" + modalitaPrenotazione, MapperFactory.class));
	}

}
