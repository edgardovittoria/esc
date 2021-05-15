package it.univaq.esc.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.controller.effettuaPrenotazione.FactoryStatoEffettuaPrenotazione;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;

@Component
public class TestFactoryStatoEffettuaPrenotazione {
	
	@Autowired
	FactoryStatoEffettuaPrenotazione factoryStatoEffettuaPrenotazione;

	public TestFactoryStatoEffettuaPrenotazione() {}
	
	public void test() {
		System.out.println("ISTANZE DEGLI STATI REGISTRATE NELLA FactoryStatoEffettuaPrenotazione");
		System.out.println(factoryStatoEffettuaPrenotazione.getStato(TipiPrenotazione.IMPIANTO.toString()));
		System.out.println(factoryStatoEffettuaPrenotazione.getStato(TipiPrenotazione.LEZIONE.toString()));
	}
}
