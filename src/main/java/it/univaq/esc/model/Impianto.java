package it.univaq.esc.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "impianti")
public class Impianto {
    
    @Id
    private int idImpianto;
    @Column
    private int costo;
    @Column
    private boolean indoor;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "specifiche_associate_impianti",
                joinColumns = {@JoinColumn(name="id_impianto")},
                inverseJoinColumns = {@JoinColumn(name="id_specifica_associata")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ImpiantoSpecs> specificheImpianto;


    @ManyToMany
    @JoinTable(name = "impianti_prenotati",
                joinColumns = {@JoinColumn(name="id_impianto_prenotato")},
                inverseJoinColumns = {@JoinColumn(name="id_specifica_prenotazione")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<PrenotazioneSpecs> prenotazioniPerImpianto;
    

    public Impianto() {}

    public Impianto(int idImpianto, List<ImpiantoSpecs> caratteristicheImpianto) {
        this.idImpianto = idImpianto;
        this.specificheImpianto = caratteristicheImpianto;
    }


    public int getIdImpianto() {
        return idImpianto;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public boolean isIndoor() {
        return this.indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public Pavimentazione getTipoPavimentazione(){
          return this.specificheImpianto.get(0).getTipoPavimentazione();
      }


    public List<ImpiantoSpecs> getSpecificheImpianto() {
        return specificheImpianto;
    }

    public void setSpecificaImpianto(ImpiantoSpecs specificaImpianto) {
        this.specificheImpianto.add(specificaImpianto);
    }

    
    
  

    /**
     * @param idImpianto the idImpianto to set
     */
    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    /**
     * @param specificheImpianto the specificheImpianto to set
     */
    public void setSpecificheImpianto(List<ImpiantoSpecs> specificheImpianto) {
        this.specificheImpianto = specificheImpianto;
    }

    /**
     * @return List<Prenotazione> return the prenotazioniPerImpianto
     */
    public List<PrenotazioneSpecs> getPrenotazioniPerImpianto() {
        return prenotazioniPerImpianto;
    }

   
}
