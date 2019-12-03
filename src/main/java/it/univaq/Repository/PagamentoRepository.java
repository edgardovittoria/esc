package it.univaq.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.Model.Pagamento;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
