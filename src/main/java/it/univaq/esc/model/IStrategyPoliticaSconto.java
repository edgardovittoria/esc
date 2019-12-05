package it.univaq.esc.model;


public interface IStrategyPoliticaSconto {

	public Sconto calcolaSconto(Prenotazione prenotazione);
	public boolean isApplicabile(Prenotazione prenotazione);
}
