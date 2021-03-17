package it.univaq.esc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "specificheImpianto")
public class ImpiantoSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSpecificaImpianto;
    @Column
    private boolean indoor;
    @Column
    private int costo;
    @Enumerated(EnumType.STRING)
    private Pavimentazione TipoPavimentazione;
    @OneToMany()
    @JoinColumn()
    private List<Sport> sportPraticabili;

    public ImpiantoSpecs(boolean indoor, int costo, Pavimentazione TipoPavimentazione, List<Sport> sportPraticabiliNellImpianto) {
        this.indoor = indoor;
        this.costo = costo;
        this.TipoPavimentazione = TipoPavimentazione;
        this.sportPraticabili = sportPraticabiliNellImpianto;
    }
    
    public boolean isIndoor() {
        return this.indoor;
    }
    
    public int getCosto() {
        return costo;
    }

    public Pavimentazione getTipoPavimentazione() {
        return TipoPavimentazione;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setTipoPavimentazione(Pavimentazione tipoPavimentazione) {
        TipoPavimentazione = tipoPavimentazione;
    }

    public List<Sport> getSportPraticabili() {
        return sportPraticabili;
    }

    public void setSportPraticabili(List<Sport> sportPraticabili) {
        this.sportPraticabili = sportPraticabili;
    }

    public void aggiungiSportPraticabile(Sport sportPraticabile){
        this.sportPraticabili.add(sportPraticabile);
    }

   
}
