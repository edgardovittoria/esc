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

import it.univaq.esc.dtoObjects.FormPrenotaImpianto;
import it.univaq.esc.dtoObjects.PrenotazioneSpecsDTO;
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
        PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione.getSpecifichePrenotazione(tipoPrenotazione);
        setPrenotazioneInAtto(new Prenotazione(lastIdPrenotazione, prenotazioneSpecs));
        getPrenotazioneInAtto().setSportivoPrenotante(sportivo);
        getPrenotazioneInAtto().aggiungiPartecipanteAPrenotazioneSpecs(sportivo, prenotazioneSpecs);
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

    public List<Sportivo> getListaSportiviInvitabili(){
        Sport sportPrenotazioneInAtto = this.getPrenotazioneInAtto().getSportAssociato();
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


    public List<Prenotazione> getPrenotazioniByEmailSportivo(String email){
        return this.getRegistroPrenotazioni().getPrenotazioniByEmailSportivo(email);
    }

    @PostMapping("/confermaPrenotazione")
    public String confermaPrenotazione(@ModelAttribute FormPrenotaImpianto formPrenotaImpianto, RedirectAttributes redirectAttributes){
        
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        for(Sport sport : this.getSportPraticabili()){
            if(sport.getNome().equals(formPrenotaImpianto.getSportSelezionato())){
                this.prenotazioneInAtto.setSportAssociato(sport);
            }
        }
        
        this.prenotazioneInAtto.setImpiantoSpecifica(this.registroImpianti.getImpiantoByID(formPrenotaImpianto.getImpianto()), this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

        // impianti.add(this.registroImpianti.getImpiantoByID(formPrenotaImpianto.getImpianto()));
        // mappaValori.put("impianto", impianti);
        
        Calendario calendarioPrenotazione = new Calendario();
        LocalDateTime dataInizio = LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraInizio());
        LocalDateTime dataFine = LocalDateTime.of(formPrenotaImpianto.getLocalDataPrenotazione(), formPrenotaImpianto.getOraFine());

        calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine , this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
        this.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione, this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

        List<Sportivo> sportivi = new ArrayList<Sportivo>();
        for(String email : formPrenotaImpianto.getSportiviInvitati()){
            sportivi.add(this.getRegistroSportivi().getSportivoDaEmail(email));
        }

        mappaValori.put("invitati", sportivi);


        this.getPrenotazioneInAtto().impostaValoriSpecificheExtraSingolaPrenotazioneSpecs(mappaValori, this.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

        this.getRegistroPrenotazioni().aggiungiPrenotazione(this.getPrenotazioneInAtto());
        
        // System.out.println("PRENOTAZIONE CONFERMATA");
        // System.out.println("ID :"+this.prenotazioneInAtto.getIdPrenotazione());
        // System.out.println("SPORTIVO PRENOTANTE :"+this.prenotazioneInAtto.getSportivoPrenotante().getEmail());
        // System.out.println("PARTECIPANTI :"+this.prenotazioneInAtto.getListaPartecipanti().get(0).getEmail());
        // System.out.println("DATA DI PRENOTAZIONE :"+this.prenotazioneInAtto.getCalendarioPrenotazione().getListaAppuntamenti().get(0).getDataOraInizioAppuntamento());
        // System.out.println("INVITATI :"+this.prenotazioneInAtto.getPrenotazioneSpecs().getValoriSpecifichePrenotazione().get("invitati").toString());

        
        redirectAttributes.addFlashAttribute("message", "La prenotazione è stata registrata con successo!");
        return "redirect:/profilo";
    }

    

    // fine temporaneo
        
}