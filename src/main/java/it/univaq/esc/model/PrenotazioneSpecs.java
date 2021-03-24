package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity(name = "prenotazioneSpecs")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PrenotazioneSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSpecificaPrenotazione;
    @ManyToOne()
    @JoinColumn()
    private Sportivo sportivoPrenotante;
    @ManyToMany()
    @JoinColumn()
    private List<Sportivo> partecipanti = new ArrayList<Sportivo>();
    @ManyToOne()
    @JoinColumn()
    private Manutentore responsabilePrenotazione;
    @OneToOne()
    private Calendario calendarioPrenotazione;
    @ManyToOne()
    @JoinColumn()
    private Sport sportAssociato;
    @ManyToMany()
    @JoinTable(name = "impianti_prenotati",
                joinColumns = {@JoinColumn(name="id_specifica_prenotazione")},
                inverseJoinColumns = {@JoinColumn(name="id_impianto_prenotato")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Impianto> impiantiPrenotati = new ArrayList<Impianto>();
    

    public void setSportivoPrenotante(Sportivo sportivoPrenotante) {
        this.sportivoPrenotante = sportivoPrenotante;
    }

    public Sportivo getSportivoPrenotante(){
        return this.sportivoPrenotante;
    }

    public List<Sportivo> getListaPartecipanti(){
        return this.partecipanti;
    }

    public void aggiungiPartecipante(Sportivo sportivoPartecipante) {
        getListaPartecipanti().add(sportivoPartecipante);
    }

    public Manutentore getManutentore() {
        return this.responsabilePrenotazione;
    }

    private void associaManutentore(Manutentore manutentoreDaAssociare) {
        this.responsabilePrenotazione = manutentoreDaAssociare;
    }

    public void setCalendario(Calendario datePrenotate){
        this.calendarioPrenotazione = datePrenotate;
    }

    public Calendario getCalendarioPrenotazione() {
        return this.calendarioPrenotazione;
    }

    public void setSport(Sport sportScelto) {
        this.sportAssociato = sportScelto;
    }

    public Sport getSportAssociato() {
        return this.sportAssociato;
    }

    public List<Impianto> getImpiantiPrenotati() {
        return impiantiPrenotati;
    }

    public void aggiungiImpiantoPrenotato(Impianto impianto){
        this.impiantiPrenotati.add(impianto);
    }
}
