package it.univaq.esc.dto;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class SportDTO {
    
    
    private String nome;

    private String descrizione;

    private Set<ImpiantoDTO> impianti;

    private Set<IstruttoreDTO> istruttori;

    public SportDTO(){
        
    }


    /**
     * @return String return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return String return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * @return List<ImpiantoDTO> return the impianti
     */
    public Set<ImpiantoDTO> getImpianti() {
        return impianti;
    }

    /**
     * @param impianti the impianti to set
     */
    public void setImpianti(Set<ImpiantoDTO> impianti) {
        this.impianti = impianti;
    }

    /**
     * @return List<IstruttoreDTO> return the istruttori
     */
    public Set<IstruttoreDTO> getIstruttori() {
        return istruttori;
    }

    /**
     * @param istruttori the istruttori to set
     */
    public void setIstruttori(Set<IstruttoreDTO> istruttori) {
        this.istruttori = istruttori;
    }

}