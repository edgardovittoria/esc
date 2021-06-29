package it.univaq.esc;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.AppuntamentoImpianto;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.RegistroAppuntamenti;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.repository.AppuntamentoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
		Prenotazione prenotazione = new Prenotazione(5);
		Appuntamento appuntamento = new AppuntamentoImpianto();
		prenotazione.aggiungi(appuntamento);
		
		System.out.println("id appuntamento prima del salvataggio: " + appuntamento.getIdAppuntamento());
		
		getRegistroAppuntamenti().salva(appuntamento);
		
		//Inserire un assert da verificare.
		
	}
}
