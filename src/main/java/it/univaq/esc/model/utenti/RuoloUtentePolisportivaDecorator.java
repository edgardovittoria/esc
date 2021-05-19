package it.univaq.esc.model.utenti;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="RuoloUtenteDecorator")
@DiscriminatorValue("utenteDecorator")
@NoArgsConstructor(access = AccessLevel.PROTECTED) 
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RuoloUtentePolisportivaDecorator extends UtentePolisportivaAbstract{
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @Getter(value = AccessLevel.PROTECTED)
    @Setter(value = AccessLevel.PROTECTED)
    private UtentePolisportivaAbstract utentePolisportiva;

    @Override
    public boolean isEqual(UtentePolisportivaAbstract utenteDaConfrontare) {
    	return this.getUtentePolisportiva().isEqual(utenteDaConfrontare);
    }

}
