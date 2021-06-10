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

import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "Squadra")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AppuntamentoSquadra extends Appuntamento {

	@ManyToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Squadra> squadrePartecipanti = new ArrayList<Squadra>();

	public AppuntamentoSquadra(LocalDateTime dataOraInizioAppuntamento, LocalDateTime dataOraFineAppuntamento,
			PrenotazioneSpecs specificaPrenotazione) {
		setDataOraInizioAppuntamento(dataOraInizioAppuntamento);
		setDataOraFineAppuntamento(dataOraFineAppuntamento);
		setPrenotazioneSpecsAppuntamento(specificaPrenotazione);
	}

	@Override
	public void aggiungiPartecipante(Object sportivoOSquadraPartecipante) {
		Squadra partecipante = (Squadra) sportivoOSquadraPartecipante;
		if (!this.squadraIsPartecipante(partecipante)) {
			this.getSquadrePartecipanti().add(partecipante);
		}

	}

	@Override
	public List<Object> getPartecipantiAppuntamento() {
		List<Object> partecipanti = new ArrayList<Object>();
		getSquadrePartecipanti().forEach((partecipante) -> partecipanti.add(partecipante));

		return partecipanti;
	}

	private boolean squadraIsPartecipante(Squadra squadraDaVerificare) {
		for (Squadra partecipante : getSquadrePartecipanti()) {
			if (partecipante.isEqual(squadraDaVerificare)) {
				return true;
			}
		}
		return false;
	}

	public void aggiungiUtentePartecipante(UtentePolisportivaAbstract nuovoPartecipante) {
		if (!utenteIsPartecipante(nuovoPartecipante)) {
			for (Squadra squadraPartecipante : getSquadrePartecipanti()) {
				if (squadraPartecipante.isMembro(nuovoPartecipante)) {
					getUtentiPartecipanti().add(nuovoPartecipante);
				}
			}
		}
	}

}
