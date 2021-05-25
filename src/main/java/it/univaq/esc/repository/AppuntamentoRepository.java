package it.univaq.esc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.univaq.esc.model.prenotazioni.Appuntamento;


public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Integer>{
    
    

    //public List<Appuntamento> findByPrenotazioneSpecsAppuntamento_ImpiantoPrenotato(Impianto impiantoPrenotato);

    public Appuntamento findByPrenotazioneSpecsAppuntamento_Id(Long idPrenotazioneSPecs);
}
