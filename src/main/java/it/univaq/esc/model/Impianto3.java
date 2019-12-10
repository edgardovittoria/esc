package it.univaq.esc.model;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("impianto3")
public class Impianto3 extends Impianto{

	private static Impianto3 impianto3Instance;
	
	private Impianto3() {
		this.sport = new ArrayList<Sport>();
	}
	
	public static Impianto3 getInstance() {
		if(impianto3Instance == null) {
			impianto3Instance = new Impianto3();
		}
		
		return impianto3Instance;
	}
}
