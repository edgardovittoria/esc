package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class FormPrenotaLezioneDTO implements IFormPrenotabile{

    private String sportSelezionato = "tennis";

    private List<OrarioAppuntamento> orariSelezionati = new ArrayList<OrarioAppuntamento>();

    private List<ImpiantoSelezionato> impianti = new ArrayList<ImpiantoSelezionato>();

    private List<IstruttoreSelezionato> istruttori = new ArrayList<IstruttoreSelezionato>();

    public FormPrenotaLezioneDTO(){}

    public List<IstruttoreSelezionato> getIstruttori() {
        return istruttori;
    }

    public void setIstruttori(List<IstruttoreSelezionato> istruttori) {
        this.istruttori = istruttori;
    }

    public List<ImpiantoSelezionato> getImpianti() {
        return impianti;
    }


    public void setImpianti(List<ImpiantoSelezionato> impianti) {
        this.impianti = impianti;
    }


    public List<OrarioAppuntamento> getOrariSelezionati() {
        return orariSelezionati;
    }


    public void setOrariSelezionati(List<OrarioAppuntamento> orariSelezionati) {
        this.orariSelezionati = orariSelezionati;
    }


    public String getSportSelezionato() {
        return sportSelezionato;
    }


    public void setSportSelezionato(String sportSelezionato) {
        this.sportSelezionato = sportSelezionato;
    }

    @Override
    public HashMap<String, Object> getValoriForm() {
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sport", this.getSportSelezionato());
        mappaValori.put("listaOrariAppuntamenti", this.getOrariSelezionati());
        mappaValori.put("impianti", this.getImpianti());
        mappaValori.put("istruttori", this.getIstruttori());

        return mappaValori;
    }
    
}
