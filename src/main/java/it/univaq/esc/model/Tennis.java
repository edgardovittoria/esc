package it.univaq.esc.model;

import java.util.ArrayList;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("tennis")
public class Tennis extends Sport{

	private static Tennis tennisInstance;

	private Tennis(){
		this.Impianti = new ArrayList<Impianto>();
		this.Istruttori = new ArrayList<Istruttore>();
		this.sportDescription = "tennis";
	}		

	public static Tennis getInstance(){
		if (tennisInstance == null){
			tennisInstance = new Tennis();
		}

		return tennisInstance;
	}
}
