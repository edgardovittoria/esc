package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;

public class ImpiantoDTO {

    private int idImpianto;
    private boolean indoor;
    private String pavimetazione;
    private List<SportDTO> sportPraticabili = new ArrayList<SportDTO>();
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();

    public ImpiantoDTO(){}

    public int getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public String getPavimetazione() {
        return pavimetazione;
    }

    public void setPavimetazione(String pavimetazione) {
        this.pavimetazione = pavimetazione;
    }

    public List<SportDTO> getSportPraticabili() {
        return sportPraticabili;
    }

    public void setSportPraticabili(List<SportDTO> sportPraticabili) {
        this.sportPraticabili = sportPraticabili;
    }

    public List<AppuntamentoDTO> getAppuntamenti() {
        return appuntamenti;
    }

    public void setAppuntamenti(List<AppuntamentoDTO> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }
    
    public void impostaValoriDTO(Impianto impianto){
        setIdImpianto(impianto.getIdImpianto());
        setIndoor(impianto.isIndoor());
        setPavimetazione(impianto.getTipoPavimentazione().toString());
        for(Sport sport : impianto.getSportPraticabili()){
            SportDTO sportDTO = new SportDTO();
            sportDTO.impostaValoriDTO(sport);
            this.getSportPraticabili().add(sportDTO);
        }
        for(Appuntamento appuntamento : impianto.getListaAppuntamenti()){
            AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();
            appuntamentoDTO.impostaValoriDTO(appuntamento);
            this.getAppuntamenti().add(appuntamentoDTO);
        }
    }

    
    
}
