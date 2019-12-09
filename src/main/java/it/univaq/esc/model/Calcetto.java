package it.univaq.esc.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "calcetto")
public class Calcetto extends Sport{

	
	private static Calcetto calcettoInstance;

	private Calcetto(){
		this.Impianti = new ArrayList<Impianto>();
		this.Istruttori = new ArrayList<Istruttore>();
	}		

	public static Calcetto getInstance(){
		if (calcettoInstance == null){
			calcettoInstance = new Calcetto();
		}

		return calcettoInstance;
	}
}
