package it.univaq.esc.model.utenti;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import lombok.AccessLevel;
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
public class ProfiloSportivo extends ProfiloUtente {

	@ManyToMany()
	@JoinTable(name = "sport_praticati_sportivi", joinColumns = { @JoinColumn(name = "email") }, inverseJoinColumns = {
			@JoinColumn(name = "sport_praticato") })
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Sport> sportPraticatiDalloSportivo = new ArrayList<Sport>();

	@Column
	private boolean moroso = false;

	@Transient
	@Setter(value = AccessLevel.PRIVATE)
	private Calendario calendarioAppuntamenti = new Calendario();

	@Override
	public boolean isProfilo(TipoRuolo ruolo) {
		if (ruolo == TipoRuolo.SPORTIVO) {
			return true;
		}
		return false;
	}

	public ProfiloSportivo(List<Sport> listaSportPraticati) {
		setSportPraticatiDalloSportivo(listaSportPraticati);
	}

	@Override
	public TipoRuolo getRuoloRelativo() {
		return TipoRuolo.SPORTIVO;
	}

	public void segnaInAgendaGliAppuntamentiDel(Calendario nuovoCalendario) {
		getCalendarioAppuntamenti().unisciCalendario(nuovoCalendario);
	}

	public void segnaInAgendaLaLista(List<Appuntamento> appuntamenti) {
		appuntamenti.forEach((appuntamento) -> getCalendarioAppuntamenti().aggiungiAppuntamento(appuntamento));
	}

	public void segnaInAgendaIl(Appuntamento nuovoAppuntamento) {
		getCalendarioAppuntamenti().aggiungiAppuntamento(nuovoAppuntamento);
	}

	public boolean isLiberoNegliOrariDel(Calendario calendario) {
		return !getCalendarioAppuntamenti().sovrapponeA(calendario);
	}

	public boolean isLiberoNellOrarioDel(Appuntamento nuovoAppuntamento) {
		return !getCalendarioAppuntamenti().sovrapponeA(nuovoAppuntamento);
	}

	public boolean isLiberoIl(OrarioAppuntamento orarioAppuntamento) {
		return !getCalendarioAppuntamenti().sovrapponeA(orarioAppuntamento);
	}

	public List<String> getSportPraticati() {
		List<String> nomiSport = new ArrayList<String>();
		getSportPraticatiDalloSportivo().forEach((sport) -> nomiSport.add(sport.getNome()));

		return nomiSport;
	}

	public List<Appuntamento> getListaAppuntamenti() {
		return getCalendarioAppuntamenti().getListaAppuntamenti();

	}
}
