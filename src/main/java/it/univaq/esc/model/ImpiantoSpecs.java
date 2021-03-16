package it.univaq.esc.model;

import java.util.List;

public class ImpiantoSpecs {

    private boolean indoor;
    private int costo;
    private Pavimentazione TipoPavimentazione;
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
