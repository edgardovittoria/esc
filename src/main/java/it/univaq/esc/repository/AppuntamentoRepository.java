package it.univaq.esc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.Appuntamento;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Integer>{
    
    public List<Appuntamento> findByImpiantoAppuntamento_IdImpianto(int idImpianto);

    public List<Appuntamento> findByPrenotazioneAppuntamento_IdPrenotazione(int idPrenotazione);

}
