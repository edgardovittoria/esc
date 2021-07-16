package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.model.utenti.Squadra;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public abstract class AppuntamentoSquadra extends Appuntamento {

	
	@ManyToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Squadra> squadrePartecipanti = new ArrayList<Squadra>();
	
	@ManyToMany()
	@JoinColumn()
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Squadra> squadreInvitate = new ArrayList<Squadra>();
	

	@Override
	public boolean aggiungiPartecipante(Object sportivoOSquadraPartecipante) {
		Squadra squadraDaAggiungereComePartecipante = (Squadra) sportivoOSquadraPartecipante;
		if (!this.haComePartecipante(squadraDaAggiungereComePartecipante) && this.accettaNuoviPartecipantiOltre(getPartecipantiAppuntamento().size())) {
			this.getSquadrePartecipanti().add(squadraDaAggiungereComePartecipante);
			return true;
		}
		return false;

	}

	@Override
	public List<Object> getPartecipantiAppuntamento() {
		List<Object> squadrePartecipanti = new ArrayList<Object>();
		getSquadrePartecipanti().forEach((squadraPartecipante) -> squadrePartecipanti.add(squadraPartecipante));

		return squadrePartecipanti;
	}

//	private boolean squadraIsPartecipante(Squadra squadraDaVerificare) {
//		for (Squadra partecipante : getSquadrePartecipanti()) {
//			if (partecipante.isEqual(squadraDaVerificare)) {
//				return true;
//			}
//		}
//		return false;
//	}

	public void aggiungiUtentePartecipante(UtentePolisportiva nuovoPartecipante) {
		if (!utenteIsPartecipante(nuovoPartecipante)) {
			for (Squadra squadraPartecipante : getSquadrePartecipanti()) {
				if (squadraPartecipante.isMembro(nuovoPartecipante)) {
					getUtentiPartecipanti().add(nuovoPartecipante);
				}
			}
		}
	}

	@Override
	public boolean haComePartecipante(Object squadra) {
		Squadra squadraDaVerificare = (Squadra) squadra;
		for (Squadra partecipante : getSquadrePartecipanti()) {
			if (partecipante.isEqual(squadraDaVerificare)) {
				return true;
			}
		}
		return false;
	}
	
	public void aggiungi(Squadra squadraAgliInvitati) {
		getSquadreInvitate().add(squadraAgliInvitati);
	}
	
	public void siAggiungeAlCalendarioDella(Squadra squadraPartecipante) {
		squadraPartecipante.segnaInCalendarioIl(this);
	}

}
