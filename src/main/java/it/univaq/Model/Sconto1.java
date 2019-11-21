package it.univaq.Model;

public class Sconto1 implements IStrategyPoliticaSconto{

	@Override
	public Sconto calcolaSconto(Prenotazione prenotazione) {
		return null;
	}

	@Override
	public boolean isApplicabile(Prenotazione prenotazione) {
		return false;
	}

}
