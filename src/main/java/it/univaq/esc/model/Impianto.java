package it.univaq.esc.model;


public class Impianto {
    
    private int idImpianto;
    private ImpiantoSpecs caratteristicheImpianto;
    

    public Impianto() {}

    public Impianto(int idImpianto, ImpiantoSpecs caratteristicheImpianto) {
        this.idImpianto = idImpianto;
        this.caratteristicheImpianto = caratteristicheImpianto;
    }


    public int getIdImpianto() {
        return idImpianto;
    }

    public boolean isIndoor(){
        return this.caratteristicheImpianto.isIndoor();   
    }

    public Pavimentazione getTipoPavimentazione(){
          return this.caratteristicheImpianto.getTipoPavimentazione();
      }

    

    public void aggiungiSportPraticabile(Sport sportPraticabile){
        this.caratteristicheImpianto.aggiungiSportPraticabile(sportPraticabile);
    }

    public ImpiantoSpecs getCaratteristicheImpianto() {
        return caratteristicheImpianto;
    }

    public void setCaratteristicheImpianto(ImpiantoSpecs caratteristicheImpianto) {
        this.caratteristicheImpianto = caratteristicheImpianto;
    }
    
  
}
