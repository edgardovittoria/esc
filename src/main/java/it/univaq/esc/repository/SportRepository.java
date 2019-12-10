package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Sport;


public interface SportRepository extends JpaRepository<Sport, Integer>{

	Sport findBySportDescription(String descrizione);
}
