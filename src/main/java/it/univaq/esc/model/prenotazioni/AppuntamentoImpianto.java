package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class AppuntamentoImpianto extends AppuntamentoSingoliPartecipanti{
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportiva> invitati = new ArrayList<UtentePolisportiva>();
	
	@Column
	private Integer numeroGiocatoriNonIscrittiAssociati = 0;
	
	
	public List<String> getNominativiInvitati(){
		List<String> nominativiInvitati = new ArrayList<String>();
		getInvitati().forEach((inviato) -> nominativiInvitati.add(inviato.getNominativoCompleto()));
		
		return nominativiInvitati;
	}
	
	public void aggiungi(UtentePolisportiva invitato) {
		getInvitati().add(invitato);
	}
	
	@Override
	public void impostaDatiAppuntamentoDa(DatiFormPerAppuntamento datiCompilatiInPrenotazione) {
		super.impostaDatiAppuntamentoDa(datiCompilatiInPrenotazione);
		setNumeroGiocatoriNonIscrittiAssociati(datiCompilatiInPrenotazione.getNumeroPartecipantiNonIscritti());
		setInvitati(datiCompilatiInPrenotazione.getInvitati());
	}
}
