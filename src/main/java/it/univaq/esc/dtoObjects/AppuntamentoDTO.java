package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public class AppuntamentoDTO {
    
    private OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
    private List<SportivoDTO> partecipanti = new ArrayList<SportivoDTO>();
    private PrenotazioneSpecsDTO specificaPrenotazione;

    public AppuntamentoDTO(){}

    private OrarioAppuntamento getOrarioAppuntamento() {
        return orarioAppuntamento;
    }

    private void setOrarioAppuntamento(OrarioAppuntamento orarioAppuntamento) {
        this.orarioAppuntamento = orarioAppuntamento;
    }

    public LocalDate getDataAppuntamento() {
        return this.getOrarioAppuntamento().getLocalDataPrenotazione();
    }

    public void setDataAppuntamento(LocalDate dataAppuntamento) {
        this.getOrarioAppuntamento().setLocalDataPrenotaione(dataAppuntamento);
    }

    public LocalTime getOraInizioAppuntamento() {
        return this.getOrarioAppuntamento().getOraInizio();
    }

    public void setOraInizioAppuntamento(LocalTime oraInizioAppuntamento) {
        this.getOrarioAppuntamento().setOraInizio(oraInizioAppuntamento);
    }

    public LocalTime getOraFineAppuntamento() {
        return this.getOrarioAppuntamento().getOraFine();
    }

    public void setOraFineAppuntamento(LocalTime oraFineAppuntamento) {
        this.getOrarioAppuntamento().setOraFine(oraFineAppuntamento);
    }

    public List<SportivoDTO> getListaPartecipanti(){
        return this.partecipanti;
    }

    public void aggiungiPartecipante(SportivoDTO partecipante){
        this.partecipanti.add(partecipante);
    }

    public void setSpecificaPrenotazione(PrenotazioneSpecsDTO specifica){
        this.specificaPrenotazione = specifica;
    }

    public PrenotazioneSpecsDTO getSpecificaPrenotazione(){
        return this.specificaPrenotazione;
    }
    

    public void impostaValoriDTO(Appuntamento appuntamento){
        setDataAppuntamento(appuntamento.getDataAppuntamento());
        setOraInizioAppuntamento(appuntamento.getOraInizioAppuntamento());
        setOraFineAppuntamento(appuntamento.getOraFineAppuntamento());
        PrenotazioneSpecsDTO specificaDTO = FactorySpecifichePrenotazione.getSpecifichePrenotazioneDTO(appuntamento.getPrenotazioneSpecsAppuntamento().getTipoPrenotazione());
        specificaDTO.impostaValoriDTO(appuntamento.getPrenotazioneSpecsAppuntamento());
        this.setSpecificaPrenotazione(specificaDTO);
        for(UtentePolisportivaAbstract partecipante : appuntamento.getListaPartecipanti()){
            SportivoDTO partecipanteDTO = new SportivoDTO();
            partecipanteDTO.impostaValoriDTO(partecipante);
            this.aggiungiPartecipante(partecipanteDTO);
        }
        
    }


    
}
