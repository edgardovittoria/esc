package it.univaq.esc.model.prenotazioni;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class AppuntamentoCorso extends AppuntamentoSingoliPartecipanti {
	
	

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UtentePolisportiva> invitati = new ArrayList<UtentePolisportiva>();
	
	@ManyToOne
	@JoinColumn
	private UtentePolisportiva istruttore;
	
	
	
	public List<String> getNominativiInvitati(){
		List<String> nominativiInvitati = new ArrayList<String>();
		getInvitati().forEach((invitato) -> nominativiInvitati.add(invitato.getNominativoCompleto()));
		
		return nominativiInvitati;
	}
	
	public String getNominativoIstruttore() {
		return getIstruttore().getNominativoCompleto();
	}
	
	public void aggiungi(UtentePolisportiva invitato) {
		getInvitati().add(invitato);
	}
	
	public void assegna(UtentePolisportiva istruttore) {
		setIstruttore(istruttore);
	}
	
	public void siAggiungeAlCalendarioDellIstruttoreRelativo() {
		getIstruttore().comeIstruttore().segnaInAgendaIl(this);
	}
	
	@Override
	public void impostaDatiAppuntamentoDa(DatiFormPerAppuntamento datiCompilatiInPrenotazione) {
		super.impostaDatiAppuntamentoDa(datiCompilatiInPrenotazione);
		setInvitati(datiCompilatiInPrenotazione.getInvitati());
		setIstruttore(datiCompilatiInPrenotazione.getIstruttore());
	}
	
	public void assegnaStesseQuotePartecipazioneAgliAltriAppuntamentiDelCorso() {
		List<Appuntamento> appuntamentiCorso = getPrenotazione().getListaAppuntamenti();
		for(Appuntamento appuntamento : appuntamentiCorso) {
			appuntamento.assegna(getQuotePartecipazione());
		}
	}
	
	public boolean haComeIstruttore(UtentePolisportiva istruttore) {
		if(getIstruttore().isEqual(istruttore)) {
			return true;
		}
		return false;
	}
}


