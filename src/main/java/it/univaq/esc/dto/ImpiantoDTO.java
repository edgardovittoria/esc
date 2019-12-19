package it.univaq.esc.dto;

import java.util.Calendar;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.IPavimentazione;

@Component
public class ImpiantoDTO {

	private boolean indoor;
	private Money costoImpianto;
	private Calendar calendario;
	private IPavimentazione pavimentazione;
	
	
	public ImpiantoDTO() {}
	
	public boolean isIndoor() {
		return indoor;
	}
	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}
	public Money getCostoImpianto() {
		return costoImpianto;
	}
	public void setCostoImpianto(Money costoImpianto) {
		this.costoImpianto = costoImpianto;
	}
	public Calendar getCalendario() {
		return calendario;
	}
	public void setCalendario(Calendar calendario) {
		this.calendario = calendario;
	}
	public IPavimentazione getPavimentazione() {
		return pavimentazione;
	}
	public void setPavimentazione(IPavimentazione pavimentazione) {
		this.pavimentazione = pavimentazione;
	}
	
	
	
}
