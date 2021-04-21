package it.univaq.esc.model.costi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import it.univaq.esc.model.Sport;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TipoSpecificaDescDecorator")
@DiscriminatorValue(value = "SpecificaDescDecorator")
public abstract class SpecificaDescDecorator extends SpecificaDesc{

    @OneToOne(cascade = CascadeType.ALL)
    private SpecificaDesc specifica;

    

    protected SpecificaDescDecorator(){
        //super();
    }
    /**
     * @return SpecificaDesc return the specifica
     */
    public SpecificaDesc getSpecifica() {
        return specifica;
    }

    /**
     * @param specifica the specifica to set
     */
    public void setSpecifica(SpecificaDesc specifica) {
        this.specifica = specifica;
    }

    @Override
    public void setTipoPrenotazione(String tipoPrenotazione) {
        this.getSpecifica().setTipoPrenotazione(tipoPrenotazione);
    }

    @Override
    public String getTipoPrenotazione(){
        return this.getSpecifica().getTipoPrenotazione();
    }

    @Override
    public Sport getSport() {
       return this.getSpecifica().getSport();
    }

    @Override
    public void setSport(Sport sport) {
        this.getSpecifica().setSport(sport);
        
    }

    @Override
    public void setCosto(float costo, String tipoSpecifica){
        if(this.getTipoSpecificaDesc().equals(tipoSpecifica)){
            this.costo = costo;
        }
        else{
            this.getSpecifica().setCosto(costo, tipoSpecifica);
        }
    }

    @Override
    public List<String> getListaTipiSpecificheDesc(){
        List<String> listaTipiSpecificheDesc = this.getSpecifica().getListaTipiSpecificheDesc();
        listaTipiSpecificheDesc.add(this.getTipoSpecificaDesc());

        return listaTipiSpecificheDesc;
    }

    


}
