package it.univaq.esc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class EffettuaPrenotazioneImpiantoState extends EffettuaPrenotazioneState{
    
    public EffettuaPrenotazioneImpiantoState(){}

    @Override
    public Map<String, Object> getDatiOpzioni() {
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        mappaValori.put("sportiviPolisportiva", this.getSportiviPolisportiva());

        return mappaValori;
    }
}
