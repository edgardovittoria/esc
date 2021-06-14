package it.univaq.esc.model.prenotazioni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.event.InternalFrameAdapter;

import org.codehaus.groovy.runtime.callsite.AbstractCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.utility.FetchDatiPrenotazioniAppuntamentiFunctionsUtlis;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Setter
@Getter
public class RegistroAppuntamenti {

	@Setter(value = AccessLevel.PRIVATE)
	private AppuntamentoRepository appuntamentoRepository;

	@Setter(value = AccessLevel.PRIVATE)
	private FactoryAppuntamenti factoryAppuntamenti;

	// @Setter(value = AccessLevel.PRIVATE)
	private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();

	public RegistroAppuntamenti(AppuntamentoRepository appuntamentoRepository,
			FactoryAppuntamenti factoryAppuntamenti) {
		this.setAppuntamentoRepository(appuntamentoRepository);
		this.setFactoryAppuntamenti(factoryAppuntamenti);
		popola();
	}

	public void popola() {
		setListaAppuntamenti(getAppuntamentoRepository().findAll());
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
		this.getAppuntamentoRepository().saveAll(listaAppuntamenti);
	}

	public Appuntamento getAppuntamentoBySpecificaAssociata(PrenotazioneSpecs prenotazioneSpecs) {
		return this.getAppuntamentoRepository().findByPrenotazioneSpecsAppuntamento_Id(prenotazioneSpecs.getId());
	}

	public List<Appuntamento> getAppuntamentiSottoscrivibiliSingoloUtentePerTipo(String tipoPrenotazione,
			UtentePolisportivaAbstract utentePerCuiTrovareAppuntamenti) {
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
		appuntamentiFiltrati = this.escludiAppuntamentiPerSquadraPartecipante(appuntamentiFiltrati, squadraPerCuiTrovareAppuntamenti);
		
		return appuntamentiFiltrati;
	}

	public List<Appuntamento> getAppuntamentiPerPartecipanteNonCreatore(
			UtentePolisportivaAbstract partecipanteNonCreatore) {
		List<Appuntamento> appuntamenti = this.getListaAppuntamenti();
		appuntamenti = this.filtraAppuntamentiPerPartecipante(appuntamenti, partecipanteNonCreatore);
		appuntamenti = this.escludiAppuntamentiPerUtenteCreatore(appuntamenti, partecipanteNonCreatore);

		return appuntamenti;
	}

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

	private List<Appuntamento> filtraAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare,
			UtentePolisportivaAbstract utenteCreatore) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (((String) appuntamento.creatoDa().getProprieta().get("email"))
					.equals((String) utenteCreatore.getProprieta().get("email"))) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

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

	private List<Appuntamento> escludiAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare,
			UtentePolisportivaAbstract utenteCreatore) {
		List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaAppuntamentiDaFiltrare) {
			if (!appuntamento.creatoDa().isEqual(utenteCreatore)) {
				appuntamentiFiltrati.add(appuntamento);
			}
		}
		return appuntamentiFiltrati;
	}

	private List<Appuntamento> escludiAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare,
			UtentePolisportivaAbstract utenteDiCuiVerificarePartecipazione) {
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
			UtentePolisportivaAbstract utenteDiCuiVerificarePartecipazione) {
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

	public List<Appuntamento> getAppuntamentiByPrenotazioneId(Integer idPrenotazione) {
		List<Appuntamento> appuntamenti = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : this.getListaAppuntamenti()) {
			if (appuntamento.getIdPrenotazione() == idPrenotazione) {
				appuntamenti.add(appuntamento);
			}
		}
		return appuntamenti;
	}

	public List<Prenotazione> getPrenotazioniAssociateAListaAppuntamenti(List<Appuntamento> listaAppuntamenti) {
		return FetchDatiPrenotazioniAppuntamentiFunctionsUtlis
				.getPrenotazioniAssociateAListaAppuntamenti(listaAppuntamenti);
	}

	public List<Appuntamento> getAppuntamentiPerPartecipante(UtentePolisportivaAbstract partecipante) {
		List<Appuntamento> appuntamenti = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : getListaAppuntamenti()) {
			if (appuntamento.utenteIsPartecipante(partecipante)) {
				appuntamenti.add(appuntamento);
			}
		}
		return appuntamenti;
	}

	private List<Appuntamento> filtraLezioniPerIstruttore(List<Appuntamento> listaLezioniDaFiltrare,
			UtentePolisportivaAbstract istruttore) {
		List<Appuntamento> listaLezioniIstruttore = new ArrayList<Appuntamento>();
		for (Appuntamento appuntamento : listaLezioniDaFiltrare) {
			if (((UtentePolisportivaAbstract) appuntamento.getPrenotazioneSpecsAppuntamento()
					.getValoriSpecificheExtraPrenotazione().get("istruttore")).isEqual(istruttore)) {
				listaLezioniIstruttore.add(appuntamento);
			}
		}
		return listaLezioniIstruttore;
	}

	public List<Appuntamento> getListaLezioniPerIstruttore(UtentePolisportivaAbstract istruttore) {
		List<Appuntamento> listaLezioni = filtraAppuntamentiPerTipoPrenotazione(getListaAppuntamenti(),
				TipiPrenotazione.LEZIONE.toString());
		listaLezioni = filtraLezioniPerIstruttore(listaLezioni, istruttore);

		return listaLezioni;
	}

	public List<Appuntamento> getListaAppuntamentiManutentore(UtentePolisportivaAbstract manutentore) {
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

	public Appuntamento creaNuovoAppuntamentoPerModalitaPrenotazione(String modalitaPrenotazione) {
		return getFactoryAppuntamenti().getAppuntamento(modalitaPrenotazione);
	}
}
