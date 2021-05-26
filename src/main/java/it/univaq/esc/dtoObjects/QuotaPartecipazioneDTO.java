package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.prenotazioni.QuotaPartecipazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class QuotaPartecipazioneDTO implements IModelToDTO{

    private float costo;

    private UtentePolisportivaDTO sportivo;

    private boolean pagata;


    @Override
    public void impostaValoriDTO(Object modelDaConvertire) {
       QuotaPartecipazione quota = (QuotaPartecipazione)modelDaConvertire;
       this.setCosto(quota.getCosto());
       this.setPagata(quota.isPagata());
       UtentePolisportivaDTO sportivo = new UtentePolisportivaDTO();
       sportivo.impostaValoriDTO(quota.getSportivoAssociato());
       this.setSportivo(sportivo);
        
    }
}
