package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.notifiche.Notifica;

public interface NotificaRepository extends JpaRepository<Notifica, Long>{

}
