package it.univaq.esc.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tennis")
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
