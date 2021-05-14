package it.univaq.esc.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.event.InternalFrameAdapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.repository.AppuntamentoRepository;

@Component
@Singleton
public class RegistroAppuntamenti {

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;
    
    private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();


    
    public RegistroAppuntamenti(){}

    @PostConstruct
    private void popola(){
        this.getListaAppuntamenti().addAll(getAppuntamentoRepository().findAll());
        for(Appuntamento appuntamento : this.getListaAppuntamenti()){
            CalcolatoreCostoBase calcBase = new CalcolatoreCostoBase();
            CalcolatoreCostoComposito calcComposito = new CalcolatoreCostoComposito();
            calcComposito.aggiungiStrategiaCosto(calcBase);
            appuntamento.setCalcolatoreCosto(calcComposito);
        }
    }

    /**
     * @return List<Appuntamento> return the listaAppuntamenti
     */
    public List<Appuntamento> getListaAppuntamenti() {
        return listaAppuntamenti;
    }

   
    public void salvaAppuntamento(Appuntamento appuntamento){
        this.getListaAppuntamenti().add(appuntamento);
        this.getAppuntamentoRepository().save(appuntamento);
    }

    public void aggiornaAppuntamento(Appuntamento appuntamento){
        this.getAppuntamentoRepository().saveAndFlush(appuntamento);
    }


    public void salvaListaAppuntamenti(List<Appuntamento> listaAppuntamenti){
        this.getListaAppuntamenti().addAll(listaAppuntamenti);
        this.getAppuntamentoRepository().saveAll(listaAppuntamenti);
    }

    /**
     * @return AppuntamentoRepository return the appuntamentoRepository
     */
    private AppuntamentoRepository getAppuntamentoRepository() {
        return appuntamentoRepository;
    }


    public Appuntamento getAppuntamentoBySpecificaAssociata(PrenotazioneSpecs prenotazioneSpecs){
        return this.getAppuntamentoRepository().findByPrenotazioneSpecsAppuntamento_Id(prenotazioneSpecs.getIDPrenotazioneSpecs());
    }

   
    public List<Appuntamento> getAppuntamentiSottoscrivibiliPerTipo(String tipoPrenotazione, UtentePolisportivaAbstract utentePerCuiTrovareAppuntamenti){
        List<Appuntamento> appuntamentiFiltrati = this.filtraAppuntamentiPerDataOra(this.getListaAppuntamenti(), LocalDateTime.now());
        appuntamentiFiltrati = this.filtraAppuntamentiPerTipoPrenotazione(appuntamentiFiltrati, tipoPrenotazione);
        appuntamentiFiltrati = this.filtraAppuntamentiPending(appuntamentiFiltrati);
        appuntamentiFiltrati = this.escludiAppuntamentiPerUtenteCreatore(appuntamentiFiltrati, utentePerCuiTrovareAppuntamenti);
        appuntamentiFiltrati = this.escludiAppuntamentiPerPartecipante(appuntamentiFiltrati, utentePerCuiTrovareAppuntamenti);

        return appuntamentiFiltrati;
    }

    public List<Appuntamento> getAppuntamentiPerPartecipanteNonCreatore(UtentePolisportivaAbstract partecipanteNonCreatore){
        List<Appuntamento> appuntamenti = this.getListaAppuntamenti();
        appuntamenti = this.filtraAppuntamentiPerPartecipante(appuntamenti, partecipanteNonCreatore);
        appuntamenti = this.escludiAppuntamentiPerUtenteCreatore(appuntamenti, partecipanteNonCreatore);

        return appuntamenti;
    }


    private List<Appuntamento> filtraAppuntamentiPerDataOra(List<Appuntamento> listaAppuntamentiDaFiltrare, LocalDateTime dataOra){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.getDataOraInizioAppuntamento().isAfter(dataOra)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPerTipoPrenotazione(List<Appuntamento> listaAppuntamentiDaFiltrare, String tipoPrenotazione){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.getTipoPrenotazione().equals(tipoPrenotazione)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPending(List<Appuntamento> listaAppuntamentiDaFiltrare){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.isPending()){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteCreatore){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(((String)appuntamento.creatoDa().getProprieta().get("email")).equals((String)utenteCreatore.getProprieta().get("email"))){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> escludiAppuntamentiPerUtenteCreatore(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteCreatore){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(!((String)appuntamento.creatoDa().getProprieta().get("email")).equals((String)utenteCreatore.getProprieta().get("email"))){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> escludiAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteDiCuiVerificarePartecipazione){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(!appuntamento.utenteIsPartecipante(utenteDiCuiVerificarePartecipazione)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }

    private List<Appuntamento> filtraAppuntamentiPerPartecipante(List<Appuntamento> listaAppuntamentiDaFiltrare, UtentePolisportivaAbstract utenteDiCuiVerificarePartecipazione){
        List<Appuntamento> appuntamentiFiltrati = new ArrayList<Appuntamento>();
        for(Appuntamento appuntamento : listaAppuntamentiDaFiltrare){
            if(appuntamento.utenteIsPartecipante(utenteDiCuiVerificarePartecipazione)){
                appuntamentiFiltrati.add(appuntamento);
            }
        }
        return appuntamentiFiltrati;
    }
    
    public Appuntamento getAppuntamentoById(Integer idAppuntamento){
        for(Appuntamento appuntamento: this.getListaAppuntamenti()){
            if(appuntamento.getIdAppuntamento() == idAppuntamento){
                return appuntamento;
            }
        }
        return null;
    }

}
