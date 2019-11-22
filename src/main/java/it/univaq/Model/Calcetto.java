package it.univaq.Model;

import java.util.ArrayList;

public class Calcetto extends Sport{

	private Calcetto calcettoInstance;

	private Calcetto(){

	}		

	public static Tennis getInstance(){
		if (this.calcettoInstance == null){
			this.calcettoInstance = new Calcetto();
		}

		return this.calcettoInstance;
	}
}
