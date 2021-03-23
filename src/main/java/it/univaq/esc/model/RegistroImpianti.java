package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.ImpiantoRepository;

@Component
public class RegistroImpianti {

    @Autowired
    private ImpiantoRepository impiantoRepository;

    //private static RegistroImpianti instance = null;
    private List<Impianto> listaImpiantiPolisportiva = new ArrayList<Impianto>();

    public RegistroImpianti() {}

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RegistroImpianti registroImpiantiSingleton(){
        return new RegistroImpianti();
    }

    @PostConstruct
    public void popola(){
        this.getListaImpiantiPolisportiva().addAll(impiantoRepository.findAll());
    }

    /*public static RegistroImpianti getInstance() {
        if(instance == null){
            instance = new RegistroImpianti();
        
        }
        return instance;
    }*/

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