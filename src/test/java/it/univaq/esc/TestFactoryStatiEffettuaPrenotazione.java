package it.univaq.esc;

import static org.junit.Assert.assertEquals;

import org.aspectj.weaver.patterns.ISignaturePattern;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.univaq.esc.controller.effettuaPrenotazione.FactoryStatoEffettuaPrenotazione;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFactoryStatiEffettuaPrenotazione {

	@Autowired
	private FactoryStatoEffettuaPrenotazione factoryStatoEffettuaPrenotazione;
	
	
	@Test
	public void testGetStatiDaFactory() {
		
		boolean isPresent = false;
		if(factoryStatoEffettuaPrenotazione.getStato(TipiPrenotazione.IMPIANTO.toString()) != null) {
			isPresent = true;
		}
		assertEquals(true, isPresent);
		
		isPresent = false;
		if(factoryStatoEffettuaPrenotazione.getStato(TipiPrenotazione.LEZIONE.toString()) != null) {
			isPresent = true;
		}
		assertEquals(true, isPresent);
		
		isPresent = false;
		if(factoryStatoEffettuaPrenotazione.getStato(TipiPrenotazione.CORSO.toString()) != null) {
			isPresent = true;
		}
		assertEquals(true, isPresent);
	}
}
