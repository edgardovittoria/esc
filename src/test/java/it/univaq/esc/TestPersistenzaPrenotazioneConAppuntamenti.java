package it.univaq.esc;

import it.univaq.esc.model.prenotazioni.*;
import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Getter
@Setter
@NoArgsConstructor
@Component
public class TestPersistenzaPrenotazioneConAppuntamenti {

	@Autowired
	private RegistroAppuntamenti registroAppuntamenti;
	@Autowired
	private RegistroPrenotazioni registroPrenotazioni;
	@Autowired
	private AppuntamentoRepository appuntamentoRepository;

	
	@Test
	public void test() {
		Prenotazione prenotazione = new Prenotazione();
		Appuntamento appuntamento = new AppuntamentoImpianto();
		prenotazione.aggiungi(appuntamento);
		
		System.out.println("id appuntamento prima del salvataggio: " + appuntamento.getIdNotificabile());
		
		getRegistroAppuntamenti().salva(appuntamento);
		
		//Inserire un assert da verificare.
		
	}
}
