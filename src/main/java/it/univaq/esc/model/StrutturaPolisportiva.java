package it.univaq.esc.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Notificabile;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public abstract class StrutturaPolisportiva extends Notificabile{

	@Transient
	private Calendario calendarioAppuntamentiStruttura = new Calendario();

	public List<Appuntamento> getListaAppuntamenti() {
		return this.getCalendarioAppuntamentiStruttura().getListaAppuntamenti();
	}

	public void segnaInCalendarioIl(Appuntamento nuovoAppuntamento) {
		getCalendarioAppuntamentiStruttura().aggiungiAppuntamento(nuovoAppuntamento);
	}

	public void segnaInCalendarioGliAppuntamentiDel(Calendario nuovoCalendario) {
		getCalendarioAppuntamentiStruttura().unisciCalendario(nuovoCalendario);
	}

	public void segnaInCalendarioLaListaDi(List<Appuntamento> nuoviAppuntamenti) {
		getCalendarioAppuntamentiStruttura().aggiungiListaDi(nuoviAppuntamenti);
	}
	
	public boolean isEqual(StrutturaPolisportiva strutturaPolisportiva) {
		return (getIdNotificabile() == strutturaPolisportiva.getIdNotificabile());
	}
	
	public boolean isSuoQuesto(Integer idStruttura) {
		return getIdNotificabile()==idStruttura;
	}
	
	public boolean isStrutturaLiberaNell(OrarioAppuntamento orario) {
		return !getCalendarioAppuntamentiStruttura().sovrapponeA(orario);
	}
	
	@Override
	public String getTipoEventoNotificabile() {
		return TipoEventoNotificabile.CREAZIONE_STRUTTURA.toString();
	}
}
