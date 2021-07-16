package it.univaq.esc.EntityDTOMappers;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoCorso;
import it.univaq.esc.model.prenotazioni.DatiFormPerAppuntamento;
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
public class AppuntamentoCorsoMapper extends AppuntamentoMapper {

	@Override
	public AppuntamentoDTO convertiInAppuntamentoDTO(Appuntamento appuntamentoDaConvertire) {
		AppuntamentoDTO appuntamentoDTO = super.convertiInAppuntamentoDTO(appuntamentoDaConvertire);
		AppuntamentoCorso appuntamentoCorso = (AppuntamentoCorso) appuntamentoDaConvertire;
		appuntamentoDTO.setInvitati(appuntamentoCorso.getNominativiInvitati());
		appuntamentoDTO.setIstruttore(appuntamentoCorso.getNominativoIstruttore());
		return appuntamentoDTO;
	}

	@Override
	public DatiFormPerAppuntamento getDatiFormPerAppuntamentoUsando(FormPrenotabile formDati,
			OrarioAppuntamentoDTO orario) {
		DatiFormPerAppuntamento datiFormPerAppuntamento = super.getDatiFormPerAppuntamentoUsando(formDati, orario);

		datiFormPerAppuntamento.setDescrizioneEvento(trovaPrenotabileDescrizioneCorsoDal(formDati.getNomeEvento()));
		datiFormPerAppuntamento
				.setIstruttore(getIstruttoreAssociatoAllOrarioDallaListaIstruttori(orario, formDati.getIstruttori()));
		datiFormPerAppuntamento.setInvitati(trovaInvitatiCorsoAPartireDalla(formDati.getSportiviInvitati()));
		return datiFormPerAppuntamento;
	}

	private PrenotabileDescrizione trovaPrenotabileDescrizioneCorsoDal(String nomeEvento) {
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili().trovaPrenotabileDescrizioneDalla(nomeEvento);
		return descrizioneCorso;
	}

	private UtentePolisportiva getIstruttoreAssociatoAllOrarioDallaListaIstruttori(OrarioAppuntamentoDTO orario,
			List<IstruttoreSelezionato> listaIstruttoriSelezionati) {
		IstruttoreSelezionato istruttoreSelezionato = null;
		for (IstruttoreSelezionato istruttore : listaIstruttoriSelezionati) {
			if (istruttore.getIdSelezione() == orario.getId()) {
				istruttoreSelezionato = istruttore;
			}
		}
		return getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(istruttoreSelezionato.getIstruttore());
	}

	private List<UtentePolisportiva> trovaInvitatiCorsoAPartireDalla(List<String> listaEmailInvitati) {
		List<UtentePolisportiva> invitati = new ArrayList<UtentePolisportiva>();
		for (String emailInvitato : listaEmailInvitati) {
			invitati.add(getRegistroUtentiPolisportiva().trovaUtenteInBaseAllaSua(emailInvitato));
		}
		return invitati;
	}
}
