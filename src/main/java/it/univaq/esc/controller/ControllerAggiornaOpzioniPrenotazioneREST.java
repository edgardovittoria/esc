package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;


@RestController
@RequestMapping("/aggiornaOpzioni")
public class ControllerAggiornaOpzioniPrenotazioneREST {

    @Autowired
    private EffettuaPrenotazioneHandler controller;

    @PostMapping("/impianti")
    public List<Impianto> getListaImpianti(@RequestBody List<LocalDateTime> dataOraInizioFine){        
        return controller.getImpiantiDisponibiliByOrario(dataOraInizioFine.get(0), dataOraInizioFine.get(1));
    }
}
