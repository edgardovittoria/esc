package it.univaq.esc.model;

public enum Pavimentazione {

     sintetico("Sintetico"),
     terraBattuta("Terra Battuta"),
     cemento("Cemento");

    private final String TipoPavimentazione;

    private Pavimentazione(String TipoPavimentazione) {
        this.TipoPavimentazione = TipoPavimentazione;
    }

    public String getTipoPavimentazione() {
        return TipoPavimentazione;
    }
   
}
