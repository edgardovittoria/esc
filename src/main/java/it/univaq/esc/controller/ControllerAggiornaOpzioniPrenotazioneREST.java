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

import it.univaq.esc.dtoObjects.PrenotazioneDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.prenotazioni.Prenotazione;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.RegistroPrenotazioni;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
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
    public @ResponseBody SportivoDTO getSportivo(@RequestParam(name = "email") String email){
        SportivoDTO sportivoDTO = new SportivoDTO();
        sportivoDTO.impostaValoriDTO(registroUtentiPolisportiva.getUtenteByEmail(email));
        return sportivoDTO;
    }

    @GetMapping("/prenotazioniSportivo")
    @CrossOrigin
    public @ResponseBody List<PrenotazioneDTO> getPrenotazioniSportivo(@RequestParam(name = "email") String email){
        List<PrenotazioneDTO> prenotazioni = new ArrayList<PrenotazioneDTO>();
        for(Prenotazione pren : registroPrenotazioni.getPrenotazioniByEmailSportivo(email)){
            PrenotazioneDTO prenDTO = new PrenotazioneDTO();
            List<Appuntamento> listaAppuntamnenti = new ArrayList<Appuntamento>();
            for(PrenotazioneSpecs spec : pren.getListaSpecifichePrenotazione()){
                listaAppuntamnenti.add(registroAppuntamenti.getAppuntamentoBySpecificaAssociata(spec));
            }
            Map<String, Object> mappa = new HashMap<String, Object>();
        mappa.put("prenotazione", pren);
        mappa.put("appuntamentiPrenotazione", listaAppuntamnenti);
        prenDTO.impostaValoriDTO(mappa);
            prenotazioni.add(prenDTO);
        }
        return prenotazioni;
        
    }

}
    
