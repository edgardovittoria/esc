package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Pagamento;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
