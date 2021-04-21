package it.univaq.esc.model.costi;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import it.univaq.esc.model.Sport;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TipoSpecificaDesc")
public abstract class SpecificaDesc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    // @Column
    // protected String tipoSpecificaDesc;
    @Column
    protected float costo;

    
    public SpecificaDesc(){
        //this.setTipoSpecificaDesc();
    }

    /**
     * @return String return the tipoSpecificaDesc
     */
    public abstract String getTipoSpecificaDesc();

   

    /**
     * @return float return the costo
     */
    public abstract Map<String, Float> getMappaCosti();
    

    protected float getCosto(){
        return this.costo;
    }
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

    public abstract List<String> getListaTipiSpecificheDesc();

}
