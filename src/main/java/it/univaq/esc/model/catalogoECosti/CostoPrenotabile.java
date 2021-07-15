package it.univaq.esc.model.catalogoECosti;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.cache.interceptor.CacheOperationSource;

import it.univaq.esc.model.Costo;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Costo costo = new Costo();
    @Enumerated(EnumType.STRING)
    private TipoCostoPrenotabile tipoCosto;


    public void setProprieta(Map<String, Object> mappaProprietaDaImpostare){}
    
    public Map<String, Object> getProprieta(){
        return null;
    }

    public Map<String, Costo> getMappaCosto(){
        Map<String, Costo> mappaCosto = new HashMap<String, Costo>();
        mappaCosto.put(this.getTipoCosto().toString(), this.getCosto());

        return mappaCosto;
    }
}
