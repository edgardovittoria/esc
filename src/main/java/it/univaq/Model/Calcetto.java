package it.univaq.Model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


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
