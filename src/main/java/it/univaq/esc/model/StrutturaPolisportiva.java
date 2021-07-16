package it.univaq.esc.model;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.model.prenotazioni.Notificabile;
import it.univaq.esc.model.prenotazioni.OrarioAppuntamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Getter
@Setter
public abstract class StrutturaPolisportiva extends Notificabile {

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
		return getIdNotificabile() == idStruttura;
	}

	public boolean isStrutturaLiberaNell(OrarioAppuntamento orario) {
		return !getCalendarioAppuntamentiStruttura().sovrapponeA(orario);
	}

	@Override
	public String getTipoEventoNotificabile() {
		return TipoEventoNotificabile.CREAZIONE_STRUTTURA.toString();
	}

	public abstract String getTipoStruttura();
}
