package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.univaq.esc.model.*;


public class EffettuaPrenotazioneHandler {

    private RegistroPrenotazioni registroPrenotazioni = RegistroPrenotazioni.getInstance();
    private RegistroSportivi registroSportivi = RegistroSportivi.getInstance();
    private RegistroImpianti registroImpianti = RegistroImpianti.getInstance();
    private Prenotazione prenotazioneInAtto;
    private PrenotazioneSpecs specifichePrenotazioneInAtto;

    public EffettuaPrenotazioneHandler() {
    }

    public List<Sportivo> getSportivi() {
        return this.registroSportivi.getListaSportivi();
    }

    public Prenotazione getPrenotazioneInAtto(){
        return this.prenotazioneInAtto;
    }

    private void setPrenotazioneInAtto(Prenotazione prenotazioneInAtto) {
        this.prenotazioneInAtto = prenotazioneInAtto;
    }

    public void avviaNuovaPrenotazione(Sportivo sportivo, TipiPrenotazione tipoPrenotazione) {
        int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();
        PrenotazioneSpecs prenotazioneSpecs = new PrenotazioneImpiantoSpecs();
        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione, sportivo, prenotazioneSpecs));
        this.specifichePrenotazioneInAtto = this.prenotazioneInAtto.getPrenotazioneSpecs();
    }

    public List<Sport> getSportPraticabili() {
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            setSportPraticabili.addAll(impianto.getCaratteristicheImpianto().getSportPraticabili());
        }
        listaSportPraticabili.addAll(setSportPraticabili);
        return listaSportPraticabili;

    }

    public List<Date> getDateDisponibiliPerPrenotazione() {
        List<Prenotazione> prenotazioniEffettuate = registroPrenotazioni.getTutteLePrenotazioni();
        Calendario dateDisponibiliSportivoPrenotante = new Calendario();
        for(Prenotazione prenotazione : prenotazioniEffettuate) {
            if(prenotazione.getPrenotazioneSpecs().getSportivoPrenotante().getEmail().equals(prenotazioneInAtto.getPrenotazioneSpecs().getSportivoPrenotante().getEmail())){
                dateDisponibiliSportivoPrenotante.unisciCalendario(prenotazione.getPrenotazioneSpecs().getCalendarioPrenotazione());
                
            }

        }
        return null;

    }

    //metodo che ricava impianti disponibili a partire dal calendario
    public List<Impianto> getImpiantiDisponibili(Calendario calendario){
        Set<Impianto> impiantiDisponibili = new HashSet<Impianto>();
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
        for (Prenotazione prenotazione : registroPrenotazioni.getTutteLePrenotazioni()){
            if(!prenotazione.getPrenotazioneSpecs().getCalendarioPrenotazione().sovrapponeA(calendario)){
                impiantiDisponibili.addAll(prenotazione.getPrenotazioneSpecs().getImpiantiPrenotati());
            }
        }
        listaImpiantiDisponibili.addAll(impiantiDisponibili);
        return listaImpiantiDisponibili;
    }

    public List<Sportivo> getListaSportiviInvitabili(){
        Sport sportPrenotazioneInAtto = this.getSpecifichePrenotazioneInAtto().getSportAssociato();
        List<Sportivo> listaSportiviInvitabili = new ArrayList<Sportivo>();
        for(Sportivo sportivo : getSportivi()){
            if(sportivo.getSportPraticatiDalloSportivo().contains(sportPrenotazioneInAtto)){
                listaSportiviInvitabili.add(sportivo);
            }
        }
        return listaSportiviInvitabili;
    }

    // temporaneo
    public RegistroImpianti getRegistroImpianti() {
        return registroImpianti;
    }

    public void setRegistroImpianti(RegistroImpianti registroImpianti) {
        this.registroImpianti = registroImpianti;
    }

    public RegistroPrenotazioni getRegistroPrenotazioni() {
        return this.registroPrenotazioni;
    }

    public void setRegistroPrenotazioni(RegistroPrenotazioni registroPrenotazioni) {
        this.registroPrenotazioni = registroPrenotazioni;
    }

    public PrenotazioneSpecs getSpecifichePrenotazioneInAtto() {
        return specifichePrenotazioneInAtto;
    }

    public void setSpecifichePrenotazioneInAtto(PrenotazioneSpecs specifichePrenotazioneInAtto) {
        this.specifichePrenotazioneInAtto = specifichePrenotazioneInAtto;
    }

    

    // fine temporaneo
        
}