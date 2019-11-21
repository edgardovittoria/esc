package it.univaq.Model;

import java.util.ArrayList;

public class Calcetto implements Sport{

	private ArrayList<Impianto> impianti;
	private ArrayList<Istruttore> istruttori;
	
	@Override
	public ArrayList<Impianto> getImpianti(){
		return this.impianti;
	}

	@Override
	public ArrayList<Istruttore> getIstruttori() {
		return this.istruttori;
	}
}
