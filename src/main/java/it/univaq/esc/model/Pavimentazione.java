package it.univaq.esc.model;

import java.util.ArrayList;
import java.util.List;

public enum Pavimentazione {

     SINTETICO,
     TERRA_BATTUTA,
     CEMENTO;
   
	
	public static List<String> getListaPavimentazioniComeStringhe(){
		List<String> listaPavimentazioni = new ArrayList<String>();
		listaPavimentazioni.add(SINTETICO.toString());
		listaPavimentazioni.add(TERRA_BATTUTA.toString());
		listaPavimentazioni.add(CEMENTO.toString());
		return listaPavimentazioni;
	}
}
