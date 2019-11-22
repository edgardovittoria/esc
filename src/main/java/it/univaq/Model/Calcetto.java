package it.univaq.Model;

import java.util.ArrayList;

public class Calcetto extends Sport{

	private Calcetto calcettoInstance;

	private Calcetto(ArrayList<Impianto> impianti, ArrayList<Istruttore> istruttori){
		this.Impianti = impianti;
		this.Istruttori = istruttori;
	}		

	public static Tennis getInstance(){
		if (this.calcettoInstance == null){
			this.calcettoInstance = new Calcetto(impianti, istruttori);
		}

		return this.calcettoInstance;
	}
}
