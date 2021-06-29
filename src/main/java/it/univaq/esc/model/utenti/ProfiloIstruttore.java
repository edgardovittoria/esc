package it.univaq.esc.model.utenti;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProfiloIstruttore extends ProfiloUtente{

	@ManyToMany
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Sport> sportInsegnati = new ArrayList<Sport>();

    @Transient
    @Setter(value = AccessLevel.PRIVATE)
    private Calendario calendarioLezioni = new Calendario();

	
	
    public ProfiloIstruttore(List<Sport> listaSportInsegnati) {
    	setSportInsegnati(listaSportInsegnati);
    }
	
	@Override
	public boolean isProfilo(TipoRuolo ruolo) {
		if(ruolo == TipoRuolo.ISTRUTTORE) {
			return true;
		}
		return false;
	}
	
	public boolean haInCalendario(Appuntamento appuntamento) {
		return getCalendarioLezioni().ha(appuntamento);
	}

	@Override
	public TipoRuolo getRuoloRelativo() {
		return TipoRuolo.ISTRUTTORE;
	}

	public void segnaInAgendaLeLezioniDel(Calendario nuovoCalendarioLezioni) {
		getCalendarioLezioni().unisciCalendario(nuovoCalendarioLezioni);
	}
	
	public void segnaInAgendaIl(Appuntamento nuovoAppuntamentoLezione) {
		getCalendarioLezioni().aggiungiAppuntamento(nuovoAppuntamentoLezione);
	}
	
	public boolean insegna(Sport sport) {
		for(Sport sportInsegnato : getSportInsegnati()) {
			if(sportInsegnato.isEqual(sport)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isLiberoNelleDateDel(Calendario calendario) {
		return !getCalendarioLezioni().sovrapponeA(calendario);
	}
	
	public boolean isLiberoNel(OrarioAppuntamento orarioAppuntamento) {
		return !getCalendarioLezioni().sovrapponeA(orarioAppuntamento);
	}
	
	public List<String> getNomiSportInsegnati(){
		List<String> nomiSport = new ArrayList<String>();
		getSportInsegnati().forEach((sport) -> nomiSport.add(sport.getNome()));
		
		return nomiSport;
	}
	
	public List<Appuntamento> getLezioni(){
		return getCalendarioLezioni().getListaAppuntamenti();
	}
}

