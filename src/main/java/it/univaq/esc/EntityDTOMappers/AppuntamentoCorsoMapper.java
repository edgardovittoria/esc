package it.univaq.esc.EntityDTOMappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamentoDTO;
import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Valuta;
import it.univaq.esc.model.Valute;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoCorso;
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

		datiFormPerAppuntamento.setDescrizioneEvento(creaPrenotabileDescrizioneCorso(formDati));
		datiFormPerAppuntamento
				.setIstruttore(getIstruttoreAssociatoAllOrarioDallaListaIstruttori(orario, formDati.getIstruttori()));
		datiFormPerAppuntamento.setInvitati(trovaInvitatiCorsoAPartireDalla(formDati.getSportiviInvitati()));
		return datiFormPerAppuntamento;
	}

	private PrenotabileDescrizione creaPrenotabileDescrizioneCorso(FormPrenotabile formDati) {
		PrenotabileDescrizione descrizioneCorso = getCatalogoPrenotabili()
				.nuovoPrenotabile_avviaCreazione(this.getRegistroSport().getSportByNome(formDati.getSportSelezionato()),
						TipiPrenotazione.CORSO.toString(), formDati.getNumeroMinimoPartecipanti(),
						formDati.getNumeroMassimoPartecipanti())
				.nuovoPrenotabile_impostaCostoUnaTantum(
						new Costo(formDati.getCostoPerPartecipante(), new Valuta(Valute.EUR)))
				.nuovoPrenotabile_impostaModalitaPrenotazioneComeSingoloUtente()
				.nuovoPrenotabile_salvaPrenotabileInCreazione();

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
