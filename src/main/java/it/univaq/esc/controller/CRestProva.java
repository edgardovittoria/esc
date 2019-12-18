package it.univaq.esc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.SportRepository;
import it.univaq.esc.services.ImpiantoService;
import it.univaq.esc.services.IstruttoreService;
import it.univaq.esc.services.PrenotazioneService;
import it.univaq.esc.services.SportService;
import it.univaq.esc.services.SportivoService;
import it.univaq.esc.utility.SimpleFactory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/prova")
public class CRestProva {
    
    
    @Autowired
	private SportService sportService;
	@Autowired
	private ImpiantoService impiantoService;
	@Autowired
	private PrenotazioneService prenotazioneService;
	@Autowired
	private SportivoService sportivoService;
	@Autowired
	private IstruttoreService istruttoreService;
	@Autowired
	private SimpleFactory simpleFactory;

    public CRestProva(){

    }


    @GetMapping("/sports")
	public Model index(Model results){
        return this.simpleFactory.getOpzioni(impiantoService, sportivoService, sportService, istruttoreService, prenotazioneService, results);

	}
}