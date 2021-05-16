package it.univaq.esc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sports")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Sport {

    @Id
    private String nome;
    @Column
    private int numeroGiocatori;

      
}
