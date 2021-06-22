package it.univaq.esc.model.prenotazioni;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
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

	public void aggiungiUtentePartecipante(UtentePolisportivaAbstract nuovoPartecipante) {
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

}
