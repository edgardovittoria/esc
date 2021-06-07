package it.univaq.esc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Any;

import it.univaq.esc.controller.effettuaPrenotazione.EffettuaPrenotazioneImpiantoState;
import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Pavimentazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.costi.CatalogoPrenotabili;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.PrenotabileDescrizioneBuilder;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoSingoliPartecipanti;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneImpiantoSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@RunWith(MockitoJUnitRunner.class)
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
public class TestCreazioneQuotePartecipazione {

	@Spy
	private AppuntamentoSingoliPartecipanti appuntamento;
	
	private Integer idEvento = 0;
	
	@Mock
	private RegistroAppuntamenti RegistroAppuntamenti;
	
	@Mock
	private RegistroUtentiPolisportiva registroUtenti;
	
	
	private UtentePolisportivaAbstract sportivo;
	
	
	private EffettuaPrenotazioneImpiantoState effettuaPrenotazioneImpiantoState;
		
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		/*
		 * Inizializziamo l'appuntamento
		 */
		PrenotabileDescrizione descrizione = (new PrenotabileDescrizioneBuilder()).creaNuovaDescrizione()
				.impostaTipoPrenotazione(TipiPrenotazione.IMPIANTO.toString())
				.impostaSport(new Sport())
				.impostaCostoUnaTantum(Float.parseFloat("5"))
				.impostaSogliaMassimaPartecipanti(30)
				.impostaSogliaMinimaPartecipanti(3)
				.build();
		Impianto impianto = mock(Impianto.class);
		Prenotazione prenotazione = mock(Prenotazione.class);
		
		PrenotazioneSpecs specifica = new PrenotazioneImpiantoSpecs();
		specifica.setSpecificaDescription(descrizione);
		Map<String, Object> mappaSpecifica = new HashMap<String, Object>();
		mappaSpecifica.put("impianto", impianto);
		specifica.impostaValoriSpecificheExtraPrenotazione(mappaSpecifica);
		specifica.setPrenotazioneAssociata(prenotazione);
		
		getAppuntamento().setPrenotazioneSpecsAppuntamento(specifica);
		LocalDateTime dataOraInizio = LocalDateTime.of(2021, 7, 21, 12, 30);
		LocalDateTime dataOraFine = LocalDateTime.of(2021, 7, 21, 14, 30);
		getAppuntamento().setDataOraInizioAppuntamento(dataOraInizio);
		getAppuntamento().setDataOraFineAppuntamento(dataOraFine);
		CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
		CalcolatoreCosto calcBase = mock(CalcolatoreCostoBase.class);
		when(calcBase.calcolaCosto(getAppuntamento())).thenReturn(Float.parseFloat("5"));
		calcolatoreCosto.aggiungiStrategiaCosto(calcBase);
		getAppuntamento().setCalcolatoreCosto(calcolatoreCosto);
		
		
		/*
		 * Creiamo un utente base di prova
		 */
		String emailSportivo = "sportivo@email.com";
		setSportivo(new UtentePolisportiva("pippo", "franco", emailSportivo, "password"));
		
		/*
		 * Creiamo l'oggetto con il metodo da testares
		 */
		effettuaPrenotazioneImpiantoState = new EffettuaPrenotazioneImpiantoState(null, null, null, getRegistroUtenti(), getRegistroAppuntamenti(), null, null, null);
		
		
		/*
		 * Impostiamo alcuni comportamenti necessari per i mock e gli spy creati.
		 */
		when(prenotazione.getSportivoPrenotante()).thenReturn(getSportivo());
		when(impianto.getIdImpianto()).thenReturn(2);
		when(impianto.getTipoPavimentazione()).thenReturn(Pavimentazione.TERRA_BATTUTA);
		when(getRegistroAppuntamenti().getAppuntamentoById(getIdEvento())).thenReturn(getAppuntamento());
		when(getRegistroUtenti().getUtenteByEmail(emailSportivo)).thenReturn(getSportivo());
		
		
	}
	
	@Test
	public void testAggiungiPartecipanteECreaQuotaPartecipazioneConQuotaSportivoGiaPresente() {
		when(getAppuntamento().getNumeroPartecipantiMassimo()).thenReturn(40);
		when(getAppuntamento().getSogliaMinimaPartecipantiPerConferma()).thenReturn(1);
		
		QuotaPartecipazione quotaProva = new QuotaPartecipazione();
		quotaProva.setSportivoAssociato(getSportivo());
		getAppuntamento().aggiungiPartecipante(getSportivo());
		getAppuntamento().aggiungiQuotaPartecipazione(quotaProva);
		
		String emailPartecipante = (String)getSportivo().getProprieta().get("email");
		
		Object appuntamentoDTO = getEffettuaPrenotazioneImpiantoState()
				.aggiungiPartecipanteAEventoEsistente(getIdEvento(), emailPartecipante);
	/*
	 * Verifichiamo che sia ritornato effettivamente un oggetto.	
	 */
	assertThat(appuntamentoDTO != null);
	
	/*
	 * Verifichiamo che nel caso esista già una quota di partecipazione associata allo sportivo che vogliamo aggiungere, non 
	 * ne venga creata un'altra nè venga aggiunto come partecipante un'altra volta.
	 */
	assertEquals(1, getAppuntamento().getQuotePartecipazione().size());
	assertEquals(1, getAppuntamento().getPartecipantiAppuntamento().size());
	}
}
