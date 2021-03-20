package it.univaq.esc.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

@Entity
@Table(name = "specificheImpianto")
public class ImpiantoSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSpecificaImpianto;
    
    @Enumerated(EnumType.STRING)
    private Pavimentazione TipoPavimentazione;
    @ManyToOne()
    @JoinColumn()
    private Sport sportPraticabile;

    public ImpiantoSpecs(){}

    public ImpiantoSpecs(Pavimentazione TipoPavimentazione, Sport sportPraticabileNellImpianto) {
        this.TipoPavimentazione = TipoPavimentazione;
        this.sportPraticabile = sportPraticabileNellImpianto;
    }


    public Pavimentazione getTipoPavimentazione() {
        return TipoPavimentazione;
    }    

    public void setTipoPavimentazione(Pavimentazione tipoPavimentazione) {
        TipoPavimentazione = tipoPavimentazione;
    }

    public Sport getSportPraticabile() {
        return sportPraticabile;
    }

    public void setSportPraticabile(Sport sportPraticabile){
        this.sportPraticabile = sportPraticabile;
    }

   
}
