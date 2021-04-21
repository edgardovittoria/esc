package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
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

   

}
