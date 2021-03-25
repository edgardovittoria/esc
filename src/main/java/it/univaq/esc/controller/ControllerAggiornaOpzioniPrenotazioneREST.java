package it.univaq.esc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/impianti")
    public List<Impianto> getListaImpianti(){
        return controller.getImpiantiDisponibili(new Calendario());
    }
}
