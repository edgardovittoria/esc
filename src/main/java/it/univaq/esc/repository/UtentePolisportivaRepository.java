package it.univaq.esc.repository;

import it.univaq.esc.model.utenti.UtentePolisportiva;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UtentePolisportivaRepository extends JpaRepository<UtentePolisportiva, String> {
    
}
