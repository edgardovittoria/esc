package it.univaq.esc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.model.Sport;
import it.univaq.esc.repository.SportRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/prova")
public class CRestProva {
    
    @Autowired
    private SportRepository sportRepo;

    public CRestProva(){

    }


    @GetMapping("/sports")
	public List<Sport> index(){
		return sportRepo.findAll();
	}
}