package it.univaq.esc.model.catalogoECosti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Sport;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class PrenotabileDescrizione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private Integer id;
    @Column
    private String tipoPrenotazione;
    @Column
    private String modalitaPrenotazione;
    @ManyToOne
    @JoinColumn
    private Sport sportAssociato;
    @Column 
    private Integer minimoNumeroPartecipanti;
    @Column
    private Integer massimoNumeroPartecipanti;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CostoPrenotabile> listaCosti = new ArrayList<CostoPrenotabile>();

    
    public void aggiungiCosto(CostoPrenotabile costoDaAggiungere){
        this.getListaCosti().add(costoDaAggiungere);
    }
  

    public Map<String, Float> getMappaCosti(){
        Map<String, Float> mappaCosti = new HashMap<String, Float>();
        for(CostoPrenotabile costo : this.getListaCosti()){
            mappaCosti.putAll(costo.getMappaCosto());
        }
        return mappaCosti;
    }
    
}
