package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.Sportivo;

public class PrenotazioneDTO {

    private float costo;
    private boolean confermata;
    private SportivoDTO sportivoPrenotante;  
    private List<SportivoDTO> partecipanti = new ArrayList<SportivoDTO>();
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
    private SportDTO sport;
    private List<ImpiantoDTO> impiantiPrenotati = new ArrayList<ImpiantoDTO>();
    private IPrenotabileDTO specifichePrenotazione;    

    public PrenotazioneDTO(){}


    public float getCosto() {
        return costo;
    }


    public void setCosto(float costo) {
        this.costo = costo;
    }


    public boolean isConfermata() {
        return confermata;
    }


    public void setConfermata(boolean confermata) {
        this.confermata = confermata;
    }


    public SportivoDTO getSportivoPrenotante() {
        return sportivoPrenotante;
    }


    public void setSportivoPrenotante(SportivoDTO sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }


    public List<SportivoDTO> getPartecipanti() {
        return partecipanti;
    }


    public void setPartecipanti(List<SportivoDTO> partecipanti) {
        this.partecipanti = partecipanti;
    }


    public List<AppuntamentoDTO> getAppuntamenti() {
        return appuntamenti;
    }


    public void setAppuntamenti(List<AppuntamentoDTO> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }


    public SportDTO getSport() {
        return sport;
    }


    public void setSport(SportDTO sport) {
        this.sport = sport;
    }


    public List<ImpiantoDTO> getImpiantiPrenotati() {
        return impiantiPrenotati;
    }


    public void setImpiantiPrenotati(List<ImpiantoDTO> impiantiPrenotati) {
        this.impiantiPrenotati = impiantiPrenotati;
    }


    public IPrenotabileDTO getSpecifichePrenotazione() {
        return specifichePrenotazione;
    }


    public void setSpecifichePrenotazione(IPrenotabileDTO specifichePrenotazione) {
        this.specifichePrenotazione = specifichePrenotazione;
    }

    public void impostaValoriDTO(Prenotazione prenotazione){
        this.sportivoPrenotante = new SportivoDTO();
        this.sportivoPrenotante.impostaValoriDTO(prenotazione.getSportivoPrenotante());
        this.confermata = prenotazione.isConfermata();
        this.costo = prenotazione.getCosto();
        for(Sportivo sportivo : prenotazione.getListaPartecipanti()){
            SportivoDTO partecipanteDTO = new SportivoDTO();
            partecipanteDTO.impostaValoriDTO(sportivo);
            this.partecipanti.add(partecipanteDTO);
        }
        this.sport = new SportDTO();
        this.sport.impostaValoriDTO((Sport) prenotazione.getSingolaSpecifica("sport"));
        for(Appuntamento appuntamento : prenotazione.getListaAppuntamenti()){
            AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();
            appuntamentoDTO.impostaValoriDTO(appuntamento);
            this.getAppuntamenti().add(appuntamentoDTO);
        }
    }


}
