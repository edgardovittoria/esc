package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("calcetto")
public class Calcetto extends Sport{

	
	private static Calcetto calcettoInstance;

	private Calcetto(){
		this.impianti = new HashSet<Impianto>();
		this.istruttori = new HashSet<Istruttore>();
		this.sportDescription = "calcetto";
	}		

	public static Calcetto getInstance(){
		if (calcettoInstance == null){
			calcettoInstance = new Calcetto();
		}

		return calcettoInstance;
	}
}
