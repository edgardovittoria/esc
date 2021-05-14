package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
import it.univaq.esc.model.RegistroAppuntamenti;

@RestController
@RequestMapping("/aggiornaOpzioni")
public class ControllerAggiornaOpzioniPrenotazioneREST {

    @Autowired
    private RegistroUtentiPolisportiva registroUtentiPolisportiva;

    @Autowired
    private RegistroPrenotazioni registroPrenotazioni;

    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    @GetMapping("/sportivo")
    @CrossOrigin
    public @ResponseBody SportivoDTO getSportivo(@RequestParam(name = "email") String email) {
        SportivoDTO sportivoDTO = new SportivoDTO();
        sportivoDTO.impostaValoriDTO(registroUtentiPolisportiva.getUtenteByEmail(email));
        return sportivoDTO;
    }

    @GetMapping("/prenotazioniEPartecipazioniSportivo")
    @CrossOrigin
    public @ResponseBody Map<String, Object> getAppuntamentiSportivo(@RequestParam(name = "email") String email) {
    
        List<PrenotazioneDTO> prenotazioni = new ArrayList<PrenotazioneDTO>();
        for (Prenotazione pren : registroPrenotazioni.getPrenotazioniByEmailSportivo(email)) {
            PrenotazioneDTO prenDTO = new PrenotazioneDTO();
            List<Appuntamento> listaAppuntamnenti = new ArrayList<Appuntamento>();
            for (PrenotazioneSpecs spec : pren.getListaSpecifichePrenotazione()) {
                listaAppuntamnenti.add(registroAppuntamenti.getAppuntamentoBySpecificaAssociata(spec));
            }
            Map<String, Object> mappa = new HashMap<String, Object>();
            mappa.put("prenotazione", pren);
            mappa.put("appuntamentiPrenotazione", listaAppuntamnenti);
            prenDTO.impostaValoriDTO(mappa);
            prenotazioni.add(prenDTO);
        }
        
        UtentePolisportivaAbstract sportivo = this.registroUtentiPolisportiva.getUtenteByEmail(email);
        List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();
        for(Appuntamento appuntamento : this.registroAppuntamenti.getAppuntamentiPerPartecipanteNonCreatore(sportivo)){
            AppuntamentoDTO appDTO = new AppuntamentoDTO();
            appDTO.impostaValoriDTO(appuntamento);
            appuntamenti.add(appDTO);
        };

        Map<String, Object> mappaPrenotazioniPartecipazioni = new HashMap<String, Object>();
        mappaPrenotazioniPartecipazioni.put("prenotazioniEffettuate", prenotazioni);
        mappaPrenotazioniPartecipazioni.put("partecipazioni", appuntamenti);

        return mappaPrenotazioniPartecipazioni;

    }

}
