package it.univaq.Model;

public interface IStrategyPoliticaSconto {

	public Sconto calcolaSconto(Prenotazione prenotazione);
	public boolean isApplicabile(Prenotazione prenotazione);
}
