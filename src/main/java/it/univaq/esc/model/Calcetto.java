package it.univaq.esc.model;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("calcetto")
public class Calcetto extends Sport{

	
	private static Calcetto calcettoInstance;

	private Calcetto(){
		this.impianti = new ArrayList<Impianto>();
		this.istruttori = new ArrayList<Istruttore>();
		this.sportDescription = "calcetto";
	}		

	public static Calcetto getInstance(){
		if (calcettoInstance == null){
			calcettoInstance = new Calcetto();
		}

		return calcettoInstance;
	}
}
