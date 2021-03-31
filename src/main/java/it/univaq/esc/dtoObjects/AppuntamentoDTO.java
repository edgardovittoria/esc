package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;

import it.univaq.esc.model.Appuntamento;

public class AppuntamentoDTO {
    
    private LocalDate dataAppuntamento;
    private LocalTime oraInizioAppuntamento;
    private LocalTime oraFineAppuntamento;
    private int idImpianto;

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

    public int getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    public void impostaValoriDTO(Appuntamento appuntamento){
        setDataAppuntamento(appuntamento.getDataAppuntamento());
        setOraInizioAppuntamento(appuntamento.getOraInizioAppuntamento());
        setOraFineAppuntamento(appuntamento.getOraFineAppuntamento());
        setIdImpianto(appuntamento.getIdImpiantoAppuntamento());
    }
    
}
