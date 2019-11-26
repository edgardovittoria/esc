package it.univaq.Model;

import java.util.ArrayList;

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
