package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;

public class ImpiantoDTO implements IModelToDTO{

    private int idImpianto;
    private boolean indoor;
    private String pavimentazione;
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

    public String getPavimentazione() {
        return pavimentazione;
    }

    public void setPavimentazione(String pavimentazione) {
        this.pavimentazione = pavimentazione;
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
    
    @Override
    public void impostaValoriDTO(Object modelDaConveritire){
        Impianto impianto = (Impianto)modelDaConveritire;
        setIdImpianto(impianto.getIdImpianto());
        setIndoor(impianto.isIndoor());
        setPavimentazione(impianto.getTipoPavimentazione().toString());
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
