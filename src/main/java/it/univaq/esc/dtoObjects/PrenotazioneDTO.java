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

    @Autowired
    private RegistroAppuntamentiDTO registroAppuntamentiDTO;

    private SportivoDTO sportivoPrenotante;  
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();   
    
    

    public PrenotazioneDTO(){}

    


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

    public List<AppuntamentoDTO> getgetAppuntamenti() {
        return this.appuntamenti;
    }


    

    public void aggiungiAppuntamento(AppuntamentoDTO appuntamento){
        this.getgetAppuntamenti().add(appuntamento);
    }

    public void impostaValoriDTO(Prenotazione prenotazione){
        this.sportivoPrenotante = new SportivoDTO();
        this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());
       
        for(PrenotazioneSpecs specs : prenotazione.getListaSpecifichePrenotazione()){
            PrenotazioneSpecsDTO specificaDTO = FactorySpecifichePrenotazione.getSpecifichePrenotazioneDTO(specs.getTipoPrenotazione());
            specificaDTO.impostaValoriDTO(specs);
            System.out.println(this.registroAppuntamentiDTO.toString());
            for(AppuntamentoDTO appDTO : this.registroAppuntamentiDTO.getListaAppuntamenti()){
                if(appDTO.getSpecificaPrenotazione().getIdPrenotazioneSPecsDTO() == specificaDTO.getIdPrenotazioneSPecsDTO()){
                    this.aggiungiAppuntamento(appDTO);
                }
            }
        }
         
    }

    

    



   
}
