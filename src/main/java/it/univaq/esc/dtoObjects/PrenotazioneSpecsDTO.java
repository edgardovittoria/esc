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

    private List<SportivoDTO> partecipanti = new ArrayList<SportivoDTO>();

    // private List<QuotaPartecipazione> quoteDiPartecipazione = new
    // ArrayList<QuotaPartecipazione>();

    private ImpiantoDTO impiantoPrenotato;

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
     * @return List<SportivoDTO> return the partecipanti
     */
    public List<SportivoDTO> getPartecipanti() {
        return partecipanti;
    }

    /**
     * @param partecipanti the partecipanti to set
     */
    public void setPartecipanti(List<SportivoDTO> partecipanti) {
        this.partecipanti = partecipanti;
    }

    /**
     * @return ImpiantoDTO return the impiantoPrenotato
     */
    public ImpiantoDTO getImpiantoPrenotato() {
        return impiantoPrenotato;
    }

    /**
     * @param impiantoPrenotato the impiantoPrenotato to set
     */
    public void setImpiantoPrenotato(ImpiantoDTO impiantoPrenotato) {
        this.impiantoPrenotato = impiantoPrenotato;
    }

    public void impostaValoriDTO(PrenotazioneSpecs prenotazioneSpecs) {
        for (Sportivo sportivo : prenotazioneSpecs.getListaPartecipanti()) {
            SportivoDTO partecipanteDTO = new SportivoDTO();
            partecipanteDTO.impostaValoriDTO(sportivo);
            this.partecipanti.add(partecipanteDTO);
        }

        ImpiantoDTO impiantoDTO = new ImpiantoDTO();
        impiantoDTO.impostaValoriDTO(prenotazioneSpecs.getImpiantoPrenotato());
        this.setImpiantoPrenotato(impiantoDTO);

    }

    public abstract void impostaValoriSpecificheExtraPrenotazioneDTO(Map<String, Object> mappaValori);

    public abstract Map<String, Object> getValoriSpecificheExtraPrenotazioneDTO();

}
