package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.RegistroAppuntamenti;
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

    public void impostaValoriDTO(Prenotazione prenotazione, RegistroAppuntamenti registroAppuntamenti){
        this.sportivoPrenotante = new SportivoDTO();
        this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());
       
        for(PrenotazioneSpecs specs : prenotazione.getListaSpecifichePrenotazione()){           
            for(Appuntamento app : registroAppuntamenti.getListaAppuntamenti()){
               
                if(app.getPrenotazioneSpecsAppuntamento().getIDPrenotazioneSpecs() == specs.getIDPrenotazioneSpecs()){
                    AppuntamentoDTO appDTO = new AppuntamentoDTO();
                    appDTO.impostaValoriDTO(app);
                    this.aggiungiAppuntamento(appDTO);
                }
            }
        }
         
    }

    public void setAppuntamenti(List<AppuntamentoDTO> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }

    

    



   
}
