package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.catalogoECosti.CatalogoPrenotabili;
import it.univaq.esc.model.catalogoECosti.PrenotabileDescrizione;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.catalogoECosti.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.PrenotazioneLezioneSpecs;

import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

/**
 * Stato del controller EffettuaPrenotazioneHandler che gestisce la specifica
 * implementazione relativa al tipo di prenotazione LEZIONE. Fornisce la
 * specifica implementazione per ricavare i dati per popolare, aggiornare e
 * registrare le opzioni di compilazione nel caso della prenotazione di una
 * nuova Lezione, o della partecipazione ad una già esistente.
 * 
 * @author esc
 *
 */
@Component
public class EffettuaPrenotazioneLezioneState extends EffettuaPrenotazioneState {

	public EffettuaPrenotazioneLezioneState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {

		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti,
				registroPrenotazioni, catalogoPrenotabili, registroSquadre);
	}

	/**
	 * Metodo che restituisce i dati per popolare le opzioni di prenotazione, in
	 * fase di avvio di una nuova prenotazione, nel caso di una Lezione.
	 */
	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandler controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());

		return mappaValori;
	}

	/**
	 * Metodo che imposta i dati della prenotazione, passati tramite una form DTO,
	 * nella controparte software lato server.
	 */
	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati,
			EffettuaPrenotazioneHandler controller) {

		PrenotabileDescrizione descrizioneSpecifica = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						controller.getTipoPrenotazioneInAtto(),
						getRegistroSport().getSportByNome((String) formDati.getValoriForm().get("sport")),
						formDati.getModalitaPrenotazione());

		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {
			PrenotazioneLezioneSpecs prenotazioneSpecs = new PrenotazioneLezioneSpecs();
			controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);
			impostaValoriPrenotazioniSpecs(formDati, prenotazioneSpecs, descrizioneSpecifica, controller, orario);

			// ---------------------------------------------------------------------------------------

			AppuntamentoSingoliPartecipanti appuntamento = new AppuntamentoSingoliPartecipanti();
			impostaValoriAppuntamento(formDati, controller, appuntamento, prenotazioneSpecs, orario);

			this.aggiungiPartecipante(controller.getPrenotazioneInAtto().getSportivoPrenotante(), appuntamento);

			controller.aggiungiAppuntamento(appuntamento);
		}

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper().convertiInPrenotazioneDTO(
				controller.getPrenotazioneInAtto(), controller.getListaAppuntamentiPrenotazioneInAtto());

		return prenDTO;

	}

	public void impostaValoriPrenotazioniSpecs(FormPrenotabile formDati, PrenotazioneLezioneSpecs prenotazioneSpecs,
			PrenotabileDescrizione descrizioneSpecifica, EffettuaPrenotazioneHandler controller,
			OrarioAppuntamento orario) {

		prenotazioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());

		prenotazioneSpecs.setSpecificaDescription(descrizioneSpecifica);

		Integer idImpianto = 0;
		for (ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>) formDati.getValoriForm().get("impianti")) {
			if (impianto.getIdSelezione() == orario.getId()) {
				idImpianto = impianto.getIdImpianto();
			}
		}
		prenotazioneSpecs.setImpiantoPrenotato(getRegistroImpianti().getImpiantoByID(idImpianto));

		String emailIstruttore = "";
		for (IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>) formDati.getValoriForm()
				.get("istruttori")) {
			if (istruttore.getIdSelezione() == orario.getId()) {
				emailIstruttore = istruttore.getIstruttore();
			}
		}
		prenotazioneSpecs.setIstruttore(getRegistroUtenti().getUtenteByEmail(emailIstruttore));

	}

	public void impostaValoriAppuntamento(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller,
			AppuntamentoSingoliPartecipanti appuntamento, PrenotazioneLezioneSpecs prenotazioneSpecs,
			OrarioAppuntamento orario) {
		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
		LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

		appuntamento.setDataOraInizioAppuntamento(dataInizio);
		appuntamento.setDataOraFineAppuntamento(dataFine);

		appuntamento.calcolaCosto();

	}

	/**
	 * Metodo che aggiorna eventuali oggetti correlati con la prenotazione che si
	 * sta gestendo, u na volta che questa è stata confermata.
	 */
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		for (Appuntamento app : controller.getListaAppuntamentiPrenotazioneInAtto()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto((Impianto) controller.getPrenotazioneInAtto()
					.getSingolaSpecificaExtra("impianto", app.getPrenotazioneSpecsAppuntamento()), calendarioDaUnire);
			getRegistroUtenti().aggiornaCalendarioIstruttore(calendarioDaUnire,
					(UtentePolisportivaAbstract) controller.getPrenotazioneInAtto()
							.getSingolaSpecificaExtra("istruttore", app.getPrenotazioneSpecsAppuntamento()));
		}

	}

	/**
	 * Metodo che aggiorna i dati per popolare le opzioni di prenotazione, nel caso
	 * della prenotazione di una Lezione.
	 */
	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> mappaDatiAggiornati = new HashMap<String, Object>();
		mappaDatiAggiornati.put("impiantiDisponibili", this.getImpiantiDTODisponibili(dati));
		mappaDatiAggiornati.put("istruttoriDisponibili", this.getIstruttoriDTODisponibili(dati));
		return mappaDatiAggiornati;
	}

	/**
	 * Metodo che gestisce la partecipazione di un utente ad una lezione esistente.
	 */
	@Override
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, Object identificativoPartecipante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandler controller) {
		// TODO Auto-generated method stub
		return null;
	}

}
