package it.univaq.esc.dtoObjects;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.repository.AppuntamentoRepository;


@Component
public class RegistroAppuntamentiDTO {

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    
    private List<AppuntamentoDTO> appuntamenti = new ArrayList<AppuntamentoDTO>();


    public RegistroAppuntamentiDTO(){}


    @PostConstruct
    private void popola(){
        for(Appuntamento appuntamento : appuntamentoRepository.findAll()){
            AppuntamentoDTO appuntamentoDTO = new AppuntamentoDTO();
            appuntamentoDTO.impostaValoriDTO(appuntamento);
            appuntamenti.add(appuntamentoDTO);
        }
    }

    public List<AppuntamentoDTO> getListaAppuntamenti(){
        return this.appuntamenti;
    }
}
