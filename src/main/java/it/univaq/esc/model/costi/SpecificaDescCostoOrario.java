package it.univaq.esc.model.costi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import it.univaq.esc.model.Sport;

@Entity
@DiscriminatorValue(value = "COSTO_ORARIO")
public class SpecificaDescCostoOrario extends SpecificaDesc implements Serializable{

    @Column
    private String tipoPrenotazione;
    @ManyToOne
    @JoinColumn
    private Sport sport;

    public SpecificaDescCostoOrario(){
       // super();
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
    // @Override
    // public void setTipoSpecificaDesc() {
    //     this.tipoSpecificaDesc = TipoSpecificaDesc.COSTO_ORARIO.toString();
        
    // }
    @Override
    public void setCosto(float costo, String tipoSpecifica) {
        this.costo = costo;
        
    }
    @Override
    public List<String> getListaTipiSpecificheDesc() {
        List<String> listaTipiSpecificheDesc = new ArrayList<String>();
        listaTipiSpecificheDesc.add(this.getTipoSpecificaDesc());

        return listaTipiSpecificheDesc;
    }
    @Override
    public Map<String, Float> getMappaCosti() {
        Map<String, Float> mappaCosti = new HashMap<String, Float>();
        mappaCosti.put("costoOrario", this.getCosto());

        return mappaCosti;
    }

    @Override
    public String getTipoSpecificaDesc() {
        return TipoSpecificaDesc.COSTO_ORARIO.toString();
    }


}
