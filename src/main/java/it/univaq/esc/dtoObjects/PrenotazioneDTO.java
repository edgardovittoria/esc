package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.Sport;



public class PrenotazioneDTO {


    private SportivoDTO sportivoPrenotante;  
    private List<PrenotazioneSpecsDTO> listaSpecifichePrenotazione = new ArrayList<PrenotazioneSpecsDTO>();    
    
    

    public PrenotazioneDTO(){}

    public PrenotazioneDTO(PrenotazioneSpecsDTO specifichePrenotazione){
        this.aggiungiSpecificaPrenotazione(specifichePrenotazione);
    }


    // public List<ImpiantoDTO> getImpiantiPrenotati(){
    //     List<ImpiantoDTO> impiantiPrenotati = new ArrayList<ImpiantoDTO>();
    //     for (PrenotazioneSpecsDTO specifica : this.getListaSpecifichePrenotazione()){
    //         impiantiPrenotati.add(specifica.getImpiantoPrenotato());
    //     }
    //     return impiantiPrenotati;
    // }
    

    

   

    public SportivoDTO getSportivoPrenotante() {
        return sportivoPrenotante;
    }

    public void setSportivoPrenotante(SportivoDTO sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
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

}
