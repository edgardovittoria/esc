package it.univaq.esc.model;

import java.util.List;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.javamoney.moneta.Money;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "nome")
@Table(name = "impianti")
public abstract class Impianto implements IPrenotabile{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idImpianto;
	@Column
	protected boolean indoor;
	@Column
	protected Money costoImpianto;
	@Column
	protected Calendar calendario;
	@Transient
	protected IPavimentazione pavimentazione;
	@ManyToMany
	protected List<Sport> sportPraticabili;
	
	public int getIdImpianto() {
		return idImpianto;
	}
	public void setIdImpianto(int idImpianto) {
		this.idImpianto = idImpianto;
	}
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
	public List<Sport> getSportPraticabili() {
		return sportPraticabili;
	}
	public void setSportPraticabili(List<Sport> sportPraticabili) {
		this.sportPraticabili = sportPraticabili;
	}
	
	@Override
	public boolean confermaPrenotazione(List<Object> parametri) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
