package it.univaq.esc.model.prenotazioni;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.utenti.UtentePolisportiva;

//import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "quotePartecipazione")
@Getter @Setter
public class QuotaPartecipazione {
    
    @Id
    @Setter(value = AccessLevel.PRIVATE)
    private int idQuotaPartecipazione;
    @Column
    private boolean pagata = false;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Costo costo;
    @ManyToOne()
    @JoinColumn(name = "sportivo_email",nullable = false)
    private UtentePolisportiva sportivoAssociato;
    

    public QuotaPartecipazione(Integer lastIdQuote) {
    	setIdQuotaPartecipazione(lastIdQuote+1);
    }
    
    public QuotaPartecipazione(Integer lastIdQuote ,boolean pagata, Costo costo, UtentePolisportiva sportivoDaAssociareAllaQuota) {
        this.setPagata(pagata);
        this.setCosto(costo);
        this.setSportivoAssociato(sportivoDaAssociareAllaQuota);
        setIdQuotaPartecipazione(lastIdQuote+1);
    }
    
    
    public void impostaDati(UtentePolisportiva utenteAssociato, Costo importo) {
    	setSportivoAssociato(utenteAssociato);
    	setCosto(importo);
    }
}
