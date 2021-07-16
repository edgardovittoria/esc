package it.univaq.esc.model.catalogoECosti;

public enum ModalitaPrenotazione {

	SINGOLO_UTENTE, SQUADRA;
	
	public boolean isEqual(ModalitaPrenotazione modalitaDaVerificare) {
		if(this.toString().equals(modalitaDaVerificare.toString())) {
			return true;
		}
		return false;
	}
}
