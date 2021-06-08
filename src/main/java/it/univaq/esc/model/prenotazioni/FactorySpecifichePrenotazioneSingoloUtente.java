package it.univaq.esc.model.prenotazioni;

import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.dtoObjects.PrenotazioneCorsoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneImpiantoSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneLezioneSpecsDTO;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;

@Component
@Singleton
public class FactorySpecifichePrenotazioneSingoloUtente extends FactorySpecifichePrenotazione{

	public FactorySpecifichePrenotazioneSingoloUtente() {
	}

	@Override
	public PrenotazioneSpecs getSpecifichePrenotazione(String tipoPrenotazione) {
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

	@Override
	public PrenotazioneSpecsDTO getSpecifichePrenotazioneDTO(String tipoPrenotazione) {
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
