package it.univaq.esc.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "specificheImpianto")
@NoArgsConstructor @Getter @Setter
public class ImpiantoSpecs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private int idSpecificaImpianto;
    
    @Enumerated(EnumType.STRING)
    private Pavimentazione TipoPavimentazione;
    @OneToOne()
    @JoinColumn()
    private Sport sportPraticabile;

   

    public ImpiantoSpecs(Pavimentazione TipoPavimentazione, Sport sportPraticabileNellImpianto) {
        this.TipoPavimentazione = TipoPavimentazione;
        this.sportPraticabile = sportPraticabileNellImpianto;
    }

    public boolean isEqual(ImpiantoSpecs specificaDaVerificare) {
    	if(getIdSpecificaImpianto()==specificaDaVerificare.getIdSpecificaImpianto()) {
    		return true;
    	}
    	return false;
    }
    
    public boolean haQuesta(String pavimentazione) {
    	return getTipoPavimentazione().toString().equals(pavimentazione);
    }
    
    public boolean haQuesto(String sport) {
    	return getSportPraticabile().isSuoQuesto(sport);
    }
   
}
