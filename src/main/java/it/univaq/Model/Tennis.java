package it.univaq.Model;

import java.util.ArrayList;

public class Tennis extends Sport{

	private static Tennis tennisInstance;

	private Tennis(){
		this.Impianti = new ArrayList<Impianto>();
		this.Istruttori = new ArrayList<Istruttore>();
	}		

	public static Tennis getInstance(){
		if (tennisInstance == null){
			tennisInstance = new Tennis();
		}

		return tennisInstance;
	}
}
