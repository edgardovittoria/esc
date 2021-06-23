package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.utenti.UtentePolisportiva;


public interface UtentePolisportivaRepository extends JpaRepository<UtentePolisportiva, String> {
    
}
