package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;

public abstract class FactorySpecifichePrenotazione {

	public abstract PrenotazioneSpecs getSpecifichePrenotazione(String tipoPrenotazione);
	
	public abstract PrenotazioneSpecsDTO getSpecifichePrenotazioneDTO(String tipoPrenotazione);
}
