package it.univaq.esc;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

import java.security.KeyStore.PrivateKeyEntry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import it.univaq.esc.model.prenotazioni.Appuntamento;
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
public class TestRicercaAppuntamentiSottoscrivibili {
	
	private List<Appuntamento> appuntamenti;
	
	private UtentePolisportivaAbstract userProva;

	@Mock
	private AppuntamentoRepository appuntamentoRepository;

	
	@InjectMocks
	private RegistroAppuntamenti registroAppuntamenti;
	
	@Before
	public void init() {
		UtentePolisportivaAbstract utenteProva = mock(UtentePolisportiva.class);
		UtentePolisportivaAbstract user1 = mock(UtentePolisportiva.class);
		when(user1.isEqual(utenteProva)).thenReturn(true);
		UtentePolisportivaAbstract user2 = mock(UtentePolisportiva.class);
		when(user2.isEqual(utenteProva)).thenReturn(false);
		
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
		when(appuntamento1.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 7, 23, 15, 30));
		
		Appuntamento appuntamento3 = mock(Appuntamento.class);
		when(appuntamento3.isPending()).thenReturn(true);
		when(appuntamento3.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento3.creatoDa()).thenReturn(user1);
		when(appuntamento3.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento1.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 8, 20, 15, 30));
		
		Appuntamento appuntamento4 = mock(Appuntamento.class);
		when(appuntamento4.isPending()).thenReturn(true);
		when(appuntamento4.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento4.creatoDa()).thenReturn(user2);
		when(appuntamento4.utenteIsPartecipante(utenteProva)).thenReturn(false);
		when(appuntamento1.getDataOraInizioAppuntamento()).thenReturn(LocalDateTime.of(2021, 6, 20, 15, 30));
		
		List<Appuntamento> appuntamentiProva = new ArrayList<Appuntamento>();
		appuntamentiProva.add(appuntamento1);
		appuntamentiProva.add(appuntamento2);
		appuntamentiProva.add(appuntamento3);
		appuntamentiProva.add(appuntamento4);
		
		
		
		setAppuntamenti(appuntamentiProva);
		setUserProva(utenteProva);
			
		
		
		//getRegistroAppuntamenti().setListaAppuntamenti(appuntamentiProva);
		
		when(getAppuntamentoRepository().findAll()).thenReturn(getAppuntamenti());
	}
	
	
	
	@Test
	public void testQuattroAppuntamentiDueSottoscrivibili() {
	
				
		assertEquals(4, getAppuntamenti().size());
//		List<Appuntamento> appuntamentiSottoscrivibili = getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliPerTipo(TipiPrenotazione.IMPIANTO.toString(), getUserProva());
//		
//		assertEquals(2, appuntamentiSottoscrivibili.size());
	}
}
