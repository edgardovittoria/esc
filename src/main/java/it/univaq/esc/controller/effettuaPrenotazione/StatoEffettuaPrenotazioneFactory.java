package it.univaq.esc.controller.effettuaPrenotazione;


public abstract class StatoEffettuaPrenotazioneFactory {

	public abstract EffettuaPrenotazioneState getStato(String tipoPrenotazione);

}
