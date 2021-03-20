package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Impianto;

public interface ImpiantoRepository extends JpaRepository<Impianto, Integer>{
    
}
