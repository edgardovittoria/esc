package it.univaq.esc.dtoObjects;

import it.univaq.esc.model.QuotaPartecipazione;

public class QuotaPartecipazioneDTO implements IModelToDTO{

    private float costo;

    private SportivoDTO sportivo;

    private boolean pagata;

    public QuotaPartecipazioneDTO(){}

    public float getCosto() {
        return costo;
    }

    public boolean isPagata() {
        return pagata;
    }

    public void setPagata(boolean pagata) {
        this.pagata = pagata;
    }

    public SportivoDTO getSportivo() {
        return sportivo;
    }

    public void setSportivo(SportivoDTO sportivo) {
        this.sportivo = sportivo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

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
