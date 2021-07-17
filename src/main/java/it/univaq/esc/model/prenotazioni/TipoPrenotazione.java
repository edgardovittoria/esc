package it.univaq.esc.model.prenotazioni;

public enum TipoPrenotazione {
    
    IMPIANTO,
    LEZIONE,
    CORSO;
	
	public boolean isEqual(TipoPrenotazione tipoPrenotazioneDaConfrontare) {
		if(tipoPrenotazioneDaConfrontare.toString().equals(this.toString())) {
			return true;
		}
		return false;
	}
}
