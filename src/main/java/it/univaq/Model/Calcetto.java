package it.univaq.Model;

import java.util.ArrayList;

public class Calcetto extends Sport{

	private Calcetto calcettoInstance;

	private Calcetto(){
		this.Impianti = new ArrayList<Impianto>();
		this.Istruttori = new ArrayList<Istruttore>();
	}		

	public static Tennis getInstance(){
		if (this.calcettoInstance == null){
			this.calcettoInstance = new Calcetto();
		}

		return this.calcettoInstance;
	}
}
