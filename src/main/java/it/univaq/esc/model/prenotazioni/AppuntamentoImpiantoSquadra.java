package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.utenti.Squadra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter @Setter
@NoArgsConstructor
public class AppuntamentoImpiantoSquadra extends AppuntamentoSquadra{

	

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Squadra> squadreInvitate = new ArrayList<Squadra>();
	
	public void aggiungi(Squadra squadraInvitata) {
		getSquadreInvitate().add(squadraInvitata);
	}
}
