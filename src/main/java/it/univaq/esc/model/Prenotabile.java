package it.univaq.esc.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="iprenotabile_type")
@Table(name="IPrenotabile")
public abstract class Prenotabile implements IPrenotabile{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    }