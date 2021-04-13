package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Sportivo;
import it.univaq.esc.model.UtentePolisportivaAbstract;

public class AppuntamentoDTO {
    
    private LocalDate dataAppuntamento;
    private LocalTime oraInizioAppuntamento;
    private LocalTime oraFineAppuntamento;
    private List<SportivoDTO> partecipanti = new ArrayList<SportivoDTO>();

    public AppuntamentoDTO(){}

    public LocalDate getDataAppuntamento() {
        return dataAppuntamento;
    }

    public void setDataAppuntamento(LocalDate dataAppuntamento) {
        this.dataAppuntamento = dataAppuntamento;
    }

    public LocalTime getOraInizioAppuntamento() {
        return oraInizioAppuntamento;
    }

    public void setOraInizioAppuntamento(LocalTime oraInizioAppuntamento) {
        this.oraInizioAppuntamento = oraInizioAppuntamento;
    }

    public LocalTime getOraFineAppuntamento() {
        return oraFineAppuntamento;
    }

    public void setOraFineAppuntamento(LocalTime oraFineAppuntamento) {
        this.oraFineAppuntamento = oraFineAppuntamento;
    }

    public List<SportivoDTO> getListaPartecipanti(){
        return this.partecipanti;
    }

    public void aggiungiPartecipante(SportivoDTO partecipante){
        this.partecipanti.add(partecipante);
    }

    

    public void impostaValoriDTO(Appuntamento appuntamento){
        setDataAppuntamento(appuntamento.getDataAppuntamento());
        setOraInizioAppuntamento(appuntamento.getOraInizioAppuntamento());
        setOraFineAppuntamento(appuntamento.getOraFineAppuntamento());
        for(UtentePolisportivaAbstract partecipante : appuntamento.getListaPartecipanti()){
            SportivoDTO partecipanteDTO = new SportivoDTO();
            partecipanteDTO.impostaValoriDTO(partecipante);
            this.aggiungiPartecipante(partecipanteDTO);
        }
        
    }
    
}
