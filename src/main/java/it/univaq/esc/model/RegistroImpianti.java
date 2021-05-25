package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import groovy.lang.Singleton;
import it.univaq.esc.model.prenotazioni.Appuntamento;
import it.univaq.esc.repository.AppuntamentoRepository;
import it.univaq.esc.repository.ImpiantoRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Singleton
@Getter @Setter
public class RegistroImpianti {

    @Setter(value = AccessLevel.PRIVATE)
    private ImpiantoRepository impiantoRepository;

    @Setter(value = AccessLevel.PRIVATE)
    private AppuntamentoRepository appuntamentoRepository;

    @Setter(value = AccessLevel.PRIVATE)
    private List<Impianto> listaImpiantiPolisportiva = new ArrayList<Impianto>();

    public RegistroImpianti(ImpiantoRepository impiantoRepository, AppuntamentoRepository appuntamentoRepository) {
    	this.setAppuntamentoRepository(appuntamentoRepository);
    	this.setImpiantoRepository(impiantoRepository);
    }

    

    @PostConstruct
    public void popola(){
        this.setListaImpiantiPolisportiva(getImpiantoRepository().findAll());
        for(Impianto impianto : this.getListaImpiantiPolisportiva()){
            List<Appuntamento> appuntamentiImpianto = new ArrayList<Appuntamento>();
            for(Appuntamento appuntamento : getAppuntamentoRepository().findAll()){
                if(impianto.getIdImpianto() == appuntamento.getImpiantoPrenotato().getIdImpianto()){
                    appuntamentiImpianto.add(appuntamento);
                }
            }
            impianto.getCalendarioAppuntamentiImpianto().setListaAppuntamenti(appuntamentiImpianto);
        }
        
    }

    public void aggiornaCalendarioImpianto(Impianto impianto, Calendario calendarioDaUnire){
        impianto.getCalendarioAppuntamentiImpianto().unisciCalendario(calendarioDaUnire);
    }



    public void aggiungiImpianto(Impianto impiantoDaAggiungere) {
        getListaImpiantiPolisportiva().add(impiantoDaAggiungere);
        getImpiantoRepository().save(impiantoDaAggiungere);
    }

   

    public void rimuoviImpianto(Impianto impiantoDaRimuovere){
        getListaImpiantiPolisportiva().remove(impiantoDaRimuovere);
        getImpiantoRepository().delete(impiantoDaRimuovere);
    }

    public Impianto getImpiantoByID(Integer idImpianto){
        for(Impianto impianto : this.getListaImpiantiPolisportiva()){
            if(impianto.getIdImpianto() == idImpianto){
                return impianto;
            }
        }
        return null;
    }
    
}