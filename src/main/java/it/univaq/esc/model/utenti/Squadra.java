package it.univaq.esc.model.utenti;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Squadra {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idSquadra;

	@Column
	private String nome;

	@ManyToOne
	@JoinColumn
	private Sport sport;

	@ManyToMany
	@JoinColumn
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportiva> membri = new ArrayList<UtentePolisportiva>();

	@ManyToMany
	@JoinColumn
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportiva> amministratori = new ArrayList<UtentePolisportiva>();
	
	@Transient
	private Calendario calendarioSquadra = new Calendario(); 
	
	

	public Integer aggiungiMembro(UtentePolisportiva nuovoMembro) {
		if (!isMembro(nuovoMembro)) {
			getMembri().add(nuovoMembro);
		}
		return getMembri().size();
	}

	public void aggiungiAmministratore(UtentePolisportiva nuovoAmministratore) {
		if (!isAmministratore(nuovoAmministratore)) {
			getAmministratori().add(nuovoAmministratore);
		}
		aggiungiMembro(nuovoAmministratore);
	}

	public Integer rimuoviMembro(UtentePolisportiva membroCheRimuove, UtentePolisportiva membroDaRimuovere) {
		if(isAmministratore(membroCheRimuove) || membroCheRimuove.isEqual(membroDaRimuovere)) {
		getMembri().remove(membroDaRimuovere);
		}
		return getMembri().size();
	}

	public void rimuoviAmministratore(UtentePolisportiva amministratoreCheRimuove,
			UtentePolisportiva amministratoreDaRimuovere) {
		if (isAmministratore(amministratoreCheRimuove) && getAmministratori().size() > 1) {
			rimuoviMembro(amministratoreCheRimuove, amministratoreDaRimuovere);
			getAmministratori().remove(amministratoreDaRimuovere);
		}
	}

	public boolean isMembro(UtentePolisportiva nuovoMembro) {
		for (UtentePolisportiva membro : getMembri()) {
			if (membro.isEqual(nuovoMembro)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAmministratore(UtentePolisportiva nuovoAmministratore) {
		for (UtentePolisportiva amministratore : getAmministratori()) {
			if (amministratore.isEqual(nuovoAmministratore)) {
				return true;
			}
		}
		return false;
	}
	
	public Integer getNumeroMinimoMembri() {
		return getSport().getNumeroMinimoGiocatoriPerSquadra();
	}
	
	
	public boolean isEqual(Squadra squadraDaConfrontare) {
		if(squadraDaConfrontare.getIdSquadra() == this.getIdSquadra()) {
			return true;
		}
		return false;
	}
	
	public void segnaInCalendarioIl(Appuntamento nuovoAppuntamento) {
		getCalendarioSquadra().aggiungiAppuntamento(nuovoAppuntamento);
	}
	
}
