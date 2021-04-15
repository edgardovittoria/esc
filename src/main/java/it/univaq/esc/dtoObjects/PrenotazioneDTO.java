package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.Sport;

public class PrenotazioneDTO {

    

    private SportivoDTO sportivoPrenotante;  
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();   
    
    

    public PrenotazioneDTO(){}
 

    

   

    public SportivoDTO getSportivoPrenotante() {
        return sportivoPrenotante;
    }

    public void setSportivoPrenotante(SportivoDTO sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public List<AppuntamentoDTO> getAppuntamenti() {
        return this.appuntamenti;
    }


    

    public void aggiungiAppuntamento(AppuntamentoDTO appuntamento){
        this.getAppuntamenti().add(appuntamento);
    }

    public void impostaValoriDTO(Prenotazione prenotazione, RegistroAppuntamentiDTO registroAppuntamentiDTO){
        this.sportivoPrenotante = new SportivoDTO();
        this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());
       
        for(PrenotazioneSpecs specs : prenotazione.getListaSpecifichePrenotazione()){
            PrenotazioneSpecsDTO specificaDTO = FactorySpecifichePrenotazione.getSpecifichePrenotazioneDTO(specs.getTipoPrenotazione());
            specificaDTO.impostaValoriDTO(specs);
            System.out.println(registroAppuntamentiDTO.toString());
            for(AppuntamentoDTO appDTO : registroAppuntamentiDTO.getListaAppuntamenti()){
                System.out.println("ID_SPECIFICA:" +appDTO.getSpecificaPrenotazione().getIdPrenotazioneSPecsDTO());
                if(appDTO.getSpecificaPrenotazione().getIdPrenotazioneSPecsDTO() == specificaDTO.getIdPrenotazioneSPecsDTO()){
                    this.aggiungiAppuntamento(appDTO);
                }
            }
        }
         
    }

    public void setAppuntamenti(List<AppuntamentoDTO> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }

    

    



   
}
