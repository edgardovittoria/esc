package it.univaq.esc.model.catalogoECosti;

import it.univaq.esc.model.Costo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;
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
    public Map<String, Costo> getMappaCosto(){
        Map<String, Costo> mappaCosto = new HashMap<String, Costo>();
        mappaCosto.put(this.getTipoPavimentazione(), this.getCosto());

        return mappaCosto;
    }
    
}
