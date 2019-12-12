package it.univaq.esc.model;

import java.util.ArrayList;

import org.springframework.stereotype.Component;
@Component
public class RegistroIstruttori {

	private ArrayList<Istruttore> istruttori;
	private static RegistroIstruttori registroIstruttoriInstanceIstruttori;
	
	private RegistroIstruttori() {
		this.istruttori = new ArrayList<Istruttore>();
	}
	
	public static RegistroIstruttori getInstance() {
		if(registroIstruttoriInstanceIstruttori == null) {
			registroIstruttoriInstanceIstruttori = new RegistroIstruttori();
		}
		return registroIstruttoriInstanceIstruttori;
	}

	public ArrayList<Istruttore> getIstruttori() {
		return istruttori;
	}

	public void setIstruttori(ArrayList<Istruttore> istruttori) {
		this.istruttori = istruttori;
	}
	
	public void addIstruttor(Istruttore istruttore) {
		this.istruttori.add(istruttore);
	}
}
