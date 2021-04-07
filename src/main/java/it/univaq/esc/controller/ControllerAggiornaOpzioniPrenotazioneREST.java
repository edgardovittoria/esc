package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;



@RestController
@RequestMapping("/aggiornaOpzioni")
public class ControllerAggiornaOpzioniPrenotazioneREST {

    @Autowired
    private EffettuaPrenotazioneHandler controller;

    @GetMapping("/sportivo")
    @CrossOrigin
    public @ResponseBody SportivoDTO getSportivo(@RequestParam(name = "email") String email){
        SportivoDTO sportivoDTO = new SportivoDTO();
        sportivoDTO.impostaValoriDTO(controller.getRegistroSportivi().getSportivoDaEmail(email));
        return sportivoDTO;
    }

    @PostMapping("/impianti")
    public @ResponseBody HashMap<Integer, String> getListaImpianti(@RequestBody HashMap<String, Object> dati){        

        
        List<String> orario = (ArrayList<String>)dati.get("orario");
        
        List<Impianto> impiantiDisponibili =  controller.getImpiantiDisponibiliByOrario(LocalDateTime.parse(orario.get(0)), LocalDateTime.parse(orario.get(1)));
        HashMap<Integer, String> datiImpiantiDisponibili = new HashMap<Integer, String>();
        for(Impianto impianto : impiantiDisponibili){
            for(ImpiantoSpecs specifica : impianto.getSpecificheImpianto()){
                if(specifica.getSportPraticabile().getNome().equals(dati.get("sport"))){
                    datiImpiantiDisponibili.put(impianto.getIdImpianto(), impianto.getSpecificheImpianto().get(0).getTipoPavimentazione().toString());
                }
            }
            
        } 

        return datiImpiantiDisponibili;
    }
}
