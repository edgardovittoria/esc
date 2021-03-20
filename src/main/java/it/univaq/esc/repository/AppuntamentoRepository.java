package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Appuntamento;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Integer>{
    
}
