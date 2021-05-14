package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import it.univaq.esc.dtoObjects.IFormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoDTO;
import it.univaq.esc.dtoObjects.IstruttoreDTO;
import it.univaq.esc.dtoObjects.SportDTO;
import it.univaq.esc.dtoObjects.SportivoDTO;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.ImpiantoSpecs;
import it.univaq.esc.model.RegistroAppuntamenti;
import it.univaq.esc.model.RegistroImpianti;
import it.univaq.esc.model.Sport;
import it.univaq.esc.model.utenti.RegistroUtentiPolisportiva;
import it.univaq.esc.model.utenti.TipoRuolo;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

public abstract class EffettuaPrenotazioneState {
    @Autowired
    private RegistroImpianti registroImpianti;

    @Autowired
    private RegistroUtentiPolisportiva registroUtenti;

    @Autowired
    private RegistroAppuntamenti registroAppuntamenti;

    public EffettuaPrenotazioneState() {
    }

    protected RegistroAppuntamenti getRegistroAppuntamenti() {
        return registroAppuntamenti;
    }

    public abstract Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller);

    public abstract void impostaDatiPrenotazione(IFormPrenotabile formDati, EffettuaPrenotazioneHandlerRest controller);

    public abstract void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller);

    public abstract Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati);

    public abstract HttpStatus aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante);

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

    protected RegistroImpianti getRegistroImpianti() {
        return this.registroImpianti;
    }

    protected RegistroUtentiPolisportiva getRegistroUtenti() {
        return this.registroUtenti;
    }

    protected List<IstruttoreDTO> getIstruttoriPerSport(String sport) {
        List<IstruttoreDTO> listaIstruttori = new ArrayList<IstruttoreDTO>();
        Sport sportRichiesto = null;
        for (Sport sportPolisportiva : this.getSportPraticabili()) {
            if (sportPolisportiva.getNome().equals(sport)) {
                sportRichiesto = sportPolisportiva;
            }
        }
        List<UtentePolisportivaAbstract> istruttori = this.getRegistroUtenti().getIstruttoriPerSport(sportRichiesto);
        for (UtentePolisportivaAbstract istruttore : istruttori) {
            IstruttoreDTO istDTO = new IstruttoreDTO();
            istDTO.impostaValoriDTO(istruttore);
            listaIstruttori.add(istDTO);
        }

        return listaIstruttori;
    }

    protected List<ImpiantoDTO> getImpiantiDTODisponibili(Map<String, Object> dati) {
        List<Impianto> listaImpiantiDisponibili = this.getRegistroImpianti().getListaImpiantiPolisportiva();
        if (dati.containsKey("orario")) {
            Map<String, String> orario = (HashMap<String, String>) dati.get("orario");
            listaImpiantiDisponibili = this.filtraImpiantiDisponibiliByOrario(
                    LocalDateTime.parse(orario.get("oraInizio"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
                    LocalDateTime.parse(orario.get("oraFine"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
                    listaImpiantiDisponibili);

            if (dati.containsKey("sport")) {
                listaImpiantiDisponibili = this.filtraImpiantiPerSport((String) dati.get("sport"),
                        listaImpiantiDisponibili);

            }
        } else if (dati.containsKey("sport")) {
            listaImpiantiDisponibili = this.filtraImpiantiPerSport((String) dati.get("sport"),
                    listaImpiantiDisponibili);
        }

        List<ImpiantoDTO> listaImpiantiDTODisponibili = new ArrayList<ImpiantoDTO>();
        for (Impianto impianto : listaImpiantiDisponibili) {

            ImpiantoDTO impiantoDTO = new ImpiantoDTO();
            impiantoDTO.impostaValoriDTO(impianto);
            listaImpiantiDTODisponibili.add(impiantoDTO);

        }

        return listaImpiantiDTODisponibili;
    }

    protected List<Impianto> filtraImpiantiDisponibiliByOrario(LocalDateTime oraInizio, LocalDateTime oraFine,
            List<Impianto> listaImpianti) {
        List<Impianto> listaImpiantiDisponibili = new ArrayList<Impianto>();

        for (Impianto impianto : listaImpianti) {
            if (!impianto.getCalendarioAppuntamentiImpianto().sovrapponeA(oraInizio, oraFine)) {
                listaImpiantiDisponibili.add(impianto);
            }
        }

        return listaImpiantiDisponibili;
    }

    protected List<Impianto> filtraImpiantiPerSport(String nomeSport, List<Impianto> listaImpianti) {
        List<Impianto> impianti = new ArrayList<Impianto>();
        for (Impianto impianto : listaImpianti) {
            for (ImpiantoSpecs specifica : impianto.getSpecificheImpianto()) {
                if (specifica.getSportPraticabile().getNome().equals(nomeSport)) {
                    impianti.add(impianto);
                }
            }

        }
        return impianti;
    }

    protected List<UtentePolisportivaAbstract> filtraIstruttoriPerSport(String nomeSport,
            List<UtentePolisportivaAbstract> listaIstruttori) {
        List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
        for (UtentePolisportivaAbstract istruttore : listaIstruttori) {
            for (Sport sport : (List<Sport>) istruttore.getProprieta().get("sportInsegnati")) {
                if (sport.getNome().equals(nomeSport)) {
                    istruttori.add(istruttore);
                }
            }
        }
        return istruttori;
    }

    protected List<UtentePolisportivaAbstract> filtraIstruttoriPerOrario(LocalDateTime oraInizio, LocalDateTime oraFine,
            List<UtentePolisportivaAbstract> listaIstruttori) {
        List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
        for (UtentePolisportivaAbstract istruttore : listaIstruttori) {
            if (!((Calendario)istruttore.getProprieta().get("calendarioLezioni")).sovrapponeA(oraInizio, oraFine)) {
                istruttori.add(istruttore);
            }
        }
        return istruttori;
    }

    protected List<IstruttoreDTO> getIstruttoriDTODisponibili(Map<String, Object> dati) {
        List<UtentePolisportivaAbstract> istruttoriDisponibili = this.getRegistroUtenti()
                .getListaUtentiByRuolo(TipoRuolo.ISTRUTTORE);
        if (dati.containsKey("orario")) {
            Map<String, String> orario = (HashMap<String, String>) dati.get("orario");
            istruttoriDisponibili = this.filtraIstruttoriPerOrario(
                    LocalDateTime.parse(orario.get("oraInizio"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
                    LocalDateTime.parse(orario.get("oraFine"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")),
                    istruttoriDisponibili);

            if (dati.containsKey("sport")) {
                istruttoriDisponibili = this.filtraIstruttoriPerSport((String) dati.get("sport"),
                        istruttoriDisponibili);

            }
        } else if (dati.containsKey("sport")) {
            istruttoriDisponibili = this.filtraIstruttoriPerSport((String) dati.get("sport"), istruttoriDisponibili);
        }
        List<IstruttoreDTO> listaIstruttoriDTODisponibili = new ArrayList<IstruttoreDTO>();
        for (UtentePolisportivaAbstract istruttore : istruttoriDisponibili) {

            IstruttoreDTO istruttoreDTO = new IstruttoreDTO();
            istruttoreDTO.impostaValoriDTO(istruttore);
            listaIstruttoriDTODisponibili.add(istruttoreDTO);

        }
        return listaIstruttoriDTODisponibili;
    }

}
