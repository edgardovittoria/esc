package it.univaq.esc.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SpecificaDesc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    protected String tipoSpecificaDesc;
    @Column
    protected float costo;

    
    public SpecificaDesc(){}

    /**
     * @return String return the tipoSpecificaDesc
     */
    public String getTipoSpecificaDesc() {
        return tipoSpecificaDesc;
    }

    /**
     * @param tipoSpecificaDesc the tipoSpecificaDesc to set
     */
    public abstract void setTipoSpecificaDesc();

    /**
     * @return float return the costo
     */
    public  abstract float getCosto();
    

    /**
     * @param costo the costo to set
     */
    public abstract void setCosto(float costo, String tipoSpecifica);


    public abstract void setTipoPrenotazione(String tipoPrenotazione);
    public abstract String getTipoPrenotazione();
    public abstract Sport getSport();
    public abstract void setSport(Sport sport);

    public abstract void impostaProprieta(Map<String, Object> mappaProprieta);
    public abstract Map<String, Object> getProprieta();

}
