package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
@NoArgsConstructor
public class AppuntamentoLezione extends AppuntamentoSingoliPartecipanti{

	

	@ManyToOne
	@JoinColumn
	private UtentePolisportiva istruttore;
	
	public boolean isNelCalendarioDi(UtentePolisportiva istruttore) {
		return istruttore.isEqual(getIstruttore());
	}
	
	public String getNominativoIstruttore() {
		return getIstruttore().getNominativoCompleto();
	}
	
	public void siAggiungeAlCalendarioDellIstruttoreRelativo() {
		getIstruttore().comeIstruttore().segnaInAgendaIl(this);
	}
	
	@Override
	public void impostaDatiAppuntamentoDa(DatiFormPerAppuntamento datiCompilatiInPrenotazione) {
		super.impostaDatiAppuntamentoDa(datiCompilatiInPrenotazione);
		setIstruttore(datiCompilatiInPrenotazione.getIstruttore());
	}
	
	public boolean haComeIstruttore(UtentePolisportiva istruttore) {
		if(getIstruttore().isEqual(istruttore)) {
			return true;
		}
		return false;
	}
}
