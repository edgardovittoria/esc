package it.univaq.esc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.IFormPrenotabile;

@Component
public class EffettuaPrenotazioneLezioneState extends EffettuaPrenotazioneState{
    
    public EffettuaPrenotazioneLezioneState(){}

    @Override
    public Map<String, Object> getDatiOpzioni() {
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        
        return mappaValori;
    }

    @Override
    public void impostaDatiPrenotazione(IFormPrenotabile formDati, EffettuaPrenotazioneHandlerRest controller) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
        // TODO Auto-generated method stub
        return null;
    }


}
