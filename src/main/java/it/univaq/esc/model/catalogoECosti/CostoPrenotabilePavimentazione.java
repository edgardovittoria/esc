package it.univaq.esc.model.catalogoECosti;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@DiscriminatorValue("costoPavimentazione")
@Getter @Setter @NoArgsConstructor
public class CostoPrenotabilePavimentazione extends CostoPrenotabile{
    
	@Column
    private String tipoPavimentazione;

    

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
