package it.univaq.esc.model;

import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@Entity


public abstract class UtentePolisportivaAbstract {
    
    @Id
    private Long id;

    public abstract void setProprieta(Map<String, Object> mappaProprieta);
    public abstract Map<String, Object> getProprieta();
    public abstract List<String> getRuoliUtentePolisportiva();
}
