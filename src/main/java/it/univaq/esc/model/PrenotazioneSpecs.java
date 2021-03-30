package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "prenotazioneSpecs")
@DiscriminatorValue("prenotazioneSpecsBase")
public class PrenotazioneSpecs extends Prenotabile {
  
    
    @ManyToOne()
    @JoinColumn()
    private Manutentore responsabilePrenotazione;
    @ManyToOne()
    @JoinColumn()
    private Sport sportAssociato;
    @ManyToMany()
    @JoinTable(name = "impianti_prenotati", joinColumns = {
            @JoinColumn(name = "id_specifica_prenotazione") }, inverseJoinColumns = {
                    @JoinColumn(name = "id_impianto_prenotato") })
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Impianto> impiantiPrenotati = new ArrayList<Impianto>();

    public PrenotazioneSpecs(){}


    public Manutentore getManutentore() {
        return this.responsabilePrenotazione;
    }

    public void associaManutentore(Manutentore manutentoreDaAssociare) {
        this.responsabilePrenotazione = manutentoreDaAssociare;
    }

    private void setSport(Sport sportScelto) {
        this.sportAssociato = sportScelto;
    }

    public Sport getSportAssociato() {
        return this.sportAssociato;
    }

    public List<Impianto> getImpiantiPrenotati() {
        return impiantiPrenotati;
    }

    public void aggiungiImpiantoPrenotato(Impianto impianto) {
        this.impiantiPrenotati.add(impianto);
    }

    public void aggiungiListaImpiantiPrenotati(List<Impianto> listaImpianti) {
        this.impiantiPrenotati.addAll(listaImpianti);
    }

    

    @Override
    public void impostaValoriSpecifichePrenotazione(HashMap<String, Object> mappaValori) {
        
        for(String chiave : mappaValori.keySet()){


            switch (chiave) {
                case "manutentore":
                    this.associaManutentore((Manutentore)mappaValori.get(chiave));
                    break;
                case "sport":
                
                    this.setSport((Sport)mappaValori.get(chiave));
                    break;
                case "impianti":
                
                    this.aggiungiListaImpiantiPrenotati((List<Impianto>)mappaValori.get(chiave));
                default:
                    break;
            }
        }

    }

    @Override
    public HashMap<String, Object> getValoriSpecifichePrenotazione() {
        HashMap<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("manutentore", this.getManutentore());
        mappaValori.put("sport", this.getSportAssociato());
        mappaValori.put("impianti", this.getImpiantiPrenotati());

        return mappaValori;
    }
}
