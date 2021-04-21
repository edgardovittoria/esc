package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.utenti.Sportivo;

public interface SportivoRepository extends JpaRepository<Sportivo, String> {
    
}
