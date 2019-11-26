package it.univaq.Model;

import java.util.ArrayList;

public class Tennis extends Sport{

	private Tennis tennisInstance;

	private Tennis(){
		this.Impianti = new ArrayList<Impianto>();
		this.Istruttori = new ArrayList<Istruttore>();
	}		

	public static Tennis getInstance(){
		if (this.tennisInstance == null){
			this.tennisInstance = new Tennis();
		}

		return this.tennisInstance;
	}
}
