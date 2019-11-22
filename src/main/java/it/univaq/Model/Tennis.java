package it.univaq.Model;

import java.util.ArrayList;

public class Tennis extends Sport{

	private Tennis tennisInstance;

	private Tennis(ArrayList<Impianto> impianti, ArrayList<Istruttore> istruttori){
		this.Impianti = impianti;
		this.Istruttori = istruttori;
	}		

	public static Tennis getInstance(){
		if (this.tennisInstance == null){
			this.tennisInstance = new Tennis(impianti, istruttori);
		}

		return this.tennisInstance;
	}
}
