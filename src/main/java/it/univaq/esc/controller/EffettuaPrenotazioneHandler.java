package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.*;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;


@Controller
@RequestMapping("/prenotazione")
public class EffettuaPrenotazioneHandler {


    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private RegistroUtentiPolisportiva registroSportivi;

    @Autowired
    private RegistroImpianti registroImpianti;


    private Prenotazione prenotazioneInAtto;
   
    

    public EffettuaPrenotazioneHandler() {}

    public List<UtentePolisportivaAbstract> getSportivi() {
        return this.registroSportivi.getListaUtenti();
    }

    public Prenotazione getPrenotazioneInAtto(){
        return this.prenotazioneInAtto;
    }

    private void setPrenotazioneInAtto(Prenotazione prenotazioneInAtto) {
        this.prenotazioneInAtto = prenotazioneInAtto;
    }

    public void avviaNuovaPrenotazione(UtentePolisportivaAbstract sportivo, String tipoPrenotazione) {
        int lastIdPrenotazione = this.registroPrenotazioni.getLastIdPrenotazione();
        PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione.getSpecifichePrenotazione(tipoPrenotazione);
        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione, prenotazioneSpecs));
        prenotazioneSpecs.setPrenotazioneAssociata(getPrenotazioneInAtto());
        getPrenotazioneInAtto().setSportivoPrenotante(sportivo);
        Appuntamento appuntamento = new Appuntamento();
        appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
        appuntamento.aggiungiPartecipante(sportivo);
       // getPrenotazioneInAtto().aggiungiPartecipanteAPrenotazioneSpecs(sportivo, prenotazioneSpecs);
        
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

    // public List<Date> getDateDisponibiliPerPrenotazione() {
    //     List<Prenotazione> prenotazioniEffettuate = registroPrenotazioni.getTutteLePrenotazioni();
    //     Calendario dateDisponibiliSportivoPrenotante = new Calendario();
    //     for(Prenotazione prenotazione : prenotazioniEffettuate) {
    //         if(prenotazione.getPrenotazioneSpecs().getSportivoPrenotante().getEmail().equals(prenotazioneInAtto.getPrenotazioneSpecs().getSportivoPrenotante().getEmail())){
    //             dateDisponibiliSportivoPrenotante.unisciCalendario(prenotazione.getCalendarioPrenotazione());
                
    //         }

    //     }
    //     return null;

    // }

    public PrenotazioneSpecsDTO getSpecifichePrenotazioneDTOByTipoPrenotazione(String tipoPrenotazione){
        return FactorySpecifichePrenotazione.getSpecifichePrenotazioneDTO(tipoPrenotazione);
    }

    public List<Impianto> getImpiantiDisponibiliByOrario(LocalDateTime oraInizio, LocalDateTime oraFine){
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();
        for(Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()){
            if(!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(oraInizio, oraFine)){
                listaImpiantiDisponibili.add(impianto);
            }
        }
        return listaImpiantiDisponibili;
    }

    //metodo che ricava impianti disponibili a partire dal calendario
    public List<Impianto> getImpiantiDisponibili(Calendario calendario){
        
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();        
        for(Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()){
            if(!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(calendario)){
                listaImpiantiDisponibili.add(impianto);
            }
        }
        return listaImpiantiDisponibili;
        
    }

    public List<UtentePolisportivaAbstract> getListaSportiviInvitabili(){
        Sport sportPrenotazioneInAtto = this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0).getSportAssociato();
        List<UtentePolisportivaAbstract> listaSportiviInvitabili = new ArrayList<UtentePolisportivaAbstract>();
        for(UtentePolisportivaAbstract sportivo : getSportivi()){
            if(((List<Sport>)sportivo.getProprieta().get("sportPraticati")).contains(sportPrenotazioneInAtto)){
                listaSportiviInvitabili.add(sportivo);
            }
        }
        return listaSportiviInvitabili;
    }

    // temporaneo
    public RegistroUtentiPolisportiva getRegistroSportivi(){
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


    public List<Prenotazione> getPrenotazioniByEmailSportivo(String email){
        return this.getRegistroPrenotazioni().getPrenotazioniByEmailSportivo(email);
    }

    
    

    // fine temporaneo
        
}