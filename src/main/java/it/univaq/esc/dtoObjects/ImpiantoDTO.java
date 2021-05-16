package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.Sport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ImpiantoDTO implements IModelToDTO{

    private int idImpianto;
    private boolean indoor;
    private String pavimentazione;
    private List<SportDTO> sportPraticabili = new ArrayList<SportDTO>();
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();

    
    @Override
    public void impostaValoriDTO(Object modelDaConveritire){
        Impianto impianto = (Impianto)modelDaConveritire;
        setIdImpianto(impianto.getIdImpianto());
        setIndoor(impianto.isIndoor());
        setPavimentazione(impianto.getTipoPavimentazione().toString());
        for(Sport sport : impianto.getSportPraticabili()){
            SportDTO sportDTO = new SportDTO();
            sportDTO.impostaValoriDTO(sport);
            this.getSportPraticabili().add(sportDTO);
        }
        for(Appuntamento appuntamento : impianto.getListaAppuntamenti()){
            AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();
            appuntamentoDTO.impostaValoriDTO(appuntamento);
            this.getAppuntamenti().add(appuntamentoDTO);
        }
    }

    
    
}
