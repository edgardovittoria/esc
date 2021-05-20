package it.univaq.esc.model.prenotazioni;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.PrenotazioneCorsoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneLezioneSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.model.costi.PrenotazioneLezioneSpecs;

@Component
public class FactorySpecifichePrenotazione {

	public FactorySpecifichePrenotazione() {
	}

	public static PrenotazioneSpecs getSpecifichePrenotazione(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return new PrenotazioneImpiantoSpecs();

		case "LEZIONE":
			return new PrenotazioneLezioneSpecs();
		case "CORSO":
			return new PrenotazioneCorsoSpecs();

		}
	}

	public static PrenotazioneSpecsDTO getSpecifichePrenotazioneDTO(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return new PrenotazioneImpiantoSpecsDTO();

		case "LEZIONE":
			return new PrenotazioneLezioneSpecsDTO();

		case "CORSO":
			return new PrenotazioneCorsoSpecsDTO();

		}
	}

}
