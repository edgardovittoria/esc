package it.univaq.esc;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.univaq.esc.model.utenti.RegistroSquadre;
import it.univaq.esc.model.utenti.Squadra;
import lombok.Getter;

@RunWith(SpringRunner.class)
@SpringBootTest
@Getter
public class TestRegistroSquadre {

	@Autowired
	private RegistroSquadre registroSquadre;
	
	
	@Test
	public void testPopolamento() {
		for(Squadra squadra : getRegistroSquadre().getListaSquadre()) {
			assertEquals(1, squadra.getAmministratori().size());
		}
	}
}
