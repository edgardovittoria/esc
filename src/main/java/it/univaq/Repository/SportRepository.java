package it.univaq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.Model.Sport;


public interface SportRepository extends JpaRepository<Sport, Integer>{

}
