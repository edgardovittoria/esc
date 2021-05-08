package it.univaq.esc.controller;

import java.util.HashMap;
import java.util.Map;

public class EffettuaPrenotazioneLezioneState extends EffettuaPrenotazioneState{
    
    public EffettuaPrenotazioneLezioneState(){}

    @Override
    public Map<String, Object> getDatiOpzioni() {
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        
        return mappaValori;
    }


}
