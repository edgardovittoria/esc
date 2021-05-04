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

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name = "impianti")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idImpianto")
public class Impianto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    
    @Transient
    private Calendario calendarioAppuntamentiImpianto = new Calendario();
    

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

    public List<Sport> getSportPraticabili(){
        List<Sport> sportPraticabili = new ArrayList<Sport>();
        for(ImpiantoSpecs impiantoSpecs : this.getSpecificheImpianto()){
            sportPraticabili.add(impiantoSpecs.getSportPraticabile());
        }
        return sportPraticabili;
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
     * @return Calendario return the calendarioAppuntamentiImpianto
     */
    public Calendario getCalendarioAppuntamentiImpianto() {
        return calendarioAppuntamentiImpianto;
    }

    /**
     * @param calendarioAppuntamentiImpianto the calendarioAppuntamentiImpianto to set
     */
    public void setCalendarioAppuntamentiImpianto(Calendario calendarioAppuntamentiImpianto) {
        this.calendarioAppuntamentiImpianto = calendarioAppuntamentiImpianto;
    }

    public List<Appuntamento> getListaAppuntamenti(){
        return this.getCalendarioAppuntamentiImpianto().getListaAppuntamenti();
    }

}
