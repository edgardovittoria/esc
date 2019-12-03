package it.univaq.Model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class Impianto1 extends Impianto{
	
	
	private static Impianto1 impianto1Instance;
	
	private Impianto1() {
		this.sport = new ArrayList<Sport>();
		//bisogna settare gli altri attributi??
	}
	
	public static Impianto1 getInstance() {
		if(impianto1Instance == null) {
			impianto1Instance = new Impianto1();
		}
		
		return impianto1Instance;
	}

}
