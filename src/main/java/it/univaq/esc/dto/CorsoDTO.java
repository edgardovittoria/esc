package it.univaq.esc.dto;

import java.util.List;

import org.javamoney.moneta.Money;



public class CorsoDTO {
    private int numMaxPartecipanti;
	
	private int numMinPartecipanti;
	
	private Money costoCorso;
	
	private List<LezioneDTO> lezioni;
	
	private List<IstruttoreDTO> istruttori;
	

    /**
     * @return int return the numMaxPartecipanti
     */
    public int getNumMaxPartecipanti() {
        return numMaxPartecipanti;
    }

    /**
     * @param numMaxPartecipanti the numMaxPartecipanti to set
     */
    public void setNumMaxPartecipanti(int numMaxPartecipanti) {
        this.numMaxPartecipanti = numMaxPartecipanti;
    }

    /**
     * @return int return the numMinPartecipanti
     */
    public int getNumMinPartecipanti() {
        return numMinPartecipanti;
    }

    /**
     * @param numMinPartecipanti the numMinPartecipanti to set
     */
    public void setNumMinPartecipanti(int numMinPartecipanti) {
        this.numMinPartecipanti = numMinPartecipanti;
    }

    /**
     * @return Money return the costoCorso
     */
    public Money getCostoCorso() {
        return costoCorso;
    }

    /**
     * @param costoCorso the costoCorso to set
     */
    public void setCostoCorso(Money costoCorso) {
        this.costoCorso = costoCorso;
    }

    /**
     * @return List<Lezione> return the lezioni
     */
    public List<LezioneDTO> getLezioni() {
        return lezioni;
    }

    /**
     * @param lezioni the lezioni to set
     */
    public void setLezioni(List<LezioneDTO> lezioni) {
        this.lezioni = lezioni;
    }

    /**
     * @return List<Istruttore> return the istruttori
     */
    public List<IstruttoreDTO> getIstruttori() {
        return istruttori;
    }

    /**
     * @param istruttori the istruttori to set
     */
    public void setIstruttori(List<IstruttoreDTO> istruttori) {
        this.istruttori = istruttori;
    }

}