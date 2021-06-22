package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class AppuntamentoCorso extends AppuntamentoSingoliPartecipanti {
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportivaAbstract> invitati = new ArrayList<UtentePolisportivaAbstract>();
	
	@ManyToOne
	@JoinColumn
	private UtentePolisportivaAbstract istruttore;
	
	
	public void aggiungiInvitato(UtentePolisportivaAbstract invitato) {
		getInvitati().add(invitato);
	}
	
}


