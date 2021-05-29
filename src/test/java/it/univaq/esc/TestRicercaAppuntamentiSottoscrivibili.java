package it.univaq.esc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.AppuntamentoRepository;

@RunWith(MockitoJUnitRunner.class)
public class TestRicercaAppuntamentiSottoscrivibili {

	@Mock
	private AppuntamentoRepository appuntamentoRepository;
	
	@InjectMocks
	private RegistroAppuntamenti registroAppuntamenti;
	
	
	public void testQuattroAppuntamentiDueSottoscrivibili() {
		UtentePolisportivaAbstract utenteProva = mock(UtentePolisportivaAbstract.class);
		UtentePolisportivaAbstract user1 = mock(UtentePolisportivaAbstract.class);
		when(user1.isEqual(utenteProva)).thenReturn(true);
		UtentePolisportivaAbstract user2 = mock(UtentePolisportivaAbstract.class);
		when(user2.isEqual(utenteProva)).thenReturn(false);
		
		Appuntamento appuntamento1 = mock(Appuntamento.class);
		when(appuntamento1.isPending()).thenReturn(true);
		when(appuntamento1.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento1.creatoDa()).thenReturn(user2);
		
		Appuntamento appuntamento2 = mock(Appuntamento.class);
		when(appuntamento2.isPending()).thenReturn(false);
		when(appuntamento2.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento2.creatoDa()).thenReturn(user2);
		
		Appuntamento appuntamento3 = mock(Appuntamento.class);
		when(appuntamento3.isPending()).thenReturn(true);
		when(appuntamento3.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento3.creatoDa()).thenReturn(user1);
		
		Appuntamento appuntamento4 = mock(Appuntamento.class);
		when(appuntamento4.isPending()).thenReturn(true);
		when(appuntamento4.getTipoPrenotazione()).thenReturn(TipiPrenotazione.IMPIANTO.toString());
		when(appuntamento4.creatoDa()).thenReturn(user2);
	}
}
