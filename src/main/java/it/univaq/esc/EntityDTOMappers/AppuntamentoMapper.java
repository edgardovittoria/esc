package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.CheckboxPendingSelezionato;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.dtoObjects.QuotaPartecipazioneDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;

public abstract class AppuntamentoMapper extends EntityDTOMapper {

	public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
		AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();

		appuntamentoDTO.setIdAppuntamento(appuntamentoDaConvertire.getIdNotificabile());
		appuntamentoDTO.setDataAppuntamento(appuntamentoDaConvertire.getDataAppuntamento());
		appuntamentoDTO.setOraInizioAppuntamento(appuntamentoDaConvertire.getOraInizioAppuntamento());
		appuntamentoDTO.setOraFineAppuntamento(appuntamentoDaConvertire.getOraFineAppuntamento());
		appuntamentoDTO.setPartecipanti(getListaEmailDegli(appuntamentoDaConvertire.getUtentiPartecipanti()));
		appuntamentoDTO.setQuotePartecipazione(convertiInDTOLaLista(appuntamentoDaConvertire.getQuotePartecipazione()));
		appuntamentoDTO
				.setCreatore(appuntamentoDaConvertire.getUtenteCheHaEffettuatoLaPrenotazioneRelativa().getEmail());
		if (appuntamentoDaConvertire.haManutentoreAssegnato()) {
			appuntamentoDTO.setManutentore(appuntamentoDaConvertire.getNominativoManutentore());
		}
		appuntamentoDTO.setModalitaPrenotazione(appuntamentoDaConvertire.getModalitaPrenotazione().toString());
		appuntamentoDTO.setTipoPrenotazione(appuntamentoDaConvertire.getTipoPrenotazione());
		appuntamentoDTO.setPending(appuntamentoDaConvertire.isPending());
		appuntamentoDTO.setConfermata(appuntamentoDaConvertire.isConfermato());
		appuntamentoDTO.setCosto(appuntamentoDaConvertire.getCostoAppuntamento().getAmmontare());
		appuntamentoDTO.setSportAssociato(
				getMapperFactory().getSportMapper().convertiInSportDTO(appuntamentoDaConvertire.getSportEvento()));
		appuntamentoDTO.setIdImpiantoPrenotato(appuntamentoDaConvertire.getStrutturaPrenotata().getIdNotificabile());
		if(appuntamentoDaConvertire.getStrutturaPrenotata() instanceof Impianto) {
		appuntamentoDTO.setPavimentazioneImpianto(
				((Impianto)appuntamentoDaConvertire.getStrutturaPrenotata()).getTipoPavimentazione().toString());
		}
		appuntamentoDTO.setTipoEventoNotificabile(appuntamentoDaConvertire.getTipoEventoNotificabile());

		return appuntamentoDTO;
	}

	private List<String> getListaEmailDegli(List<UtentePolisportiva> utentiPartecipanti) {
		List<String> listaEmailPartecipanti = new ArrayList<String>();
		utentiPartecipanti.forEach((partecipante) -> listaEmailPartecipanti.add(partecipante.getEmail()));

		return listaEmailPartecipanti;
	}

	private List<QuotaPartecipazioneDTO> convertiInDTOLaLista(List<QuotaPartecipazione> quotePartecipazione) {
		List<QuotaPartecipazioneDTO> listaQuote = new ArrayList<QuotaPartecipazioneDTO>();
		for (QuotaPartecipazione quota : quotePartecipazione) {
			QuotaPartecipazioneDTO quotaDTO = getMapperFactory().getQuotaPartecipazioneMapper()
					.convertiInQuotaPartecipazioneDTO(quota);
			listaQuote.add(quotaDTO);

		}
		return listaQuote;
	}

	public DatiFormPerAppuntamento getDatiFormPerAppuntamentoUsando(FormPrenotabile formDati,
			OrarioAppuntamentoDTO orario) {
		DatiFormPerAppuntamento datiFormPerAppuntamento = new DatiFormPerAppuntamento();

		datiFormPerAppuntamento.setDataAppuntamento(orario.getDataPrenotazione());
		datiFormPerAppuntamento.setOraInizio(orario.getOraInizio());
		datiFormPerAppuntamento.setOraFine(orario.getOraFine());
		
		datiFormPerAppuntamento.setImpiantoPrenotato(
				trovaNellaListaImpiantiPrenotatiQuelloRelativoAOrario(formDati.getImpianti(), orario));

		return datiFormPerAppuntamento;

	}

	protected boolean trovaNellaListaCheckboxesValorePendingRelativoAOrario(
			List<CheckboxPendingSelezionato> listaCheckboxes, OrarioAppuntamentoDTO orario) {
		for (CheckboxPendingSelezionato checkbox : listaCheckboxes) {
			if (checkbox.getIdSelezione() == orario.getId()) {
				return checkbox.isPending();
			}
		}
		return false;
	}

	private Impianto trovaNellaListaImpiantiPrenotatiQuelloRelativoAOrario(
			List<ImpiantoSelezionato> listaImpiantiPrenotati, OrarioAppuntamentoDTO orario) {
		ImpiantoSelezionato impiantoSelezionato = null;
		for (ImpiantoSelezionato impianto : listaImpiantiPrenotati) {
			if (impianto.getIdSelezione() == orario.getId()) {
				impiantoSelezionato = impianto;
			}
		}
		return getRegistroImpianti().getImpiantoByID(impiantoSelezionato.getIdImpianto());
	}

}
