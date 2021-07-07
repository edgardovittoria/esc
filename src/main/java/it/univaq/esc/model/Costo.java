package it.univaq.esc.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter(value = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PRIVATE)
public class Costo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idCosto;

	@Column
	private Float costo = Float.parseFloat("0");

	@ManyToOne
	@JoinColumn
	private Valuta valuta;

	public Costo(Float costo, Valuta valuta) {
		setCosto(costo);
		setValuta(valuta);
	}
	
	public void imposta(Float costo, Valuta valuta) {
		setCosto(costo);
		setValuta(valuta);
	}

	public String getAmmontare() {
		return getCosto() + " " + getValuta().getValoreComeStringa();
	}

	public void sommaAlValoreIl(Costo ammontareAggiuntivo) {
		setCosto(getCosto() + (ammontareAggiuntivo.getCosto()));
	}
	
	public void sommaAlValoreIl(Float ammontareAggiuntivo) {
		setCosto(getCosto() + (ammontareAggiuntivo));
	}

	public void dividiIlValorePer(Integer numero) {
		setCosto(getCosto() / (numero));
	}

	public void dividiIlValorePer(Float numero) {
		setCosto(getCosto() / (numero));
	}
	
	public void dividiIlValorePer(Costo costoDaDividere) {
		setCosto(getCosto() / costoDaDividere.getCosto());
	}
	
	public void sottraiAlValoreIl(Costo ammontareDaSottrarre) {
		setCosto(getCosto() - (ammontareDaSottrarre.getCosto()));
	}
	
	public void sottraiAlValoreIl(Float ammontareDaSottrarre) {
		setCosto(getCosto() - (ammontareDaSottrarre));
	}

	public void moltiplicaIlValorePer(Integer numero) {
		setCosto(getCosto() * (numero));
	}
	
	public void moltiplicaIlValorePer(Long numero) {
		setCosto(getCosto() * (numero));
	}
	
	public void moltiplicaIlValorePer(Float numero) {
		setCosto(getCosto() * (numero));
	}
	
	public void moltiplicaIlValorePer(Costo costoDaMoltiplicare) {
		setCosto(getCosto() * (costoDaMoltiplicare.getCosto()));
	}
	
	public boolean isMinoreDi(Costo costoDaConfrontare) {
		return getCosto()<costoDaConfrontare.getCosto();
	}
	
	public Costo sommaIl(Costo costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.sommaAlValoreIl(costoDaSommare);
		return costoRisultato;
	}
	
	private Costo creaCloneDi(Costo costo) {
		Costo nuovoCosto = new Costo();		
		nuovoCosto.imposta(getCosto(), getValuta());
		return nuovoCosto;
	}
	
	public Costo sottraiIl(Costo costoDaSottrarre) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.sottraiAlValoreIl(costoDaSottrarre);
		return costoRisultato;
	}
	
	public Costo moltiplicaPer(Costo costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.moltiplicaIlValorePer(costoDaSommare);
		return costoRisultato;
	}
	
	public Costo moltiplicaPer(Float costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.moltiplicaIlValorePer(costoDaSommare);
		return costoRisultato;
	}
	
	public Costo moltiplicaPer(Integer costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.moltiplicaIlValorePer(costoDaSommare);
		return costoRisultato;
	}
	
	public Costo moltiplicaPer(Long costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.moltiplicaIlValorePer(costoDaSommare);
		return costoRisultato;
	}
	
	public Costo dividiPer(Costo costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.dividiIlValorePer(costoDaSommare);
		return costoRisultato;
	}
	
	public Costo dividiPer(Float costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.dividiIlValorePer(costoDaSommare);
		return costoRisultato;
	}
	
	public Costo dividiPer(Integer costoDaSommare) {
		Costo costoRisultato = creaCloneDi(this);
		costoRisultato.dividiIlValorePer(costoDaSommare);
		return costoRisultato;
	}
}
