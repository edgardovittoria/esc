package it.univaq.Model;

public class Sconto {

	private int IDSconto;
	private boolean usato;
	private IStrategyPoliticaSconto politicaSconto;
	
	public Sconto() {
		super();
	}

	public boolean isUsato() {
		return usato;
	}

	public void setUsato(boolean usato) {
		this.usato = usato;
	}

	public void setPoliticaSconto(IStrategyPoliticaSconto politicaSconto) {
		this.politicaSconto = politicaSconto;
	}
	
	
	
	
	
	
}
