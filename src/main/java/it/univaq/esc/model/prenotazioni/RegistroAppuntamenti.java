package it.univaq.esc.model.prenotazioni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;

import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;

import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class RegistroAppuntamenti {

	private AppuntamentoRepository appuntamentoRepository;

	private RegistroPrenotazioni registroPrenotazioni;

	// @Setter(value = AccessLevel.PRIVATE)
	private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();

	public RegistroAppuntamenti(AppuntamentoRepository appuntamentoRepository,
			RegistroPrenotazioni registroPrenotazioni) {
		this.setAppuntamentoRepository(appuntamentoRepository);
		setRegistroPrenotazioni(registroPrenotazioni);
		popola();
	}

	public void popola() {
		setListaAppuntamenti(getRegistroPrenotazioni()
				.getAppuntamentiAssociatiAdUna(getRegistroPrenotazioni().getPrenotazioniRegistrate()));
		for (Appuntamento appuntamento : this.getListaAppuntamenti()) {
			CalcolatoreCostoBase calcBase = new CalcolatoreCostoBase();
			CalcolatoreCostoComposito calcComposito = new CalcolatoreCostoComposito();
			calcComposito.aggiungiStrategiaCosto(calcBase);
			appuntamento.setCalcolatoreCosto(calcComposito);
		}

	}

	public void salvaAppuntamento(Appuntamento appuntamento) {
		this.getListaAppuntamenti().add(appuntamento);
		this.getAppuntamentoRepository().save(appuntamento);
	}

	public void aggiornaAppuntamento(Appuntamento appuntamento) {
		this.getAppuntamentoRepository().save(appuntamento);
	}

	public void salvaListaAppuntamenti(List<Appuntamento> listaAppuntamenti) {
		this.getListaAppuntamenti().addAll(listaAppuntamenti);

	}

	public List<Appuntamento> getAppuntamentiSottoscrivibiliSingoloUtentePerTipo(String tipoPrenotazione,
			UtentePolisportiva utentePerCuiTrovareAppuntamenti) {
		List<Appuntamento> appuntamentiFiltrati = this.filtraAppuntamentiPerDataOra(this.getListaAppuntamenti(),
				LocalDateTime.now());
		appuntamentiFiltrati = this.filtraAppuntamentiPerTipoPrenotazione(appuntamentiFiltrati, tipoPrenotazione);
		appuntamentiFiltrati = this.filtraAppuntamentiPerModalitaPrenotazione(appuntamentiFiltrati,
				ModalitaPrenotazione.SINGOLO_UTENTE.toString());
		appuntamentiFiltrati = this.filtraAppuntamentiPending(appuntamentiFiltrati);
		// appuntamentiFiltrati =
		// this.escludiAppuntamentiPerUtenteCreatore(appuntamentiFiltrati,
		// utentePerCuiTrovareAppuntamenti);
		appuntamentiFiltrati = this.escludiAppuntamentiPerPartecipante(appuntamentiFiltrati,
				utentePerCuiTrovareAppuntamenti);

		return appuntamentiFiltrati;
	}

	public List<Appuntamento> getAppuntamentiSottoscrivibiliSquadraPerTipo(String tipoPrenotazione,
			Squadra squadraPerCuiTrovareAppuntamenti) {
		List<Appuntamento> appuntamentiFiltrati = this.filtraAppuntamentiPerDataOra(this.getListaAppuntamenti(),
				LocalDateTime.now());
		appuntamentiFiltrati = this.filtraAppuntamentiPerTipoPrenotazione(appuntamentiFiltrati, tipoPrenotazione);
		appuntamentiFiltrati = this.filtraAppuntamentiPerModalitaPrenotazione(appuntamentiFiltrati,
				ModalitaPrenotazione.SQUADRA.toString());
		appuntamentiFiltrati = this.filtraAppuntamentiPending(appuntamentiFiltrati);
		appuntamentiFiltrati = this.escludiAppuntamentiPerSquadraPartecipante(appuntamentiFiltrati,
				squadraPerCuiTrovareAppuntamenti);

		return appuntamentiFiltrati;
	}

//	public List<Appuntamento> getAppuntamentiPerPartecipanteNonCreatore(
//			UtentePolisportiva partecipanteNonCreatore) {
//		List<Appuntamento> appuntamenti = this.getListaAppuntamenti();
//		appuntamenti = this.filtraAppuntamentiPerPartecipante(appuntamenti, partecipanteNonCreatore);
//		appuntamenti = this.escludiAppuntamentiPerUtenteCreatore(appuntamenti, partecipanteNonCreatore);
//
//		return appuntamenti;
//	}

	private List<Appuntamento> filtraAppuntamentiPerDataOra(List<Appuntamento> listaAppuntamentiDaFiltrare,
			LocalDateTime dataOra) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (appuntamento.getDataOraInizioAppuntamento().isAfter(dataOra)) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

	private List<Appuntamento> filtraAppuntamentiPerTipoPrenotazione(List<Appuntamento> listaAppuntamentiDaFiltrare,
			String tipoPrenotazione) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (appuntamento.getTipoPrenotazione().equals(tipoPrenotazione)) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

	private List<Appuntamento> filtraAppuntamentiPending(List<Appuntamento> listaAppuntamentiDaFiltrare) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (appuntamento.isPending()) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

//	private List<Appuntamento> filtraAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare,
//			UtentePolisportiva utenteCreatore) {
//		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
//		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
//			if (((String) appuntamento.creatoDa().getProprieta().get("email"))
//					.equals((String) utenteCreatore.getProprieta().get("email"))) {
//				appuntamentiFiltrati.add(appuntamento);
//			}
//		}
//		return appuntamentiFiltrati;
//	}

	public List<Appuntamento> escludiAppuntamentiDiCorsi(List<Appuntamento> listaAppuntamentiDaFiltrare) {
		List<Appuntamento> appuntamentiNonCorsi = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (!appuntamento.appartieneA().equals(TipiPrenotazione.CORSO.toString())) {
				appuntamentiNonCorsi.add(appuntamento);
			}
		}

		return appuntamentiNonCorsi;
	}

	public List<Appuntamento> filtraAppuntamentiDiCorsi(List<Appuntamento> listaAppuntamentiDaFiltrare) {
		List<Appuntamento> appuntamentiCorsi = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (appuntamento.appartieneA().equals(TipiPrenotazione.CORSO.toString())) {
				appuntamentiCorsi.add(appuntamento);
			}
		}

		return appuntamentiCorsi;
	}

