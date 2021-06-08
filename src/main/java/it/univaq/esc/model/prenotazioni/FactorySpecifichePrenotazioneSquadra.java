package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;

public class FactorySpecifichePrenotazioneSquadra extends FactorySpecifichePrenotazione {

	@Override
	public PrenotazioneSpecs getSpecifichePrenotazione(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return new PrenotazioneImpiantoSquadraSpecs();

		case "LEZIONE":
			return null;
		case "CORSO":
			return null;
		}

	}

	@Override
	public PrenotazioneSpecsDTO getSpecifichePrenotazioneDTO(String tipoPrenotazione) {
		switch (tipoPrenotazione) {
		default:
			return null;

		case "IMPIANTO":
			return null;

		case "LEZIONE":
			return null;
		case "CORSO":
			return null;
		}
	}

}
