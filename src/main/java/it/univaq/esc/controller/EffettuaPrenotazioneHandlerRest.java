package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.RegistroSportivi;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.UtentePolisportivaAbstract;

@RestController
@RequestMapping("/effettuaPrenotazione")
public class EffettuaPrenotazioneHandlerRest {

    @Autowired
    private RegistroImpianti registroImpianti;

    @Autowired
    private RegistroSportivi registroSportivi;
    
    public EffettuaPrenotazioneHandlerRest(){}


    @GetMapping("/sportPraticabili")
    @CrossOrigin
    public @ResponseBody List<SportDTO> getSportPraticabiliPolisportiva(){
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()) {
            for(ImpiantoSpecs specifica : impianto.getSpecificheImpianto()){
                setSportPraticabili.add(specifica.getSportPraticabile());
            }
        }
        listaSportPraticabili.addAll(setSportPraticabili);
        List<SportDTO> listaSportPraticabiliDTO = new ArrayList<SportDTO>();
        for(Sport sport : listaSportPraticabili){
            SportDTO sportDTO = new SportDTO();
            sportDTO.impostaValoriDTO(sport);
            listaSportPraticabiliDTO.add(sportDTO);
        }
        return listaSportPraticabiliDTO;
    }


    @GetMapping("/impiantiDisponibili")
    @CrossOrigin
    public @ResponseBody List<ImpiantoDTO> getImpiantiDisponibili(){
        Calendario calendario = new Calendario();
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();        
        for(Impianto impianto : registroImpianti.getListaImpiantiPolisportiva()){
            if(!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(calendario)){
                listaImpiantiDisponibili.add(impianto);
            }
        }
        List<ImpiantoDTO> listaImpiantiDisponibiliDTO = new ArrayList<ImpiantoDTO>();  
        for(Impianto impianto : listaImpiantiDisponibili){
            ImpiantoDTO impiantoDTO = new ImpiantoDTO();
            impiantoDTO.impostaValoriDTO(impianto);
            listaImpiantiDisponibiliDTO.add(impiantoDTO);
        }
        return listaImpiantiDisponibiliDTO;
        
    }

    @GetMapping("/sportiviPolisportiva")
    @CrossOrigin
    public @ResponseBody List<SportivoDTO> getSportiviPolisportiva(){
        List<SportivoDTO> listaSportiviDTO = new ArrayList<SportivoDTO>();
        for(UtentePolisportivaAbstract utente : this.registroSportivi.getListaSportivi()){
            SportivoDTO sportivoDTO = new SportivoDTO();
            sportivoDTO.impostaValoriDTO(utente);
            listaSportiviDTO.add(sportivoDTO);
        }

        return listaSportiviDTO;
    }
}
