package it.univaq.esc.model.prenotazioni;






import java.time.LocalDateTime;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "Singolo_Utente")
@NoArgsConstructor
public class AppuntamentoSingoliPartecipanti extends Appuntamento {

	
	public AppuntamentoSingoliPartecipanti(LocalDateTime dataOraInizioAppuntamento,
			LocalDateTime dataOraFineAppuntamento, PrenotazioneSpecs specificaPrenotazione) {
		setDataOraInizioAppuntamento(dataOraInizioAppuntamento);
		setDataOraFineAppuntamento(dataOraFineAppuntamento);
		setPrenotazioneSpecsAppuntamento(specificaPrenotazione);
	}

	@Override
	public boolean aggiungiPartecipante(Object sportivoPartecipante) {
		UtentePolisportivaAbstract partecipante = (UtentePolisportivaAbstract) sportivoPartecipante;
		if (!this.utenteIsPartecipante(partecipante) && getPartecipantiAppuntamento().size() < getNumeroPartecipantiMassimo()) {
			this.getUtentiPartecipanti().add(partecipante);
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

	

}