package it.univaq.esc.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("impianto2")
public class Impianto2 extends Impianto{
	

	private static Impianto2 impianto2Instance;
	
	private Impianto2() {}
	
	public static Impianto2 getInstance() {
		if(impianto2Instance == null) {
			impianto2Instance = new Impianto2();
		}
		
		return impianto2Instance;
	}
 
}
