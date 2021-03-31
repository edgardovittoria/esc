package it.univaq.esc.model;

import java.util.HashMap;



public interface IPrenotabile {
    public void impostaValoriSpecifichePrenotazione(HashMap<String, Object> mappaValori);
    public HashMap<String, Object> getValoriSpecifichePrenotazione();
    public String getTipoSpecifica();
}