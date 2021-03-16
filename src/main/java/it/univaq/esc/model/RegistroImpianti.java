package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

public class RegistroImpianti {

    private static RegistroImpianti instance = null;
    private List<Impianto> listaImpiantiPolisportiva = new ArrayList<Impianto>();

    private RegistroImpianti() {}


    public static RegistroImpianti getInstance() {
        if(instance == null){
            instance = new RegistroImpianti();
        
        }
        return instance;
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