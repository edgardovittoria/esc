package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

public class Prenotazione {
    private int idPrenotazione;
    private boolean confermata = false;
    private int postiLiberi;
    private List<QuotaPartecipazione> quoteDiPartecipazione = new ArrayList<QuotaPartecipazione>();
    private Manutentore responsabilePrenotazione;
    private Calendario calendarioPrenotazione;
    private Sport sportAssociato;
    private Sportivo sportivoPrenotante;
    private List<Sportivo> partecipanti = new ArrayList<Sportivo>();
    private List<Sportivo> invitati = new ArrayList<Sportivo>();
    private List<Impianto> impiantiPrenotati = new ArrayList<Impianto>();


    public Prenotazione(int lastIdPrenotazione, Sportivo sportivoPrenotante) {
        setIdPrenotazione(lastIdPrenotazione);
        setSportivoPrenotante(sportivoPrenotante);
        aggiungiPartecipante(sportivoPrenotante);
        aggiungiQuotaPartecipazione(sportivoPrenotante, 0, false);
        
    }


    public int getIdPrenotazione(){
        return this.idPrenotazione;
    }

    private void setIdPrenotazione(int lastIdPrenotazione) {
        this.idPrenotazione = lastIdPrenotazione + 1;
    }

    private void setSportivoPrenotante(Sportivo sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public Sportivo getSportivoPrenotante(){
        return this.sportivoPrenotante;
    }

    public void aggiungiPartecipante(Sportivo sportivoPartecipante) {
        getListaPartecipanti().add(sportivoPartecipante);
    }

    public void aggiungiListaPartecipanti(List<Sportivo> listaPartecipantiDaAggiungere){
        getListaPartecipanti().addAll(listaPartecipantiDaAggiungere);
    }
    
    
    public List<Sportivo> getListaPartecipanti(){
        return this.partecipanti;
    }

    public List<Sportivo> getListaInvitati(){
        return this.invitati;
    }

    public void invitaSportivo(Sportivo sportivoDaInvitare) {
        getListaInvitati().add(sportivoDaInvitare);
    }

    public void invitaSportivi(List<Sportivo> listaSportiviDaInvitare) {
        getListaInvitati().addAll(listaSportiviDaInvitare);
    }

    public void setConfermata() {
        this.confermata = true;
    }

    public void setNonConfermata() {
        this.confermata = false;
    }

    public boolean isConfermata() {
        return this.confermata;
    }

    public int getPostiLiberi() {
        return this.postiLiberi;
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

    public void aggiungiQuotaPartecipazione(Sportivo sportivoDaAssociare, float costo, boolean isPagata){
        QuotaPartecipazione quotaDaAggiungere = new QuotaPartecipazione(isPagata, costo);
        quotaDaAggiungere.setSportivoAssociato(sportivoDaAssociare);
        getListaQuotePartecipazione().add(quotaDaAggiungere);
    }

    public List<QuotaPartecipazione> getListaQuotePartecipazione(){
        return this.quoteDiPartecipazione;
    }


    public List<Impianto> getImpiantiPrenotati() {
        return impiantiPrenotati;
    }

    public void aggiungiImpiantoPrenotato(Impianto impianto){
        this.impiantiPrenotati.add(impianto);
    }
}