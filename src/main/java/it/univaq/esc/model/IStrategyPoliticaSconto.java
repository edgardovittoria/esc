package it.univaq.esc.model;

import javax.persistence.MappedSuperclass;


public interface IStrategyPoliticaSconto {

	public Sconto calcolaSconto(Prenotazione prenotazione);
	public boolean isApplicabile(Prenotazione prenotazione);
}
