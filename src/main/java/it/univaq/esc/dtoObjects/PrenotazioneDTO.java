package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;



public class PrenotazioneDTO implements IModelToDTO{

    

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

    @Override
    public void impostaValoriDTO(Object modelDaConvertire){
        Map<String, Object> mappa = (HashMap<String, Object>)modelDaConvertire; 
        Prenotazione prenotazione = (Prenotazione)mappa.get("prenotazione");
        List<Appuntamento> listaAppuntamentiPrenotazione = (List<Appuntamento>)mappa.get("appuntamentiPrenotazione");
        this.sportivoPrenotante = new SportivoDTO();
        this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());
       
        for(Appuntamento app : listaAppuntamentiPrenotazione){
            AppuntamentoDTO appDTO = new AppuntamentoDTO();
                    appDTO.impostaValoriDTO(app);
                    this.aggiungiAppuntamento(appDTO);
        }

        
         
    }

    public void setAppuntamenti(List<AppuntamentoDTO> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }

    

    



   
}
