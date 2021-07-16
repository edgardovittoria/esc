package it.univaq.esc;

import it.univaq.esc.controller.notifiche.DettagliNotificaStrategy;
import it.univaq.esc.controller.notifiche.FactoryDettagliNotificaStrategy;
import it.univaq.esc.model.TipoEventoNotificabile;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestVerificaFactoryDettagliNotifiche {

	@Autowired
	private FactoryDettagliNotificaStrategy factoryDettagliNotificaStrategy;
	
	
	@Test
	public void testCaricamentoStrategieInFactory() {
		DettagliNotificaStrategy strategy = factoryDettagliNotificaStrategy.getStrategy(TipoEventoNotificabile.PRENOTAZIONE.toString());
		assertThat(strategy != null);
	}
}
