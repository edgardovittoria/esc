package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.utenti.Manutentore;



public interface ManutentoreRepository extends JpaRepository<Manutentore, String>{
    
}
