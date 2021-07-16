package it.univaq.esc.model.utenti;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProfiloManutentore extends ProfiloUtente{

	@Transient
	private Calendario calendarioManutentore = new Calendario();
	
	@Override
	public boolean isProfilo(TipoRuolo ruolo) {
		if(ruolo == TipoRuolo.MANUTENTORE) {
			return true;
		}
		return false;
	}

	@Override
	public TipoRuolo getRuoloRelativo() {
		return TipoRuolo.MANUTENTORE;
	}

	
	public void segnaInAgendaGliAppuntamentiDel(Calendario nuovoCalendarioManutenzioni) {
		getCalendarioManutentore().unisciCalendario(nuovoCalendarioManutenzioni);
	}
	
	public void segnaInAgendaLaLista(List<Appuntamento> appuntamentiManutentore) {
		appuntamentiManutentore.forEach((appuntamento) -> getCalendarioManutentore().aggiungiAppuntamento(appuntamento));
	}
	
	public void segnaInAgendaIl(Appuntamento nuovoAppuntamentoManutentore) {
		getCalendarioManutentore().aggiungiAppuntamento(nuovoAppuntamentoManutentore);
	}
	
	public boolean isLiberoNegliOrariDel(Calendario calendario) {
		return !getCalendarioManutentore().sovrapponeA(calendario);
	}
	
	public boolean isLiberoPer(Appuntamento nuovoAppuntamento) {
		return !getCalendarioManutentore().sovrapponeA(nuovoAppuntamento);
	}
	
	public List<Appuntamento> getListaAppuntamenti(){
		return getCalendarioManutentore().getListaAppuntamenti();
	}
}
