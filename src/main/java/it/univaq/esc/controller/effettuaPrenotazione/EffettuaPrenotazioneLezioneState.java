package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.FormPrenotaLezioneDTO;
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
import it.univaq.esc.model.prenotazioni.AppuntamentoLezione;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;

import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
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
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller) {
		FormPrenotaLezioneDTO formLezione = (FormPrenotaLezioneDTO) formDati;

		for (OrarioAppuntamento orario : formLezione.getOrariSelezionati()) {

			// ---------------------------------------------------------------------------------------

			AppuntamentoLezione appuntamento = (AppuntamentoLezione) getElementiPrenotazioneFactory()
					.getAppuntamento(TipiPrenotazione.LEZIONE.toString());
			impostaValoriAppuntamento(formDati, controller, appuntamento, orario);

			controller.getPrenotazioneInAtto().aggiungi(appuntamento);
		}

		PrenotazioneDTO prenDTO = getMapperFactory().getPrenotazioneMapper()
				.convertiInPrenotazioneDTO(controller.getPrenotazioneInAtto());

		return prenDTO;

	}

	public void impostaValoriAppuntamento(FormPrenotabile formDati, EffettuaPrenotazioneHandler controller,
			AppuntamentoSingoliPartecipanti appuntamento, OrarioAppuntamento orario) {

		PrenotabileDescrizione descrizioneSpecifica = getCatalogoPrenotabili()
				.getPrenotabileDescrizioneByTipoPrenotazioneESportEModalitaPrenotazione(
						controller.getTipoPrenotazioneInAtto(),
						getRegistroSport().getSportByNome(formDati.getSportSelezionato()),
						formDati.getModalitaPrenotazione());
		// Creazione calcolatore che poi dovrà finire altrove
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());

		appuntamento.setDescrizioneEventoPrenotato(descrizioneSpecifica);
		appuntamento.setCalcolatoreCosto(calcolatoreCosto);

		LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
		LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

		appuntamento.setDataOraInizioAppuntamento(dataInizio);
		appuntamento.setDataOraFineAppuntamento(dataFine);

		appuntamento.calcolaCosto();

		this.aggiungiPartecipanteECreaQuotePartecipazione(descrizioneSpecifica, appuntamento);

	}

	/**
	 * Metodo che aggiorna eventuali oggetti correlati con la prenotazione che si
	 * sta gestendo, u na volta che questa è stata confermata.
	 */
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandler controller) {
		for (AppuntamentoLezione app : (List<AppuntamentoLezione>) (List<?>) controller.getPrenotazioneInAtto()
				.getListaAppuntamenti()) {
			Calendario calendarioDaUnire = new Calendario();
			calendarioDaUnire.aggiungiAppuntamento(app);
			getRegistroImpianti().aggiornaCalendarioImpianto(app.getImpiantoPrenotato(), calendarioDaUnire);
			getRegistroUtenti().aggiornaCalendarioIstruttore(calendarioDaUnire, app.getIstruttore());

			getRegistroUtenti().aggiornaCalendarioSportivo(calendarioDaUnire, controller.getSportivoPrenotante());
		}

	}

	/**
	 * Metodo che aggiorna i dati per popolare le opzioni di prenotazione, nel caso
	 * della prenotazione di una Lezione.
	 */
	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> mappaDatiAggiornati = new HashMap<String, Object>();
		mappaDatiAggiornati.put("impiantiDisponibili", this.getImpiantiPrenotabiliInBaseA(dati));
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
