package it.univaq.esc.dtoObjects;

import java.util.HashMap;

public interface IPrenotabileDTO {
    
    public void impostaValoriSpecifichePrenotazioneDTO(HashMap<String, Object> mappaValori);
    public HashMap<String, Object> getValoriSpecifichePrenotazioneDTO();
}
