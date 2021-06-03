package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import it.univaq.esc.dtoObjects.IFormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.FactorySpecifichePrenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

/**
 * Stato del controller EffettuaPrenotazioneHandler che gestisce la specifica implementazione relativa al tipo di prenotazione LEZIONE.
 * Fornisce la specifica implementazione per ricavare i dati per popolare, aggiornare e registrare le opzioni di compilazione nel
 * caso della prenotazione di una nuova Lezione, o della partecipazione ad una già esistente.
 * @author esc
 *
 */
@Component
public class EffettuaPrenotazioneLezioneState extends EffettuaPrenotazioneState {

	public EffettuaPrenotazioneLezioneState(
			RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtentiPolisportiva,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili) {
		
		super(registroNotifiche, registroSport, registroImpianti, registroUtentiPolisportiva, registroAppuntamenti, registroPrenotazioni, catalogoPrenotabili);
	}

	/**
	 * Blocco statico che la prima volta che si carica la classe, la registra nella FactoryStatoEffettuaPrenotazione
	 */
	static {
		FactoryStatoEffettuaPrenotazione.registra(TipiPrenotazione.LEZIONE.toString(),
				EffettuaPrenotazioneLezioneState.class);
	}

	/**
	 * Metodo che restituisce i dati per popolare le opzioni di prenotazione, in fase di avvio di una nuova prenotazione, nel 
	 * caso di una Lezione.
	 */
	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());

		return mappaValori;
	}

	/**
	 * Metodo che imposta i dati della prenotazione, passati tramite una form DTO, nella controparte software lato server.
	 */
	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(IFormPrenotabile formDati, EffettuaPrenotazioneHandlerRest controller) {
		for (int i = 0; i < ((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
				.size(); i++) {
			PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione
					.getSpecifichePrenotazione(controller.getTipoPrenotazioneInAtto());
			controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);
			prenotazioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());

			// Creazione calcolatore che poi dovrà finire altrove
			CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
			calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
			// ---------------------------------------------------------------------------------------

			Appuntamento appuntamento = new Appuntamento();
			appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
			appuntamento.setCalcolatoreCosto(calcolatoreCosto);

			controller.aggiungiAppuntamento(appuntamento);
		}

//		PrenotabileDescrizione descrizioneSpecifica = null;
//		for (PrenotabileDescrizione desc : controller.getListinoPrezziDescrizioniPolisportiva()
//				.getCatalogoPrenotabili()) {
//			if (desc.getSportAssociato().getNome().equals((String) formDati.getValoriForm().get("sport"))
//					&& desc.getTipoPrenotazione().equals(controller.getPrenotazioneInAtto()
//							.getListaSpecifichePrenotazione().get(0).getTipoPrenotazione())) {
//				descrizioneSpecifica = desc;
//			}
//		}
//
//		for (PrenotazioneSpecs spec : controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione()) {
//			spec.setSpecificaDescription(descrizioneSpecifica);
//		}
//
//		List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
//		for (IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>) formDati.getValoriForm()
//				.get("istruttori")) {
//			istruttori.add(getRegistroUtenti().getUtenteByEmail(istruttore.getIstruttore()));
//		}
//
//		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
//				.get("listaOrariAppuntamenti")) {
//			// Calendario calendarioPrenotazione = new Calendario();
//			LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
//			LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());
//
//			// calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine ,
//			// controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
//			// controller.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione,
//			// controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
//
//			controller.getListaAppuntamentiPrenotazioneInAtto().get(
//					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
//					.setDataOraInizioAppuntamento(dataInizio);
//			controller.getListaAppuntamentiPrenotazioneInAtto().get(
//					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
//					.setDataOraFineAppuntamento(dataFine);
//
//			HashMap<String, Object> mappaValori = new HashMap<String, Object>();
//
//			Integer idImpianto = 0;
//			for (ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>) formDati.getValoriForm().get("impianti")) {
//				if (impianto.getIdSelezione() == orario.getId()) {
//					idImpianto = impianto.getIdImpianto();
//				}
//			}
//
//			mappaValori.put("impianto", getRegistroImpianti().getImpiantoByID(idImpianto));
//
//			String emailIstruttore = "";
//			for (IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>) formDati.getValoriForm()
//					.get("istruttori")) {
//				if (istruttore.getIdSelezione() == orario.getId()) {
//					emailIstruttore = istruttore.getIstruttore();
//				}
//			}
//			mappaValori.put("istruttore", getRegistroUtenti().getUtenteByEmail(emailIstruttore));
//
//			controller.getListaAppuntamentiPrenotazioneInAtto()
//					.get(((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
//							.indexOf(orario))
//					.getPrenotazioneSpecsAppuntamento().impostaValoriSpecificheExtraPrenotazione(mappaValori);
//			controller.getListaAppuntamentiPrenotazioneInAtto().get(
//					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
//					.calcolaCosto();
//		}
		
		PrenotabileDescrizione descrizioneSpecifica = null;
		for (PrenotabileDescrizione desc : this.getCatalogoPrenotabili()
				.getCatalogoPrenotabili()) {
			if (desc.getSportAssociato().getNome().equals((String) formDati.getValoriForm().get("sport"))
					&& desc.getTipoPrenotazione().equals(controller.getTipoPrenotazioneInAtto())) {
				descrizioneSpecifica = desc;
			}
		}
		
		this.impostaValoriPrenotazioniSpecs(formDati, descrizioneSpecifica, controller.getTipoPrenotazioneInAtto(), controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione(), controller);

		for (Appuntamento appuntamento : controller.getListaAppuntamentiPrenotazioneInAtto()) {
			this.aggiungiPartecipante(controller.getPrenotazioneInAtto().getSportivoPrenotante(), appuntamento);
		}
		
		PrenotazioneDTO prenDTO = new PrenotazioneDTO();
        Map<String, Object> mappa = new HashMap<String, Object>();
        mappa.put("prenotazione", controller.getPrenotazioneInAtto());
        mappa.put("appuntamentiPrenotazione", controller.getListaAppuntamentiPrenotazioneInAtto());
        prenDTO.impostaValoriDTO(mappa);
        
        return prenDTO;

	}
	
	public void impostaValoriPrenotazioniSpecs(IFormPrenotabile formDati, PrenotabileDescrizione descrizioneSpecifica, String tipoPrenotazione, List<PrenotazioneSpecs> listaSpecifiche, EffettuaPrenotazioneHandlerRest controller) {

		for (PrenotazioneSpecs spec : listaSpecifiche) {
			spec.setSpecificaDescription(descrizioneSpecifica);
		}

		List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
		for (IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>) formDati.getValoriForm()
				.get("istruttori")) {
			istruttori.add(getRegistroUtenti().getUtenteByEmail(istruttore.getIstruttore()));
		}

		for (OrarioAppuntamento orario : (List<OrarioAppuntamento>) formDati.getValoriForm()
				.get("listaOrariAppuntamenti")) {
			// Calendario calendarioPrenotazione = new Calendario();
			LocalDateTime dataInizio = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraInizio());
			LocalDateTime dataFine = LocalDateTime.of(orario.getDataPrenotazione(), orario.getOraFine());

			// calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine ,
			// controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
			// controller.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione,
			// controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

			controller.getListaAppuntamentiPrenotazioneInAtto().get(
					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
					.setDataOraInizioAppuntamento(dataInizio);
			controller.getListaAppuntamentiPrenotazioneInAtto().get(
					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
					.setDataOraFineAppuntamento(dataFine);

			HashMap<String, Object> mappaValori = new HashMap<String, Object>();

			Integer idImpianto = 0;
			for (ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>) formDati.getValoriForm().get("impianti")) {
				if (impianto.getIdSelezione() == orario.getId()) {
					idImpianto = impianto.getIdImpianto();
				}
			}

			mappaValori.put("impianto", getRegistroImpianti().getImpiantoByID(idImpianto));

			String emailIstruttore = "";
			for (IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>) formDati.getValoriForm()
					.get("istruttori")) {
				if (istruttore.getIdSelezione() == orario.getId()) {
					emailIstruttore = istruttore.getIstruttore();
				}
			}
			mappaValori.put("istruttore", getRegistroUtenti().getUtenteByEmail(emailIstruttore));

			controller.getListaAppuntamentiPrenotazioneInAtto()
					.get(((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti"))
							.indexOf(orario))
					.getPrenotazioneSpecsAppuntamento().impostaValoriSpecificheExtraPrenotazione(mappaValori);
			controller.getListaAppuntamentiPrenotazioneInAtto().get(
					((List<OrarioAppuntamento>) formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
					.calcolaCosto();
		}
	}

	
	/**
	 * Metodo che aggiorna eventuali oggetti correlati con la prenotazione che si sta gestendo, u
	 * na volta che questa è stata confermata.
	 */
	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
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
	 * Metodo che aggiorna i dati per popolare le opzioni di prenotazione, nel caso della prenotazione di una Lezione.
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
	public Object aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getDatiOpzioniModalitaDirettore(EffettuaPrenotazioneHandlerRest controller) {
		// TODO Auto-generated method stub
		return null;
	}

}
