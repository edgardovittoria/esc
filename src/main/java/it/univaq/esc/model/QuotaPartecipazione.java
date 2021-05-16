package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

//import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quotePartecipazione")
@Getter @Setter @NoArgsConstructor
public class QuotaPartecipazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private int idQuotaPartecipazione;
    @Column
    private boolean pagata;
    @Column
    private float costo;
    @ManyToOne()
    @JoinColumn(name = "sportivo_email",nullable = false)
    private UtentePolisportivaAbstract sportivoAssociato;
    

    public QuotaPartecipazione(boolean pagata, float costo, UtentePolisportivaAbstract sportivoDaAssociareAllaQuota) {
        this.setPagata(pagata);
        this.setCosto(costo);
        this.setSportivoAssociato(sportivoDaAssociareAllaQuota);
    }
    
}
