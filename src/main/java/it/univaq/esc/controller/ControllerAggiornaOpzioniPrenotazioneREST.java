package it.univaq.esc.controller;


import java.util.ArrayList;
import java.util.List;

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
import it.univaq.esc.model.Prenotazione;
import it.univaq.esc.model.PrenotazioneSpecs;
import it.univaq.esc.model.RegistroAppuntamenti;




@RestController
@RequestMapping("/aggiornaOpzioni")
public class ControllerAggiornaOpzioniPrenotazioneREST {

    @Autowired
    private EffettuaPrenotazioneHandler controller;

    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    @GetMapping("/sportivo")
    @CrossOrigin
    public @ResponseBody SportivoDTO getSportivo(@RequestParam(name = "email") String email){
        SportivoDTO sportivoDTO = new SportivoDTO();
        sportivoDTO.impostaValoriDTO(controller.getRegistroSportivi().getSportivoDaEmail(email));
        return sportivoDTO;
    }

    @GetMapping("/prenotazioniSportivo")
    @CrossOrigin
    public @ResponseBody List<PrenotazioneDTO> getPrenotazioniSportivo(@RequestParam(name = "email") String email){
        List<PrenotazioneDTO> prenotazioni = new ArrayList<PrenotazioneDTO>();
        for(Prenotazione pren : controller.getRegistroPrenotazioni().getPrenotazioniByEmailSportivo(email)){
            PrenotazioneDTO prenDTO = new PrenotazioneDTO();
            List<Appuntamento> listaAppuntamnenti = new ArrayList<Appuntamento>();
            for(PrenotazioneSpecs spec : pren.getListaSpecifichePrenotazione()){
                listaAppuntamnenti.add(registroAppuntamenti.getAppuntamentoBySpecificaAssociata(spec));
            }
            prenDTO.impostaValoriDTO(pren, listaAppuntamnenti);
            prenotazioni.add(prenDTO);
        }
        return prenotazioni;
        
    }

}
    
