package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.Sport;
import net.bytebuddy.build.HashCodeAndEqualsPlugin.ValueHandling.Sort;


public class PrenotazioneDTO {


    private SportivoDTO sportivoPrenotante;  
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
    private List<PrenotazioneSpecsDTO> listaSpecifichePrenotazione = new ArrayList<PrenotazioneSpecsDTO>();    
    private String tipoSpecifica;
    private SportDTO sportAssociato;

    public PrenotazioneDTO(){}

    public PrenotazioneDTO(PrenotazioneSpecsDTO specifichePrenotazione){
        this.aggiungiSpecificaPrenotazione(specifichePrenotazione);
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


   

    public SportivoDTO getSportivoPrenotante() {
        return sportivoPrenotante;
    }


    public void setSportivoPrenotante(SportivoDTO sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }



    public List<AppuntamentoDTO> getAppuntamenti() {
        return appuntamenti;
    }


    public void setAppuntamenti(List<AppuntamentoDTO> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }




    public List<PrenotazioneSpecsDTO> getSpecifichePrenotazione() {
        return this.listaSpecifichePrenotazione;
    }


    public void setListaSpecifichePrenotazione(List<PrenotazioneSpecsDTO> specifichePrenotazione) {
        this.getSpecifichePrenotazione().addAll(specifichePrenotazione);
    }

    public void aggiungiSpecificaPrenotazione(PrenotazioneSpecsDTO specifica){
        this.getSpecifichePrenotazione().add(specifica);
    }

    public void impostaValoriDTO(Prenotazione prenotazione){
        this.sportivoPrenotante = new SportivoDTO();
        this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());
        this.setTipoSpecifica(prenotazione.getTipoPrenotazione());
        for(PrenotazioneSpecs specifica : prenotazione.getListaSpecifichePrenotazione()){
            PrenotazioneSpecsDTO specificaDTO = FactorySpecifichePrenotazione.getSpecifichePrenotazioneDTO(this.getTipoSpecifica());
            specificaDTO.impostaValoriDTO(specifica);
            this.aggiungiSpecificaPrenotazione(specificaDTO);
        }
        this.setSportAssociato(new SportDTO());;
        this.getSportAssociato().impostaValoriDTO((Sport) prenotazione.getSportAssociato());
        
    }

    public Object getSingolaSpecificaDTO(String etichettaSpecificaDTO, PrenotazioneSpecsDTO specifica){
        return specifica.getValoriSpecificheExtraPrenotazioneDTO().get(etichettaSpecificaDTO);
    }

    



    /**
     * @return List<PrenotazioneSpecsDTO> return the listaSpecifichePrenotazione
     */
    public List<PrenotazioneSpecsDTO> getListaSpecifichePrenotazione() {
        return listaSpecifichePrenotazione;
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

}
