package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.*;


@Controller
@RequestMapping("/prenotazione")
public class EffettuaPrenotazioneHandler {


    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private RegistroSportivi registroSportivi;

    @Autowired
    private RegistroImpianti registroImpianti;

    private Prenotazione prenotazioneInAtto;
    private IPrenotabile specifichePrenotazioneInAtto;
    @Autowired
    private FactorySpecifichePrenotazione factorySpecifichePrenotazione;

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
        IPrenotabile prenotazioneSpecs = this.factorySpecifichePrenotazione.getSpecifichePrenotazione(tipoPrenotazione);
        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione, prenotazioneSpecs));
        getPrenotazioneInAtto().setSportivoPrenotante(sportivo);
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

    public List<Sportivo> getListaSportiviInvitabili(){
        Sport sportPrenotazioneInAtto = (Sport)this.getSpecifichePrenotazioneInAtto().getValoriSpecifichePrenotazione().get("sport");
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

    public IPrenotabile getSpecifichePrenotazioneInAtto() {
        return this.specifichePrenotazioneInAtto;
    }

    public void setSpecifichePrenotazioneInAtto(PrenotazioneSpecs specifichePrenotazioneInAtto) {
        this.specifichePrenotazioneInAtto = specifichePrenotazioneInAtto;
    }

    // @PostMapping("/confermaPrenotazione")
    // public ModelAndView confermaPrenotazione(@ModelAttribute FormPrenotaImpianto form){
    //     this.getPrenotazioneInAtto().getPrenotazioneSpecs().setSport(this.getSportPraticabili().stream().filter(sport -> sport.getNome().equals(form.getSport())).collect(Collectors.toList()).get(0));
    //     this.getPrenotazioneInAtto().getPrenotazioneSpecs().aggiungiImpiantoPrenotato(this.getRegistroImpianti().getImpiantoByID(form.getImpianto()));
    //     LocalDateTime dataOraInizio = LocalDateTime.of(form.getDataPrenotazione(), form.getOraInizio());
    //     LocalDateTime dataOraFine = LocalDateTime.of(form.getDataPrenotazione(), form.getOraFine());
    //     Calendario calendarioPrenotazione = new Calendario();
    //     calendarioPrenotazione.aggiungiAppuntamento(dataOraInizio, dataOraFine, this.prenotazioneInAtto, this.getRegistroImpianti().getImpiantoByID(form.getImpianto()));
    //     this.prenotazioneInAtto.setCalendario(calendarioPrenotazione);
        
    //     // for(String email : form.getSportiviInvitati()){
    //     //     this.getPrenotazioneInAtto().getPrenotazioneSpecs().setsp
    //     // }
        
    //     this.getPrenotazioneInAtto().setConfermata();
    //     prenotazioneRepository.save(this.getPrenotazioneInAtto());
    // }

    

    // fine temporaneo
        
}