package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.costi.ModalitaPrenotazione;

public class PrenotazioneSpecsDTOFactory {

	public static PrenotazioneSpecsDTO getPrenotazioneSpecsDTO(String modalitaPrenotazione, String tipoPrenotazione) {
		if (modalitaPrenotazione.equals(ModalitaPrenotazione.SINGOLO_UTENTE.toString())) {
			switch (tipoPrenotazione) {
			case "IMPIANTO":
				return new PrenotazioneImpiantoSpecsDTO();
			case "LEZIONE":
				return new PrenotazioneLezioneSpecsDTO();
			case "CORSO":
				return new PrenotazioneCorsoSpecsDTO();
			default:
				return null;
			}
		} else {
			switch (tipoPrenotazione) {
			case "IMPIANTO":
				return new PrenotazioneImpiantoSquadraSpecsDTO();
			case "LEZIONE":
				return null;
			case "CORSO":
				return null;
			default:
				return null;
			}
		}
	}
}
