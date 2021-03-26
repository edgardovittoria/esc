package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;

@Component
@Singleton
public class RegistroImpianti {

    @Autowired
    private ImpiantoRepository impiantoRepository;

    @Autowired 
    private AppuntamentoRepository appuntamentoRepository;

    //private static RegistroImpianti instance = null;
    private List<Impianto> listaImpiantiPolisportiva = new ArrayList<Impianto>();

    public RegistroImpianti() {}

    

    @PostConstruct
    public void popola(){
        this.getListaImpiantiPolisportiva().addAll(impiantoRepository.findAll());
        for(Impianto impianto : this.getListaImpiantiPolisportiva()){
            impianto.getCalendarioAppuntamentiImpianto().setListaAppuntamenti(appuntamentoRepository.findByImpiantoAppuntamento_IdImpianto(impianto.getIdImpianto()));
        }
        
    }



    public void aggiungiImpianto(Impianto impiantoDaAggiungere) {
        getListaImpiantiPolisportiva().add(impiantoDaAggiungere);
    }

    public List<Impianto> getListaImpiantiPolisportiva() {
        return this.listaImpiantiPolisportiva;
    }

    public void rimuoviImpianto(Impianto impiantoDaRimuovere){
        getListaImpiantiPolisportiva().remove(impiantoDaRimuovere);
    }
    
}