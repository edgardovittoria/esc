package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "istruttori")
public class Istruttore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idIstruttore;
	@Column
	private String nome;
	@Column
	private String cognome;
	@Column
	private Date dataNascita;
	@ManyToOne
	private Sport sportInsegnato;
	@OneToMany(mappedBy = "istruttore")
	private List<Lezione> lezioni;
	
	@ManyToMany(mappedBy = "istruttori")
	private List<Corso> corsiTenuti;
	
	

	public Istruttore() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Sport getSportInsegnato() {
		return this.sportInsegnato;
	}

	public void setSportInsegnato(Sport sportInsegnato) {
		this.sportInsegnato = sportInsegnato;
	}
	
	public List<Lezione> getLezioni() {
		return lezioni;
	}

	public void setLezioni(List<Lezione> lezioni) {
		this.lezioni = lezioni;
	}

	public void addLezione(Lezione lezione){
		this.lezioni.add(lezione);
	}
	
	

    /**
     * @return int return the IDIstruttore
     */
    public int getIdIstruttore() {
        return this.idIstruttore;
    }


    /**
     * @param idIstruttore the idIstruttore to set
     */
    public void setIdIstruttore(int idIstruttore) {
        this.idIstruttore = idIstruttore;
    }

    /**
     * @return List<Corso> return the corsiTenuti
     */
    public List<Corso> getCorsiTenuti() {
        return corsiTenuti;
    }

    /**
     * @param corsiTenuti the corsiTenuti to set
     */
    public void setCorsiTenuti(List<Corso> corsiTenuti) {
        this.corsiTenuti = corsiTenuti;
    }

}
