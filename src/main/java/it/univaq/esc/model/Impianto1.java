package it.univaq.esc.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("impianto1")
public class Impianto1 extends Impianto{
	
	private static Impianto1 impianto1Instance;
	
	private Impianto1() {}
	
	public static Impianto1 getInstance() {
		if(impianto1Instance == null) {
			impianto1Instance = new Impianto1();
		}
		
		return impianto1Instance;
	}

}
