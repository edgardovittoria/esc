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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "istruttori")
public class Istruttore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IDIstruttore;
	@Column
	private String nome;
	@Column
	private String cognome;
	@Column
	private Date dataNascita;
	@OneToMany(mappedBy = "istruttore")
	private List<Lezione> lezioni;
	
	@ManyToMany(mappedBy = "istruttori")
	private List<Corso> corsiTenuti;
	
	

	public Istruttore() {
		this.lezioni = new ArrayList<Lezione>();
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
    public int getIDIstruttore() {
        return IDIstruttore;
    }

    /**
     * @param IDIstruttore the IDIstruttore to set
     */
    public void setIDIstruttore(int IDIstruttore) {
        this.IDIstruttore = IDIstruttore;
    }

}
