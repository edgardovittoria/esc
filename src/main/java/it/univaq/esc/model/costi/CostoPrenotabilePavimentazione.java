package it.univaq.esc.model.costi;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("costoPavimentazione")
public class CostoPrenotabilePavimentazione extends CostoPrenotabile{
    @Column
    private String tipoPavimentazione;

    CostoPrenotabilePavimentazione(){}

    private String getTipoPavimentazione() {
        return tipoPavimentazione;
    }

    private void setTipoPavimentazione(String tipoPavimentazione) {
        this.tipoPavimentazione = tipoPavimentazione;
    }

    @Override
    public Map<String, Object> getProprieta() {
       Map<String, Object> mappaProprieta = new HashMap<String, Object>();
       mappaProprieta.put("tipoPavimentazione", this.getTipoPavimentazione());
       return mappaProprieta;
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprietaDaImpostare) {
        this.setTipoPavimentazione((String)mappaProprietaDaImpostare.get("tipoPavimentazione"));
    }

    @Override
    public Map<String, Float> getMappaCosto(){
        Map<String, Float> mappaCosto = new HashMap<String, Float>();
        mappaCosto.put(this.getTipoPavimentazione(), this.getCosto());

        return mappaCosto;
    }
    
}
