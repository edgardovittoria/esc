package it.univaq.esc.model.costi;

import java.util.HashMap;
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

import it.univaq.esc.repository.ImpiantoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoCostoPrenotabile")
@DiscriminatorValue("costoBase")
@Getter @Setter @NoArgsConstructor
public class CostoPrenotabile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private Integer idCosto;
    @Column
    private Float costo;
    @Column
    private String tipoCosto;


    public void setProprieta(Map<String, Object> mappaProprietaDaImpostare){}
    
    public Map<String, Object> getProprieta(){
        return null;
    }

    public Map<String, Float> getMappaCosto(){
        Map<String, Float> mappaCosto = new HashMap<String, Float>();
        mappaCosto.put(this.getTipoCosto(), this.getCosto());

        return mappaCosto;
    }
}
