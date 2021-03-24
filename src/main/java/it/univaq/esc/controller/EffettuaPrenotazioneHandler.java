package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.*;
import it.univaq.esc.repository.PrenotazioneRepository;

@Controller
public class EffettuaPrenotazioneHandler {


    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private RegistroSportivi registroSportivi;

    @Autowired
    private RegistroImpianti registroImpianti;

    //questo serve perchè nel metodo confermaPrenotazione salviamo la prenotazione nel db
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    private Prenotazione prenotazioneInAtto;
    private PrenotazioneSpecs specifichePrenotazioneInAtto;
    private FactorySpecifichePrenotazione factorySpecifichePrenotazione = new FactorySpecifichePrenotazione();

    public EffettuaPrenotazioneHandler() {}

    public List<Sportivo> getSportivi() {
        return this.registroSportivi.getListaSportivi();
    }

    public Prenotazione getPrenotazioneInAtto(){
        return this.prenotazioneInAtto;
    }

    private void setPrenotazioneInAtto(Prenotazione prenotazioneInAtto) {
        this.prenotazioneInAtto = prenotazioneInAtto;
    }

    public void avviaNuovaPrenotazione(Sportivo sportivo, String tipoPrenotazione) {
        int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();
        PrenotazioneSpecs prenotazioneSpecs = this.factorySpecifichePrenotazione.getSpecifichePrenotazione(tipoPrenotazione);
        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione, sportivo, prenotazioneSpecs));
        this.specifichePrenotazioneInAtto = this.prenotazioneInAtto.getPrenotazioneSpecs();
    }

    public List<Sport> getSportPraticabili() {
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            for(ImpiantoSpecs specifica : impianto.getSpecificheImpianto()){
                setSportPraticabili.add(specifica.getSportPraticabile());
            }
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
        
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
        
        for(Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()){
            Calendario calendarioImpianto = new Calendario();
            for(PrenotazioneSpecs prenotazioneImpianto : impianto.getPrenotazioniPerImpianto()){
                calendarioImpianto.unisciCalendario(prenotazioneImpianto.getCalendarioPrenotazione());
            }
            if(!calendarioImpianto.sovrapponeA(calendario)){
                listaImpiantiDisponibili.add(impianto);
            }
        }

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
    public RegistroSportivi getRegistroSportivi(){
        return registroSportivi;
    }
    
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

    public void confermaPrenotazione(){
        this.getPrenotazioneInAtto().setConfermata();
        prenotazioneRepository.save(this.getPrenotazioneInAtto());
    }

    

    // fine temporaneo
        
}