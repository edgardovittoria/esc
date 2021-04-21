package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public interface UtentePolisportivaAbstractRepository extends JpaRepository<UtentePolisportivaAbstract, Long> {
    
}
