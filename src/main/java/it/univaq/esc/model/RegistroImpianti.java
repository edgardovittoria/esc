package it.univaq.esc.model;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.repository.ImpiantoRepository;

@Component
public class RegistroImpianti {
    private List<Impianto> impianti;
    private static RegistroImpianti RegistroImpiantiInstance;

    @Autowired
    private ImpiantoRepository impRepo;


    private RegistroImpianti(){

    }


    public static RegistroImpianti getInstance() {
		if(RegistroImpiantiInstance == null){
			RegistroImpiantiInstance = new RegistroImpianti();
		}
        return RegistroImpiantiInstance;
    }


    @PostConstruct
	private void inizializzaSports(){
		impianti = impRepo.findAll();
    }
    

    public List<Impianto> getAllImpianti(){
        return impianti;
    }

    public void addImpianto(Impianto impianto){
        impianti.add(impianto);
    }
}