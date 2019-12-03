package it.univaq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.Model.Corso;


public interface CorsoRepository extends JpaRepository<Corso, Integer>{

}
