package it.univaq.esc.model;

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
@DiscriminatorValue(value="TipoUtente")
public abstract class RuoloUtentePolisportivaDecorator extends UtentePolisportivaAbstract{
    
    
    @OneToOne
    private UtentePolisportivaAbstract utentePolisportiva;

    

    
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
