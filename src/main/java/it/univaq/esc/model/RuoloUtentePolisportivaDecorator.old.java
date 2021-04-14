package it.univaq.esc.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
//@MappedSuperclass
@DiscriminatorColumn(name="RuoloUtenteDecorator")
//@SecondaryTable(name="USERS")
@DiscriminatorValue("utenteDecorator")
public abstract class RuoloUtentePolisportivaDecorator extends UtentePolisportivaAbstract{
    
    
    @OneToOne(cascade = CascadeType.ALL)
    private UtentePolisportivaAbstract utentePolisportiva;

    protected RuoloUtentePolisportivaDecorator(){}
    
    protected RuoloUtentePolisportivaDecorator(UtentePolisportivaAbstract utenteDaDecorare){
        this.setUtentePolisportiva(utenteDaDecorare);
    }
    
    /**
     * @return IUtentePolisportiva return the utentePolisportiva
     */
    protected UtentePolisportivaAbstract getUtentePolisportiva() {
        return utentePolisportiva;
    }

    /**
     * @param utentePolisportiva the utentePolisportiva to set
     */
    protected void setUtentePolisportiva(UtentePolisportivaAbstract utentePolisportiva) {
        this.utentePolisportiva = utentePolisportiva;
    }

}
