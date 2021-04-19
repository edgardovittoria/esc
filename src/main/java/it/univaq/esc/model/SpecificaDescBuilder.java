package it.univaq.esc.model;

public class SpecificaDescBuilder {
    
    private SpecificaDesc specifica;


    public SpecificaDescBuilder(){
        this.setSpecifica(new SpecificaDescCostoOrario());
    }


    private SpecificaDesc getSpecifica() {
        return specifica;
    }


    private void setSpecifica(SpecificaDesc specifica) {
        this.specifica = specifica;
    }

    public  SpecificaDescBuilder impostaCostoOrario(float costoOrario){
        this.getSpecifica().setCosto(costoOrario, TipoSpecificaDesc.COSTO_ORARIO.toString());

        return this;
    }

    public  SpecificaDescBuilder impostaCostoPavimentazione(float costoPavimentazione){
        this.getSpecifica().setCosto(costoPavimentazione, TipoSpecificaDesc.COSTO_PAVIMENTAZIONE.toString());

        return this;
    }

    public SpecificaDesc build(){
        return this.getSpecifica();
    }


}
