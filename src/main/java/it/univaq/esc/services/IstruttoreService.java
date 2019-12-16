package it.univaq.esc.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.univaq.esc.model.Istruttore;
import it.univaq.esc.model.Lezione;
import it.univaq.esc.repository.IstruttoreRepository;

@Service
public class IstruttoreService {

	@Autowired
	private IstruttoreRepository istruttoreRepository;
	private ArrayList<Istruttore> istruttori;
	
	public void addLezione(Lezione lezione, Istruttore istruttore){
		istruttore.getLezioni().add(lezione);
	}
	
	public ArrayList<Istruttore> getIstruttori() {
		return istruttori;
	}

	public void setIstruttori(ArrayList<Istruttore> istruttori) {
		this.istruttori = istruttori;
	}
	
	public void addIstruttore(Istruttore istruttore) {
		this.istruttori.add(istruttore);
	}
}
