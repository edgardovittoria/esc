package it.univaq.esc.model.catalogoECosti;

import it.univaq.esc.model.Costo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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
