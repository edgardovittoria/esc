package it.univaq.esc.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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
