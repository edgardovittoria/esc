package it.univaq.esc.model.prenotazioni;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import it.univaq.esc.model.utenti.Squadra;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PUBLIC) @Setter(value = AccessLevel.PUBLIC)
@NoArgsConstructor
public class PrenotazioneSquadra extends Prenotazione{

	@ManyToOne
	@JoinColumn
	private Squadra squadraPrenotante;
	
	
	
}
