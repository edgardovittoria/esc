package it.univaq.esc.model.catalogoECosti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.Sport;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class PrenotabileDescrizione {
    @Id
    private String descrizione;
    @Column
    private String tipoPrenotazione;
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
