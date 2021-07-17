package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
			OrarioAppuntamentoDTO orario) {

		DatiFormPerAppuntamento datiFormPerAppuntamento = super.getDatiFormPerAppuntamentoUsando(formDati, orario);

		datiFormPerAppuntamento.setDescrizioneEvento(
				trovaPrenotabileDescrizioneImpiantoInModalitaSingoloUtenteRelativaA(formDati.getSportSelezionato()));

		datiFormPerAppuntamento.setInvitati(getListaUtentiIinvitatiAPartireDa(formDati.getSportiviInvitati()));

		datiFormPerAppuntamento.setPending(
				trovaNellaListaCheckboxesValorePendingRelativoAOrario(formDati.getCheckboxesPending(), orario));
		datiFormPerAppuntamento.setNumeroPartecipantiNonIscritti(formDati.getNumeroGiocatoriNonIscritti());

		return datiFormPerAppuntamento;
	}

	private PrenotabileDescrizione trovaPrenotabileDescrizioneImpiantoInModalitaSingoloUtenteRelativaA(
			String nomeSportSelezionato) {
		Sport sportSelezionato = getRegistroSport().getSportByNome(nomeSportSelezionato);
		PrenotabileDescrizione descrizioneEventoPrenotabile = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						TipoPrenotazione.IMPIANTO, sportSelezionato,
						ModalitaPrenotazione.SINGOLO_UTENTE);

		return descrizioneEventoPrenotabile;
	}

	private List<UtentePolisportiva> getListaUtentiIinvitatiAPartireDa(List<String> listaEmailInvitati) {
		List<UtentePolisportiva> invitati = new ArrayList<UtentePolisportiva>();
		for (String emailInvitato : listaEmailInvitati) {
			invitati.add(getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(emailInvitato));
		}
		return invitati;
	}

}
