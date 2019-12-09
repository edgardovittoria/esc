package it.univaq.esc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cartaCredito")
public class CartaCredito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDCartaCredito;

	@ManyToOne
	private Sportivo proprietario;

	public CartaCredito(int iDCartaCredito) {
		
	}
	
	
	
	

    /**
     * @return int return the IDCartaCredito
     */
    public int getIDCartaCredito() {
        return IDCartaCredito;
    }

    /**
     * @param IDCartaCredito the IDCartaCredito to set
     */
    public void setIDCartaCredito(int IDCartaCredito) {
        this.IDCartaCredito = IDCartaCredito;
    }

    /**
     * @return Sportivo return the proprietario
     */
    public Sportivo getProprietario() {
        return proprietario;
    }

    /**
     * @param proprietario the proprietario to set
     */
    public void setProprietario(Sportivo proprietario) {
        this.proprietario = proprietario;
    }

}
