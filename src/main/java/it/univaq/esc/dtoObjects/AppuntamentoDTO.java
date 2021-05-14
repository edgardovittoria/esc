package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.QuotaPartecipazione;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public class AppuntamentoDTO implements IModelToDTO{
    
    private OrarioAppuntamento orarioAppuntamento = new OrarioAppuntamento();
    private List<SportivoDTO> partecipanti = new ArrayList<SportivoDTO>();
    private PrenotazioneSpecsDTO specificaPrenotazione;
    private List<QuotaPartecipazioneDTO> quotePartecipazione = new ArrayList<QuotaPartecipazioneDTO>();
    private SportivoDTO creatore;

    public AppuntamentoDTO(){}

    public SportivoDTO getCreatore() {
        return creatore;
    }

    public void setCreatore(SportivoDTO creatore) {
        this.creatore = creatore;
    }

    public List<QuotaPartecipazioneDTO> getQuotePartecipazione() {
        return quotePartecipazione;
    }

    public void setQuotePartecipazione(List<QuotaPartecipazioneDTO> quotePartecipazione) {
        this.quotePartecipazione = quotePartecipazione;
    }

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
    

    @Override
    public void impostaValoriDTO(Object modelDaConvertire){
        Appuntamento appuntamento = (Appuntamento)modelDaConvertire;
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
        List<QuotaPartecipazioneDTO> listaQuote = new ArrayList<QuotaPartecipazioneDTO>();
        for(QuotaPartecipazione quota : appuntamento.getQuotePartecipazione()){
            QuotaPartecipazioneDTO quotaDTO = new QuotaPartecipazioneDTO();
            quotaDTO.impostaValoriDTO(quota);
            listaQuote.add(quotaDTO);
            
        }
        this.setQuotePartecipazione(listaQuote);
        
        SportivoDTO creatore = new SportivoDTO();
        creatore.impostaValoriDTO(appuntamento.creatoDa());
        this.setCreatore(creatore);
        
    }


    
}
