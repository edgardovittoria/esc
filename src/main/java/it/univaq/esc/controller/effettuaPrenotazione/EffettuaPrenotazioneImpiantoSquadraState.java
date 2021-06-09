package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.FormPrenotabile;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSport;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.ModalitaPrenotazione;
import it.univaq.esc.model.notifiche.RegistroNotifiche;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

@Component
@DependsOn("beanUtil")
public class EffettuaPrenotazioneImpiantoSquadraState extends EffettuaPrenotazioneState{

	public EffettuaPrenotazioneImpiantoSquadraState(RegistroNotifiche registroNotifiche, RegistroSport registroSport,
			RegistroImpianti registroImpianti, RegistroUtentiPolisportiva registroUtenti,
			RegistroAppuntamenti registroAppuntamenti, RegistroPrenotazioni registroPrenotazioni,
			CatalogoPrenotabili catalogoPrenotabili, RegistroSquadre registroSquadre) {
		super(registroNotifiche, registroSport, registroImpianti, registroUtenti, registroAppuntamenti, registroPrenotazioni,
				catalogoPrenotabili, registroSquadre);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
		Map<String, Object> mappaValori = new HashMap<String, Object>();
		mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
		mappaValori.put("squadreInvitabili", getRegistroSquadre().getListaSquadre());
		mappaValori.put("appuntamentiSottoscrivibiliSquadra",
				this.getAppuntamentiImpiantoSottoscrivibiliDaSquadra(getRegistroSquadre().getSquadraById(controller.getIdSquadraPrenotante())));

		return mappaValori;
	}

	@Override
	public PrenotazioneDTO impostaDatiPrenotazione(FormPrenotabile formDati,
			EffettuaPrenotazioneHandlerRest controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
		Map<String, Object> datiAggiornati = new HashMap<String, Object>();
		datiAggiornati.put("impiantiDisponibili", this.getImpiantiDTODisponibili(dati));

		Map<String, String> orario = (Map<String, String>) dati.get("orario");
		LocalDateTime oraInizio = LocalDateTime.parse(orario.get("oraInizio"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
		LocalDateTime oraFine = LocalDateTime.parse(orario.get("oraFine"),
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));

		
			datiAggiornati.put("squadreInvitabili",
					getRegistroSquadre().getListaSquadreLiberePerOrarioAppuntamento(oraInizio, oraFine));
		

		return datiAggiornati;
	}

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
	
	
	private List<AppuntamentoDTO> getAppuntamentiImpiantoSottoscrivibiliDaSquadra(
			Squadra squadraPerCuiCercareAppuntamentiSottoscrivibili) {
		List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
		for (Appuntamento appuntamento : this.getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliSquadraPerTipo(
				TipiPrenotazione.IMPIANTO.toString(), squadraPerCuiCercareAppuntamentiSottoscrivibili)) {
			AppuntamentoDTO appDTO = new AppuntamentoDTO();
			appDTO.impostaValoriDTO(appuntamento);
			listaAppuntamentiDTO.add(appDTO);
		}
		return listaAppuntamentiDTO;
	}

}
