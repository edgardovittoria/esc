package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormPrenotaImpiantoRicorrente implements IFormPrenotabile{
    private String sportSelezionato = "tennis";

    private List<OrarioAppuntamento> orariSelezionati = new ArrayList<OrarioAppuntamento>();

    private List<ImpiantoSelezionato> impianti = new ArrayList<ImpiantoSelezionato>();

    private List<String> sportiviInvitati;

    private Integer postiLiberi;

    private Integer numeroGiocatoriNonIscritti;


    public FormPrenotaImpiantoRicorrente(){}


    public Integer getNumeroGiocatoriNonIscritti() {
        return numeroGiocatoriNonIscritti;
    }


    public void setNumeroGiocatoriNonIscritti(Integer numeroGiocatoriNonIscritti) {
        this.numeroGiocatoriNonIscritti = numeroGiocatoriNonIscritti;
    }


    public Integer getPostiLiberi() {
        return postiLiberi;
    }


    public void setPostiLiberi(Integer postiLiberi) {
        this.postiLiberi = postiLiberi;
    }


    public List<String> getSportiviInvitati() {
        return sportiviInvitati;
    }


    public void setSportiviInvitati(List<String> sportiviInvitati) {
        this.sportiviInvitati = sportiviInvitati;
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
        mappaValori.put("invitati", this.getSportiviInvitati());
        mappaValori.put("postiLiberi", this.getPostiLiberi());
        mappaValori.put("numeroPartecipantiNonIscritti", this.getNumeroGiocatoriNonIscritti());

        return mappaValori;
    }


}
