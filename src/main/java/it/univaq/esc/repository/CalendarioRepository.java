package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Calendario;

public interface CalendarioRepository extends JpaRepository<Calendario, Integer>{
    
}