//	private List<Appuntamento> escludiAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare,
//			UtentePolisportiva utenteCreatore) {
//		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
//		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
//			if (!appuntamento.creatoDa().isEqual(utenteCreatore)) {
//				appuntamentiFiltrati.add(appuntamento);
//			}
//		}
//		return appuntamentiFiltrati;
//	}

	private List<Appuntamento> escludiAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare,
			UtentePolisportiva utenteDiCuiVerificarePartecipazione) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (!appuntamento.utenteIsPartecipante(utenteDiCuiVerificarePartecipazione)) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

	private List<Appuntamento> escludiAppuntamentiPerSquadraPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare,
			Squadra squadraDiCuiVerificarePartecipazione) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			for (Object squadra : appuntamento.getPartecipantiAppuntamento()) {
				Squadra partecipante = (Squadra) squadra;
				if (!partecipante.isEqual(squadraDiCuiVerificarePartecipazione)) {
					appuntamentiFiltrati.add(appuntamento);
				}
			}
		}
		return appuntamentiFiltrati;
	}

	public List<Appuntamento> filtraAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare,
			UtentePolisportiva utenteDiCuiVerificarePartecipazione) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (appuntamento.utenteIsPartecipante(utenteDiCuiVerificarePartecipazione)) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

	public Appuntamento getAppuntamentoById(Integer idAppuntamento) {
		for (Appuntamento appuntamento : this.getListaAppuntamenti()) {
			if (appuntamento.getIdAppuntamento() == idAppuntamento) {
				return appuntamento;
			}
		}
		return null;
	}

	public List<Prenotazione> getPrenotazioniAssociateAListaAppuntamenti(List<Appuntamento> listaAppuntamenti) {
		Set<Prenotazione> setPrenotazioni = new HashSet<Prenotazione>();
		listaAppuntamenti.forEach((appuntamento) -> setPrenotazioni
				.add(getRegistroPrenotazioni().trovaPrenotazioneAssociataA(appuntamento)));
		List<Prenotazione> prenotazioniList = new ArrayList<Prenotazione>(setPrenotazioni);

		return prenotazioniList;
	}

	public List<Appuntamento> getAppuntamentiPerPartecipante(UtentePolisportiva partecipante) {
		List<Appuntamento> appuntamenti = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : getListaAppuntamenti()) {
			if (appuntamento.utenteIsPartecipante(partecipante)) {
				appuntamenti.add(appuntamento);
			}
		}
		return appuntamenti;
	}

	private List<Appuntamento> filtraLezioniPerIstruttore(List<AppuntamentoLezione> listaLezioniDaFiltrare,
			UtentePolisportiva istruttore) {

		List<AppuntamentoLezione> listaLezioniIstruttore = new ArrayList<AppuntamentoLezione>();
		for (AppuntamentoLezione appuntamento : listaLezioniDaFiltrare) {
			if (appuntamento.isNelCalendarioDi(istruttore)) {
				listaLezioniIstruttore.add(appuntamento);
			}
		}

		return (List<Appuntamento>) (List<?>) listaLezioniIstruttore;
	}

	public List<Appuntamento> getListaLezioniPerIstruttore(UtentePolisportiva istruttore) {
		List<Appuntamento> listaLezioni = filtraAppuntamentiPerTipoPrenotazione(getListaAppuntamenti(),
				TipiPrenotazione.LEZIONE.toString());

		listaLezioni = filtraLezioniPerIstruttore((List<AppuntamentoLezione>) (List<?>) listaLezioni, istruttore);

		return listaLezioni;
	}

	public List<Appuntamento> getListaAppuntamentiManutentore(UtentePolisportiva manutentore) {
		List<Appuntamento> appuntamentiList = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : getListaAppuntamenti()) {
			if (appuntamento.getManutentore().isEqual(manutentore)) {
				appuntamentiList.add(appuntamento);
			}
		}
		return appuntamentiList;
	}

	private List<Appuntamento> filtraAppuntamentiModalitaSquadraPerSquadraPartecipante(
			List<Appuntamento> listaAppuntamentiModaitaSquadraDaFiltrare, Squadra squadraPartecipante) {
		List<Appuntamento> listaFiltrata = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiModaitaSquadraDaFiltrare) {
			for (Object squadra : appuntamento.getPartecipantiAppuntamento()) {
				Squadra squadraIterator = (Squadra) squadra;
				if (squadraIterator.isEqual(squadraPartecipante)) {
					listaFiltrata.add(appuntamento);
				}
			}

		}
		return listaFiltrata;

	}

	private List<Appuntamento> filtraAppuntamentiPerModalitaPrenotazione(List<Appuntamento> listaAppuntamentiDaFiltrare,
			String modalitaPrenotazione) {
		List<Appuntamento> listaFiltrata = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (appuntamento.getModalitaPrenotazione().equals(modalitaPrenotazione)) {
				listaFiltrata.add(appuntamento);
			}
		}
		return listaFiltrata;
	}

	public List<Appuntamento> getAppuntamentiPerSquadraPartecipante(Squadra squadraPartecipante) {
		List<Appuntamento> listaAppuntamentiSquadra = filtraAppuntamentiPerModalitaPrenotazione(getListaAppuntamenti(),
				ModalitaPrenotazione.SQUADRA.toString());
		listaAppuntamentiSquadra = filtraAppuntamentiModalitaSquadraPerSquadraPartecipante(listaAppuntamentiSquadra,
				squadraPartecipante);

		return listaAppuntamentiSquadra;
	}

	public void creaQuotePartecipazionePerAppuntamento(Appuntamento appuntamento) {
		if (appuntamento.getPartecipantiAppuntamento().size() >= appuntamento
				.getSogliaMinimaPartecipantiPerConferma()) {
			appuntamento.confermaAppuntamento();
			appuntamento.getUtentiPartecipanti().forEach((partecipante) -> appuntamento
					.aggiungiQuotaPartecipazione(this.creaQuotaPartecipazione(appuntamento, partecipante)));
			aggiornaAppuntamento(appuntamento);
		}
		
	}

	public List<QuotaPartecipazione> creaQuotePartecipazionePerCorso(Appuntamento appuntamento) {
		List<QuotaPartecipazione> listaQuote = new ArrayList<QuotaPartecipazione>();
		if (appuntamento.getPartecipantiAppuntamento().size() >= appuntamento
				.getSogliaMinimaPartecipantiPerConferma()) {
			appuntamento.getUtentiPartecipanti().forEach(
					(partecipante) -> listaQuote.add(this.creaQuotaPartecipazione(appuntamento, partecipante)));
		}
		return listaQuote;
	}

	private QuotaPartecipazione creaQuotaPartecipazione(Appuntamento appuntamento, UtentePolisportiva sportivo) {
		boolean quotaGiaPresente = false;
		for (QuotaPartecipazione quotaPartecipazione : appuntamento.getQuotePartecipazione()) {
			if (quotaPartecipazione.getSportivoAssociato().isEqual(sportivo)) {
				quotaGiaPresente = true;
			}
		}
		if (!quotaGiaPresente) {
			QuotaPartecipazione quota = new QuotaPartecipazione();
			quota.setCosto(appuntamento.getCalcolatoreCosto().calcolaQuotaPartecipazione(appuntamento));
			quota.setSportivoAssociato(sportivo);
			quota.setPagata(false);
			return quota;
		}
		return null;
	}
}
