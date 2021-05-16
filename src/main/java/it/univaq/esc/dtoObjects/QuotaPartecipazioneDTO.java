package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.QuotaPartecipazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class QuotaPartecipazioneDTO implements IModelToDTO{

    private float costo;

    private SportivoDTO sportivo;

    private boolean pagata;


    @Override
    public void impostaValoriDTO(Object modelDaConvertire) {
       QuotaPartecipazione quota = (QuotaPartecipazione)modelDaConvertire;
       this.setCosto(quota.getCosto());
       this.setPagata(quota.isPagata());
       SportivoDTO sportivo = new SportivoDTO();
       sportivo.impostaValoriDTO(quota.getSportivoAssociato());
       this.setSportivo(sportivo);
        
    }
}
