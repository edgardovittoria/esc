package it.univaq.esc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sports")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Sport {

    @Id
    private String nome;
    @Column
    private Integer numeroGiocatoriPerIncontro;
    
    @Column
    private Integer numeroMinimoGiocatoriPerSquadra;

      
    public boolean isEqual(Sport sportDaConfrontare) {
    	if(getNome().equals(sportDaConfrontare.getNome())) {
    		return true;
    	}
    	return false;
    }
    
    public boolean isSuoQuesto(String nome) {
    	return getNome().equals(nome);
    }
}
