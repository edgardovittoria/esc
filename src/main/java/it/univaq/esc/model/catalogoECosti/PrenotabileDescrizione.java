package it.univaq.esc.model.catalogoECosti;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.prenotazioni.TipoPrenotazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor
public class PrenotabileDescrizione {
    @Id
    private String descrizione;
    @Enumerated(EnumType.STRING)
    private TipoPrenotazione tipoPrenotazione;
    @Enumerated(EnumType.STRING)
    private ModalitaPrenotazione modalitaPrenotazione;
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
  

    public Map<String, Costo> getMappaCosti(){
        Map<String, Costo> mappaCosti = new HashMap<String, Costo>();
        for(CostoPrenotabile costo : this.getListaCosti()){
            mappaCosti.putAll(costo.getMappaCosto());
        }
        return mappaCosti;
    }
    
    public boolean accettaNuoviPartecipantiOltre(Integer numeroPartecipantiAttuali) {
    	if(numeroPartecipantiAttuali < getMassimoNumeroPartecipanti()) {
    		return true;
    	}
    	return false;
    }
    
    public String getNomeEvento() {
    	return getDescrizione();
    }
    
    public boolean isEqual(PrenotabileDescrizione prenotabileDescrizione) {
    	if(getDescrizione().equals(prenotabileDescrizione.getDescrizione())) {
    		return true;
    	}
    	return false;
    }
}
