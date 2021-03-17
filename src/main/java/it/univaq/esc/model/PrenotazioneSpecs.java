package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

public abstract class PrenotazioneSpecs {

    private Sportivo sportivoPrenotante;
    private List<Sportivo> partecipanti = new ArrayList<Sportivo>();
    private Manutentore responsabilePrenotazione;
    private Calendario calendarioPrenotazione;
    private Sport sportAssociato;
    private List<Impianto> impiantiPrenotati = new ArrayList<Impianto>();
    
    public void setSportivoPrenotante(Sportivo sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public Sportivo getSportivoPrenotante(){
        return this.sportivoPrenotante;
    }

    public List<Sportivo> getListaPartecipanti(){
        return this.partecipanti;
    }

    public void aggiungiPartecipante(Sportivo sportivoPartecipante) {
        getListaPartecipanti().add(sportivoPartecipante);
    }

    public Manutentore getManutentore() {
        return this.responsabilePrenotazione;
    }

    private void associaManutentore(Manutentore manutentoreDaAssociare) {
        this.responsabilePrenotazione = manutentoreDaAssociare;
    }

    public void setCalendario(Calendario datePrenotate){
        this.calendarioPrenotazione = datePrenotate;
    }

    public Calendario getCalendarioPrenotazione() {
        return this.calendarioPrenotazione;
    }

    public void setSport(Sport sportScelto) {
        this.sportAssociato = sportScelto;
    }

    public Sport getSportAssociato() {
        return this.sportAssociato;
    }

    public List<Impianto> getImpiantiPrenotati() {
        return impiantiPrenotati;
    }

    public void aggiungiImpiantoPrenotato(Impianto impianto){
        this.impiantiPrenotati.add(impianto);
    }
}
