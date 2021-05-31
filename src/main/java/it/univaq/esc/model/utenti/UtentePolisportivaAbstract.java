package it.univaq.esc.model.utenti;

import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class UtentePolisportivaAbstract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public abstract void setProprieta(Map<String, Object> mappaProprieta);
    public abstract Map<String, Object> getProprieta();
    public abstract List<String> getRuoliUtentePolisportiva();
    
    public abstract boolean isEqual(UtentePolisportivaAbstract utenteDaConfrontare);
    	
}
