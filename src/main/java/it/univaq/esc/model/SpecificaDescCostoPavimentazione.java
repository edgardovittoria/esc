package it.univaq.esc.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CostoPavimentazione")
public class SpecificaDescCostoPavimentazione extends SpecificaDescDecorator{
    
    @Column
    private String tipoPavimentazione;
    

    @Override
    public float getCosto() {
        float costoBase = this.getSpecifica().getCosto();
        return (costoBase*this.costo)/100;
    }

    private String getTipoPavimentazione() {
        return tipoPavimentazione;
    }

    private void setTipoPavimentazione(String tipoPavimentazione) {
        this.tipoPavimentazione = tipoPavimentazione;
    }

    

    

    

    @Override
    public void impostaProprieta(Map<String, Object> mappaProprieta) {
        this.setTipoPavimentazione((String)mappaProprieta.get("tipoPavimentazione"));
        
    }

    @Override
    public Map<String, Object> getProprieta() {
        Map<String, Object> mappaProprieta = this.getSpecifica().getProprieta();
        mappaProprieta.put("tipoPavimentazione", this.getTipoPavimentazione());

        return mappaProprieta;
    }

    @Override
    public void setTipoSpecificaDesc() {
        this.tipoSpecificaDesc = TipoSpecificaDesc.COSTO_PAVIMENTAZIONE.toString();
        
    }

}
