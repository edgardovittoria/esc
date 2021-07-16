package it.univaq.esc.repository;

import it.univaq.esc.model.prenotazioni.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer>{
    
}
