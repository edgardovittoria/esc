package it.univaq.esc.repository;

import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotaPartecipazioneRepository extends JpaRepository<QuotaPartecipazione, Integer>{
    
}
