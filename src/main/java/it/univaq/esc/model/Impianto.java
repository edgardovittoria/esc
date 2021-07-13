package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;



import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import it.univaq.esc.model.prenotazioni.Appuntamento;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "impianti")
@Getter @Setter @NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idImpianto")
public class Impianto extends StrutturaPolisportiva{
    
    @Column
    private boolean indoor;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "specifiche_associate_impianti",
                joinColumns = {@JoinColumn(name="id_impianto")},
                inverseJoinColumns = {@JoinColumn(name="id_specifica_associata")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ImpiantoSpecs> specificheImpianto;    

    public Impianto(List<ImpiantoSpecs> caratteristicheImpianto) {
       
        this.setSpecificheImpianto(caratteristicheImpianto);
    }



    /**
     * Restituisce il tipo di pavimentazione dell'impianto.
     * Sebbene si possano praticare diversi sport in uno stesso impianto, la pavimentazione sarà la medesima, 
     * per cui ogni specifica avrà la medesima pavimentazione.
     * È sufficiente prendere la prima e farsi restituire la pavimentazione.
     * @return il tipo di pavimentazione dell'impianto.
     */
    public Pavimentazione getTipoPavimentazione(){
          return this.getSpecificheImpianto().get(0).getTipoPavimentazione();
      }

    
    public List<Sport> getSportPraticabili(){
        List<Sport> sportPraticabili = new ArrayList<Sport>();
        for(ImpiantoSpecs impiantoSpecs : this.getSpecificheImpianto()){
            sportPraticabili.add(impiantoSpecs.getSportPraticabile());
        }
        return sportPraticabili;
    }

}
