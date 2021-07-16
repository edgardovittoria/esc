package it.univaq.esc.model.prenotazioni;


import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public abstract class AppuntamentoSingoliPartecipanti extends Appuntamento {

	
	

	


	@Override
	public boolean aggiungiPartecipante(Object sportivoPartecipante) {
		UtentePolisportiva sportivoDaAggiungereComePartecipante = (UtentePolisportiva) sportivoPartecipante;
		if (!this.haComePartecipante(sportivoDaAggiungereComePartecipante) && this.accettaNuoviPartecipantiOltre(getPartecipantiAppuntamento().size())) {
			this.getUtentiPartecipanti().add(sportivoDaAggiungereComePartecipante);
			return true;
		}
		return false;
	}


	@Override
	public List<Object> getPartecipantiAppuntamento() {
		List<Object> partecipanti = new ArrayList<Object>();
		getUtentiPartecipanti().forEach((partecipante) -> partecipanti.add(partecipante));
		
		return partecipanti;
	}

	@Override
	public boolean haComePartecipante(Object sportivo) {
		UtentePolisportiva partecipante = (UtentePolisportiva) sportivo;
		for (UtentePolisportiva utentePartecipante : this.getUtentiPartecipanti()) {
			if (utentePartecipante.isEqual(partecipante)) {
				return true;
			}
		}
		return false;
	}

	

}