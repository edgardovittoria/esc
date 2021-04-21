package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.costi.SpecificaDesc;

public interface SpecificaDescRepository extends JpaRepository<SpecificaDesc, Integer> {
    
}
