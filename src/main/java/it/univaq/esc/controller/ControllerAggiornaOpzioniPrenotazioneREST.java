package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.Impianto;


@RestController
@RequestMapping("/aggiornaOpzioni")
public class ControllerAggiornaOpzioniPrenotazioneREST {

    @Autowired
    private EffettuaPrenotazioneHandler controller;

    @PostMapping("/impianti")
    public @ResponseBody HashMap<Integer, String> getListaImpianti(@RequestBody List<LocalDateTime> dataOraInizioFine){        
        List<Impianto> impiantiDisponibili =  controller.getImpiantiDisponibiliByOrario(dataOraInizioFine.get(0), dataOraInizioFine.get(1));
        HashMap<Integer, String> datiImpiantiDisponibili = new HashMap<Integer, String>();
        for(Impianto impianto : impiantiDisponibili){
            datiImpiantiDisponibili.put(impianto.getIdImpianto(), impianto.getSpecificheImpianto().get(0).getTipoPavimentazione().toString());
        } 

        return datiImpiantiDisponibili;
    }
}
