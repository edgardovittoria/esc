package it.univaq.esc.test;

import org.springframework.stereotype.Component;

import it.univaq.esc.model.notifiche.FactoryStatiNotifiche;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class TestFactoryStatiNotifiche {

	//private FactoryStatiNotifiche factoryStatiNotifiche;
	
	
	public void test() {
		System.out.println("ISTANZE DEGLI STATI REGISTRATE NELLA FactoryStatiNotifiche");
		System.out.println(FactoryStatiNotifiche.getStato(TipiPrenotazione.IMPIANTO.toString()));
		System.out.println(FactoryStatiNotifiche.getStato(TipiPrenotazione.CORSO.toString()));
	}
		
	
}
