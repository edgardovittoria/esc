package it.univaq.esc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.repository.ImpiantoRepository;


@Service
public class ImpiantoService{
    
    @Autowired
    private ImpiantoRepository impiantiRepo;


    public ImpiantoService(){

    }

    public boolean conferma(IPrenotabile impianto, List<Object> parametri){
        return impianto.confermaPrenotazione(parametri);
    }

    public List<Impianto> getAll(){
        return impiantiRepo.findAll();
    }

}