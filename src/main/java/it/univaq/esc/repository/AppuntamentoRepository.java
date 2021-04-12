package it.univaq.esc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Impianto;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Integer>{
    
    

    //public List<Appuntamento> findByPrenotazioneSpecsAppuntamento_ImpiantoPrenotato(Impianto impiantoPrenotato);

}
