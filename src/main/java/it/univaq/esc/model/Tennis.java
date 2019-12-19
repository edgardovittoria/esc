package it.univaq.esc.model;

import java.util.HashSet;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("tennis")
public class Tennis extends Sport{

	private static Tennis tennisInstance;

	private Tennis(){
		this.impianti = new HashSet<Impianto>();
		this.istruttori = new HashSet<Istruttore>();
		this.sportDescription = "tennis";
	}		

	public static Tennis getInstance(){
		if (tennisInstance == null){
			tennisInstance = new Tennis();
		}

		return tennisInstance;
	}
}
