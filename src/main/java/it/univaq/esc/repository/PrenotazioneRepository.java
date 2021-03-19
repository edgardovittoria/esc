package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer>{
    
}
