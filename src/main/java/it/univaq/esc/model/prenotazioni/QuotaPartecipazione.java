package it.univaq.esc.model.prenotazioni;

import it.univaq.esc.model.Costo;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "quotePartecipazione")
@Getter @Setter @NoArgsConstructor
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
