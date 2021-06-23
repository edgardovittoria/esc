package it.univaq.esc.model.utenti;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.sun.xml.fastinfoset.algorithm.IntegerEncodingAlgorithm;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
}
