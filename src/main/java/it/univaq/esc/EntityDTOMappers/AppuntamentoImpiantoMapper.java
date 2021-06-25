package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.CheckboxPendingSelezionato;
import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Singleton
@NoArgsConstructor
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class AppuntamentoImpiantoMapper extends AppuntamentoMapper {

	@Override
	public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {

		AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);

		AppuntamentoImpianto appuntamentoImpianto = (AppuntamentoImpianto) appuntamentoDaConvertire;

		appuntamentoDTO.setInvitati(appuntamentoImpianto.getNominativiInvitati());

		return appuntamentoDTO;
	}

	@Override
	public DatiFormPerAppuntamento getDatiFormPerAppuntamentoUsando(FormPrenotabile formDati,
			OrarioAppuntamento orario) {
		
		DatiFormPerAppuntamento datiFormPerAppuntamento = super.getDatiFormPerAppuntamentoUsando(formDati, orario);

		FormPrenotaImpianto formPrenotaImpianto = (FormPrenotaImpianto) formDati;

		
		datiFormPerAppuntamento
				.setDescrizioneEvento(trovaPrenotabileDescrizioneImpiantoInModalitaSingoloUtenteRelativaA(
						formPrenotaImpianto.getSportSelezionato()));
		
		datiFormPerAppuntamento.setPending(trovaNellaListaCheckboxesValorePendingRelativoAOrario(
				formPrenotaImpianto.getCheckboxesPending(), orario));
		
		datiFormPerAppuntamento.setImpiantoPrenotato(
				trovaNellaListaImpiantiPrenotatiQuelloRelativoAOrario(formPrenotaImpianto.getImpianti(), orario));
		
		datiFormPerAppuntamento.setInvitati(getListaUtentiIinvitatiAPartireDa(formPrenotaImpianto.getSportiviInvitati()));
		
		datiFormPerAppuntamento.setNumeroPartecipantiNonIscritti(formPrenotaImpianto.getNumeroGiocatoriNonIscritti());

		return datiFormPerAppuntamento;
	}
	

	private PrenotabileDescrizione trovaPrenotabileDescrizioneImpiantoInModalitaSingoloUtenteRelativaA(
			String nomeSportSelezionato) {
		Sport sportSelezionato = getRegistroSport().getSportByNome(nomeSportSelezionato);
		PrenotabileDescrizione descrizioneEventoPrenotabile = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipiPrenotazione.IMPIANTO.toString(), sportSelezionato,
						ModalitaPrenotazione.SINGOLO_UTENTE.toString());

		return descrizioneEventoPrenotabile;
	}

	private boolean trovaNellaListaCheckboxesValorePendingRelativoAOrario(
			List<CheckboxPendingSelezionato> listaCheckboxes, OrarioAppuntamento orario) {
		for (CheckboxPendingSelezionato checkbox : listaCheckboxes) {
			if (checkbox.getIdSelezione() == orario.getId()) {
				return checkbox.isPending();
			}
		}
		return false;
	}

	private Impianto trovaNellaListaImpiantiPrenotatiQuelloRelativoAOrario(
			List<ImpiantoSelezionato> listaImpiantiPrenotati, OrarioAppuntamento orario) {
		ImpiantoSelezionato impiantoSelezionato = null;
		for (ImpiantoSelezionato impianto : listaImpiantiPrenotati) {
			if (impianto.getIdSelezione() == orario.getId()) {
				impiantoSelezionato = impianto;
			}
		}
		return getRegistroImpianti().getImpiantoByID(impiantoSelezionato.getIdImpianto());
	}

	private List<UtentePolisportiva> getListaUtentiIinvitatiAPartireDa(List<String> listaEmailInvitati) {
		List<UtentePolisportiva> invitati = new ArrayList<UtentePolisportiva>();
		for (String emailInvitato : listaEmailInvitati) {
			invitati.add(getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(emailInvitato));
		}
		return invitati;
	}

}
