package it.univaq.esc.model;

public class QuotaPartecipazione {
    
    private boolean pagata;
    private float costo;
    private Sportivo sportivoAssociato;

    public QuotaPartecipazione(boolean pagata, float costo) {
        this.pagata = pagata;
        this.costo = costo;
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
