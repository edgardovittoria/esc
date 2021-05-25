package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;

public interface QuotaPartecipazioneRepository extends JpaRepository<QuotaPartecipazione, Integer>{
    
}
