package it.univaq.esc.model.prenotazioni;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class AppuntamentoLezione extends AppuntamentoSingoliPartecipanti{

	@ManyToOne
	@JoinColumn
	private UtentePolisportivaAbstract istruttore;
	
	public boolean isNelCalendarioDi(UtentePolisportivaAbstract istruttore) {
		return istruttore.isEqual(getIstruttore());
	}
}
