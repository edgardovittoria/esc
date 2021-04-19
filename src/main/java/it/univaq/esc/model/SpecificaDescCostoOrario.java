package it.univaq.esc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "CostoOrario")
public class SpecificaDescCostoOrario extends SpecificaDesc implements Serializable{

    @Column
    private String tipoPrenotazione;
    @ManyToOne
    @JoinColumn
    private Sport sport;

    
    @Override
    public float getCosto() {
        return this.costo;
    }
    @Override
    public void setTipoPrenotazione(String tipoPrenotazione) {
        this.tipoPrenotazione = tipoPrenotazione;
        
    }
    @Override
    public String getTipoPrenotazione() {
        return this.tipoPrenotazione;
    }
    @Override
    public Sport getSport() {
        return this.sport;
    }
    @Override
    public void setSport(Sport sport) {
        this.sport = sport;
        
    }
    @Override
    public void impostaProprieta(Map<String, Object> mappaProprieta) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public Map<String, Object> getProprieta() {
        return new HashMap<String, Object>();
    }
    @Override
    public void setTipoSpecificaDesc() {
        this.tipoSpecificaDesc = TipoSpecificaDesc.COSTO_ORARIO.toString();
        
    }
    @Override
    public void setCosto(float costo, String tipoSpecifica) {
        this.costo = costo;
        
    }


}
