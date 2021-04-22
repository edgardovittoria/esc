package it.univaq.esc.model.costi;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "COSTO_PAVIMENTAZIONE")
public class SpecificaDescCostoPavimentazione extends SpecificaDescDecorator{
    
    @Column
    private String tipoPavimentazione;
    
    public SpecificaDescCostoPavimentazione(){}

    public SpecificaDescCostoPavimentazione(SpecificaDesc specificaAssociata){
        super(specificaAssociata);
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

    // @Override
    // public void setTipoSpecificaDesc() {
    //     this.tipoSpecificaDesc = TipoSpecificaDesc.COSTO_PAVIMENTAZIONE.toString();
        
    // }

    @Override
    public Map<String, Float> getMappaCosti() {
        Map<String, Float> mappaCosti = this.getSpecifica().getMappaCosti();
        mappaCosti.put(this.getTipoPavimentazione(), this.getCosto());

        return mappaCosti;
    }


    @Override
    public String getTipoSpecificaDesc() {
        return TipoSpecificaDesc.COSTO_PAVIMENTAZIONE.toString();
    }

}
