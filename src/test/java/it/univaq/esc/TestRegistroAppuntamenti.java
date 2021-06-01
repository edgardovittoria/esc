package it.univaq.esc;


import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.mapping.MappableAttributesRetriever;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.PrenotazioneLezioneSpecs;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@RunWith(MockitoJUnitRunner.class)
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)

public class TestRegistroAppuntamenti {

	private List<Appuntamento> appuntamenti;

	private UtentePolisportivaAbstract userProva;

	@Mock
	private AppuntamentoRepository appuntamentoRepository;

	@InjectMocks
	private RegistroAppuntamenti registroAppuntamenti;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		UtentePolisportivaAbstract utenteProva = mock(UtentePolisportiva.class);
		UtentePolisportivaAbstract user1 = mock(UtentePolisportiva.class);
		when(user1.isEqual(utenteProva)).thenReturn(true);
		UtentePolisportivaAbstract user2 = mock(UtentePolisportiva.class);
		when(user2.isEqual(utenteProva)).thenReturn(false);
		
		PrenotazioneSpecs lezionePrenotazioneSpecs = mock(PrenotazioneLezioneSpecs.class);
		when(lezionePrenotazioneSpecs.getValoriSpecificheExtraPrenotazione()).thenReturn(new HashMap<String, Object>());

		Appuntamento appuntamento1 = mock(Appuntamento.class);
		when(appuntamento1.isPending()).thenReturn(true);
		when(appuntamento1.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento1.creatoDa()).thenReturn(user2);
		when(appuntamento1.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento1.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 7, 20, 15, 30));
		

		Appuntamento appuntamento2 = mock(Appuntamento.class);
		when(appuntamento2.isPending()).thenReturn(false);
		when(appuntamento2.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento2.creatoDa()).thenReturn(user2);
		when(appuntamento2.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento2.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 7, 23, 15, 30));
		

		Appuntamento appuntamento3 = mock(Appuntamento.class);
		when(appuntamento3.isPending()).thenReturn(true);
		when(appuntamento3.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento3.creatoDa()).thenReturn(user1);
		when(appuntamento3.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento3.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 8, 20, 15, 30));
		

		Appuntamento appuntamento4 = mock(Appuntamento.class);
		when(appuntamento4.isPending()).thenReturn(true);
		when(appuntamento4.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento4.creatoDa()).thenReturn(user2);
		when(appuntamento4.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento4.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 6, 20, 15, 30));
		
		Appuntamento appuntamento5 = mock(Appuntamento.class);
		when(appuntamento5.isPending()).thenReturn(false);
		when(appuntamento5.getTipoPrenotazione()).thenReturn(TipiPrenotazione.LEZIONE.toString());
		when(appuntamento5.creatoDa()).thenReturn(user2);
		when(appuntamento5.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento5.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 6, 20, 15, 30));
		when(appuntamento5.getPrenotazioneSpecsAppuntamento()).thenReturn(lezionePrenotazioneSpecs);
		when(appuntamento5.getPrenotazioneSpecsAppuntamento().getValoriSpecificheExtraPrenotazione().get("istruttore")).thenReturn(user1);
		
		Appuntamento appuntamento6 = mock(Appuntamento.class);
		when(appuntamento6.isPending()).thenReturn(false);
		when(appuntamento6.getTipoPrenotazione()).thenReturn(TipiPrenotazione.LEZIONE.toString());
		when(appuntamento6.creatoDa()).thenReturn(user2);
		when(appuntamento6.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento6.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 6, 20, 15, 30));
		when(appuntamento6.getPrenotazioneSpecsAppuntamento()).thenReturn(lezionePrenotazioneSpecs);
		when(appuntamento6.getPrenotazioneSpecsAppuntamento().getValoriSpecificheExtraPrenotazione().get("istruttore")).thenReturn(user2);
		
		Appuntamento appuntamento7 = mock(Appuntamento.class);
		when(appuntamento7.isPending()).thenReturn(false);
		when(appuntamento7.getTipoPrenotazione()).thenReturn(TipiPrenotazione.LEZIONE.toString());
		when(appuntamento7.creatoDa()).thenReturn(user2);
		when(appuntamento7.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento7.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 6, 20, 15, 30));
		when(appuntamento7.getPrenotazioneSpecsAppuntamento()).thenReturn(lezionePrenotazioneSpecs);
		when(appuntamento7.getPrenotazioneSpecsAppuntamento().getValoriSpecificheExtraPrenotazione().get("istruttore")).thenReturn(user1);
		
		Appuntamento appuntamento8 = mock(Appuntamento.class);
		when(appuntamento8.isPending()).thenReturn(false);
		when(appuntamento8.getTipoPrenotazione()).thenReturn(TipiPrenotazione.LEZIONE.toString());
		when(appuntamento8.creatoDa()).thenReturn(user2);
		when(appuntamento8.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento8.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 6, 20, 15, 30));
		when(appuntamento8.getPrenotazioneSpecsAppuntamento()).thenReturn(lezionePrenotazioneSpecs);
		when(appuntamento8.getPrenotazioneSpecsAppuntamento().getValoriSpecificheExtraPrenotazione().get("istruttore")).thenReturn(user2);
		

		List<Appuntamento> appuntamentiProva = new ArrayList<Appuntamento>();
		appuntamentiProva.add(appuntamento1);
		appuntamentiProva.add(appuntamento2);
		appuntamentiProva.add(appuntamento3);
		appuntamentiProva.add(appuntamento4);
		appuntamentiProva.add(appuntamento5);
		appuntamentiProva.add(appuntamento6);
		appuntamentiProva.add(appuntamento7);
		appuntamentiProva.add(appuntamento8);

		setAppuntamenti(appuntamentiProva);
		setUserProva(utenteProva);
		
		when(getAppuntamentoRepository().findAll()).thenReturn(getAppuntamenti());
		
		getRegistroAppuntamenti().popola();
	}

	@Test
	public void test_4AppuntamentiImpianto_2Sottoscrivibili() {
		
		List<Appuntamento> appuntamentiSottoscrivibili = getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliPerTipo(TipiPrenotazione.IMPIANTO.toString(), getUserProva());
		
		assertEquals(2, appuntamentiSottoscrivibili.size());
	}

	@Test
	public void testGetLezioniPerIistruttore_2LezioniSu4() {
		List<Appuntamento> lezioni = getRegistroAppuntamenti().getListaLezioniPerIstruttore(userProva);
		assertEquals(2, lezioni.size());
	}
}
