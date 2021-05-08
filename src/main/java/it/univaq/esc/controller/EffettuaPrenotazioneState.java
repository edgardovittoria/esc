package it.univaq.esc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;


import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public abstract class EffettuaPrenotazioneState {
    @Autowired
    private RegistroImpianti registroImpianti;

    @Autowired
    private RegistroUtentiPolisportiva registroUtenti;

    public EffettuaPrenotazioneState(){}

    public abstract Map<String, Object> getDatiOpzioni();

    protected List<SportDTO> getSportPraticabiliPolisportiva() {
        List<Sport> listaSportPraticabili = this.getSportPraticabili();
        
        List<SportDTO> listaSportPraticabiliDTO = new ArrayList<SportDTO>();
        for (Sport sport : listaSportPraticabili) {
            SportDTO sportDTO = new SportDTO();
            sportDTO.impostaValoriDTO(sport);
            listaSportPraticabiliDTO.add(sportDTO);
        }
        return listaSportPraticabiliDTO;
    }

    protected List<SportivoDTO> getSportiviPolisportiva() {
        List<SportivoDTO> listaSportiviDTO = new ArrayList<SportivoDTO>();
        for (UtentePolisportivaAbstract utente : getRegistroUtenti().getListaUtenti()) {
            SportivoDTO sportivoDTO = new SportivoDTO();
            sportivoDTO.impostaValoriDTO(utente);
            listaSportiviDTO.add(sportivoDTO);
        }

        return listaSportiviDTO;
    }



    private List<Sport> getSportPraticabili() {
        List<Sport> listaSportPraticabili = new ArrayList<Sport>();
        Set<Sport> setSportPraticabili = new HashSet<Sport>();
        for (Impianto impianto : getRegistroImpianti().getListaImpiantiPolisportiva()) {
            for (ImpiantoSpecs specifica : impianto.getSpecificheImpianto()) {
                setSportPraticabili.add(specifica.getSportPraticabile());
            }
        }
        listaSportPraticabili.addAll(setSportPraticabili);
        return listaSportPraticabili;

    }

    protected RegistroImpianti getRegistroImpianti(){
        return this.registroImpianti;
    }

    protected RegistroUtentiPolisportiva getRegistroUtenti(){
        return this.registroUtenti;
    }
}
