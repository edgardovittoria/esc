package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quotePartecipazione")
public class QuotaPartecipazione {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idQuotaPartecipazione;
    @Column
    private boolean pagata;
    @Column
    private float costo;
    @OneToOne()
    private Sportivo sportivoAssociato;

    public QuotaPartecipazione(){}

    public QuotaPartecipazione(boolean pagata, float costo) {
        this.pagata = pagata;
        this.costo = costo;
    }

    public int getIdQuotaPartecipazione() {
        return idQuotaPartecipazione;
    }

    public void setIdQuotaPartecipazione(int idQuotaPartecipazione) {
        this.idQuotaPartecipazione = idQuotaPartecipazione;
    }

    public boolean isPagata() {
        return pagata;
    }

    public void setPagata(boolean pagata) {
        this.pagata = pagata;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public Sportivo getSportivoAssociato() {
        return sportivoAssociato;
    }

    public void setSportivoAssociato(Sportivo sportivoAssociato) {
        this.sportivoAssociato = sportivoAssociato;
    }

    


    

    
}
