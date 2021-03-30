package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.mappingObjectsViewController.FormPrenotaImpianto;
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
        getPrenotazioneInAtto().aggiungiPartecipante(sportivo);
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

    @PostMapping("/confermaPrenotazione")
    public String confermaPrenotazione(@ModelAttribute FormPrenotaImpianto formPrenotaImpianto, RedirectAttributes redirectAttributes){
        
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        for(Sport sport : this.getSportPraticabili()){
            if(sport.getNome().equals(formPrenotaImpianto.getSportSelezionato())){
                mappaValori.put("sport", sport);
            }
        }
        
        List<Impianto> impianti = new ArrayList<Impianto>();

        impianti.add(this.registroImpianti.getImpiantoByID(formPrenotaImpianto.getImpianto()));
        mappaValori.put("impianti", impianti);
        
        Calendario calendarioPrenotazione = new Calendario();
        LocalDateTime dataInizio = LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraInizio());
        System.out.println(dataInizio);
        LocalDateTime dataFine = LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraFine());
        System.out.println(dataFine);

        calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine , this.getPrenotazioneInAtto(), this.registroImpianti.getImpiantoByID(formPrenotaImpianto.getImpianto()));
        this.getPrenotazioneInAtto().setCalendario(calendarioPrenotazione);

        List<Sportivo> sportivi = new ArrayList<Sportivo>();
        for(String email : formPrenotaImpianto.getSportiviInvitati()){
            sportivi.add(this.getRegistroSportivi().getSportivoDaEmail(email));
        }

        mappaValori.put("invitati", sportivi);


        this.getPrenotazioneInAtto().getPrenotazioneSpecs().impostaValoriSpecifichePrenotazione(mappaValori);

        this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
        
        System.out.println("PRENOTAZIONE CONFERMATA");
        System.out.println("ID :"+this.prenotazioneInAtto.getIdPrenotazione());
        System.out.println("SPORTIVO PRENOTANTE :"+this.prenotazioneInAtto.getSportivoPrenotante().getEmail());
        System.out.println("PARTECIPANTI :"+this.prenotazioneInAtto.getListaPartecipanti().get(0).getEmail());
        System.out.println("DATA DI PRENOTAZIONE :"+this.prenotazioneInAtto.getCalendarioPrenotazione().getListaAppuntamenti().get(0).getDataOraInizioAppuntamento());
        System.out.println("INVITATI :"+this.prenotazioneInAtto.getPrenotazioneSpecs().getValoriSpecifichePrenotazione().get("invitati").toString());

        
        redirectAttributes.addFlashAttribute("message", "La prenotazione è stata registrata con successo!");
        // System.out.println("ID_IMPIANTO : "+formPrenotaImpianto.getImpianto());
        // System.out.println("DATA : "+formPrenotaImpianto.getLocalDataPrenotazione());
        // System.out.println("ORA_INIZIO : "+formPrenotaImpianto.getOraInizio());
        // System.out.println("SPORT : "+formPrenotaImpianto.getSportSelezionato());
        // System.out.println("INVITATI : "+formPrenotaImpianto.getSportiviInvitati().get(0));
        // System.out.println("DATA_LOCAL : "+LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraInizio()));
        return "redirect:/profilo";
    }

    

    // fine temporaneo
        
}