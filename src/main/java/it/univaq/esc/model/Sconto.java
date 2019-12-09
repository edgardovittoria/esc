package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sconti")
public class Sconto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDSconto;
	@Column
	private boolean usato;
	@Transient
	private IStrategyPoliticaSconto politicaSconto;
	
	@ManyToOne
	private Sportivo possessore;

	public Sconto() {
	
	}

	public boolean isUsato() {
		return usato;
	}

	public void setUsato(boolean usato) {
		this.usato = usato;
	}

	public void setPoliticaSconto(IStrategyPoliticaSconto politicaSconto) {
		this.politicaSconto = politicaSconto;
	}
	

    /**
     * @return int return the IDSconto
     */
    public int getIDSconto() {
        return IDSconto;
    }

    /**
     * @param IDSconto the IDSconto to set
     */
    public void setIDSconto(int IDSconto) {
        this.IDSconto = IDSconto;
    }

    /**
     * @return IStrategyPoliticaSconto return the politicaSconto
     */
    public IStrategyPoliticaSconto getPoliticaSconto() {
        return politicaSconto;
    }

    /**
     * @return Sportivo return the possessore
     */
    public Sportivo getPossessore() {
        return possessore;
    }

    /**
     * @param possessore the possessore to set
     */
    public void setPossessore(Sportivo possessore) {
        this.possessore = possessore;
    }

}
