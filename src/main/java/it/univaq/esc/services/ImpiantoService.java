package it.univaq.esc.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.dto.ImpiantoDTO;
import it.univaq.esc.model.IPrenotabile;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.repository.ImpiantoRepository;


@Service
public class ImpiantoService{
    
    @Autowired
    private ImpiantoRepository impiantiRepo;
    private Set<Impianto> impianti;

    private static ImpiantoService impiantoServiceInstance;
    

    private ImpiantoService(){

    }

    public static ImpiantoService getInstance(){
        if (impiantoServiceInstance == null) {
			impiantoServiceInstance = new ImpiantoService();
		}
		return  impiantoServiceInstance;
    }

    public boolean confermaPrenotazione(IPrenotabile impianto, List<Object> parametri){
        return impianto.confermaPrenotazione(parametri);
    }

    
    @PostConstruct
   	private void inizializzaSports(){
   		impianti = new HashSet<>(impiantiRepo.findAll());
       }
       

       public Set<Impianto> getAllImpianti(){
           return impianti;
       }

       public void addImpianto(Impianto impianto){
           impianti.add(impianto);
           impiantiRepo.save(impianto);
       }

       public static Set<ImpiantoDTO> toDTO(Set<Impianto> impianti) {
           Set<ImpiantoDTO> listaImpianti = new HashSet<ImpiantoDTO>();
           for (Impianto impianto : impianti) {
               ImpiantoDTO impiantoDTO = new ImpiantoDTO();
               impiantoDTO.setIndoor(impianto.isIndoor());
               impiantoDTO.setCalendario(impianto.getCalendario());
               listaImpianti.add(impiantoDTO);
           }
           return listaImpianti;

       }

}