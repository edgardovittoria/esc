package it.univaq.esc.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.repository.ImpiantoRepository;


@Service
public class ImpiantoService{
    
    @Autowired
    private ImpiantoRepository impiantiRepo;
    private List<Impianto> impianti;
    

    public boolean conferma(IPrenotabile impianto, List<Object> parametri){
        return impianto.confermaPrenotazione(parametri);
    }

    
    @PostConstruct
   	private void inizializzaSports(){
   		impianti = impiantiRepo.findAll();
       }
       

       public List<Impianto> getAllImpianti(){
           return impianti;
       }

       public void addImpianto(Impianto impianto){
           impianti.add(impianto);
       }

}