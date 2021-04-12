package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.univaq.esc.model.Appuntamento;

import it.univaq.esc.model.PrenotazioneSpecs;

import it.univaq.esc.model.Sportivo;

public abstract class PrenotazioneSpecsDTO {

    private boolean confermata = false;

    private float costo;

    // private Manutentore responsabilePrenotazione;

    private SportDTO sportAssociato;

    // private List<QuotaPartecipazione> quoteDiPartecipazione = new
    // ArrayList<QuotaPartecipazione>();
    private String tipoSpecifica;
    

    /**
     * @return boolean return the confermata
     */
    public boolean isConfermata() {
        return confermata;
    }

    /**
     * @param confermata the confermata to set
     */
    public void setConfermata(boolean confermata) {
        this.confermata = confermata;
    }

    /**
     * @return float return the costo
     */
    public float getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(float costo) {
        this.costo = costo;
    }

    /**
     * @return SportDTO return the sportAssociato
     */
    public SportDTO getSportAssociato() {
        return sportAssociato;
    }

    /**
     * @param sportAssociato the sportAssociato to set
     */
    public void setSportAssociato(SportDTO sportAssociato) {
        this.sportAssociato = sportAssociato;
    }


     /**
     * @return String return the tipoSpecifica
     */
    public String getTipoSpecifica() {
        return tipoSpecifica;
    }

    /**
     * @param tipoSpecifica the tipoSpecifica to set
     */
    public void setTipoSpecifica(String tipoSpecifica) {
        this.tipoSpecifica = tipoSpecifica;
    }


    public void impostaValoriDTO(PrenotazioneSpecs prenotazioneSpecs){
        SportDTO sportAssociato = new SportDTO();
        sportAssociato.impostaValoriDTO(prenotazioneSpecs.getSportAssociato());
    };

    public abstract void impostaValoriSpecificheExtraPrenotazioneDTO(Map<String, Object> mappaValori);

    public abstract Map<String, Object> getValoriSpecificheExtraPrenotazioneDTO();

}
