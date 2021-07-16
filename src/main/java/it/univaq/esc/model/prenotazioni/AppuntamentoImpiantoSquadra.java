package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.model.utenti.Squadra;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
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
	
	@Override
	public void impostaDatiAppuntamentoDa(DatiFormPerAppuntamento datiCompilatiInPrenotazione) {
		super.impostaDatiAppuntamentoDa(datiCompilatiInPrenotazione);
		setSquadreInvitate(datiCompilatiInPrenotazione.getSquadreInvitate());
	}
	
	public void siAggiungeAlCalendarioDellaSquadraPrenotante() {
		PrenotazioneSquadra prenotazioneSquadraAssociata = (PrenotazioneSquadra) getPrenotazione();
		prenotazioneSquadraAssociata.getSquadraPrenotante().segnaInCalendarioIl(this);
	}
}
