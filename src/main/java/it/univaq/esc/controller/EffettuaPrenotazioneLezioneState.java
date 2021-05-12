package it.univaq.esc.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.IFormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.IstruttoreSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;

@Component
public class EffettuaPrenotazioneLezioneState extends EffettuaPrenotazioneState{
    
    public EffettuaPrenotazioneLezioneState(){}

    @Override
    public Map<String, Object> getDatiOpzioni() {
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        
        return mappaValori;
    }
    
    @Override
    public void impostaDatiPrenotazione(IFormPrenotabile formDati, EffettuaPrenotazioneHandlerRest controller) {
        for (int i = 0; i < ((List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")).size(); i++) {
            PrenotazioneSpecs prenotazioneSpecs = FactorySpecifichePrenotazione
                    .getSpecifichePrenotazione(controller.getTipoPrenotazioneInAtto());
            controller.getPrenotazioneInAtto().aggiungiSpecifica(prenotazioneSpecs);
            prenotazioneSpecs.setPrenotazioneAssociata(controller.getPrenotazioneInAtto());

            // Creazione calcolatore che poi dovrà finire altrove
            CalcolatoreCosto calcolatoreCosto = new CalcolatoreCostoComposito();
            calcolatoreCosto.aggiungiStrategiaCosto(new CalcolatoreCostoBase());
            // ---------------------------------------------------------------------------------------

            Appuntamento appuntamento = new Appuntamento();
            appuntamento.setPrenotazioneSpecsAppuntamento(prenotazioneSpecs);
            appuntamento.setCalcolatoreCosto(calcolatoreCosto);
            
            controller.aggiungiAppuntamento(appuntamento);
        }

        PrenotabileDescrizione descrizioneSpecifica = null;
        for (PrenotabileDescrizione desc : controller.getListinoPrezziDescrizioniPolisportiva().getCatalogoPrenotabili()) {
            if (desc.getSportAssociato().getNome().equals((String)formDati.getValoriForm().get("sport"))
                    && desc.getTipoPrenotazione().equals(
                            controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0).getTipoPrenotazione())) {
                descrizioneSpecifica = desc;
            }
        }

        for (PrenotazioneSpecs spec : controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione()) {
            spec.setSpecificaDescrtiption(descrizioneSpecifica);
        }

        List<UtentePolisportivaAbstract> istruttori = new ArrayList<UtentePolisportivaAbstract>();
        for (IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>)formDati.getValoriForm().get("istruttori")) {
            istruttori.add(getRegistroUtenti().getUtenteByEmail(istruttore.getIstruttore()));
        }

        for (OrarioAppuntamento orario : (List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")) {
            // Calendario calendarioPrenotazione = new Calendario();
            LocalDateTime dataInizio = LocalDateTime.of(orario.getLocalDataPrenotazione(), orario.getOraInizio());
            LocalDateTime dataFine = LocalDateTime.of(orario.getLocalDataPrenotazione(), orario.getOraFine());

            // calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine ,
            // controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
            // controller.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione,
            // controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

            controller.getListaAppuntamentiPrenotazioneInAtto().get(((List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
                    .setDataOraInizioAppuntamento(dataInizio);
            controller.getListaAppuntamentiPrenotazioneInAtto().get(((List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
                    .setDataOraFineAppuntamento(dataFine);
            

            HashMap<String, Object> mappaValori = new HashMap<String, Object>();
            

        
            Integer idImpianto = 0;
            for(ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>)formDati.getValoriForm().get("impianti")){
                if(impianto.getIdSelezione() == orario.getId()){
                    idImpianto = impianto.getIdImpianto();
                }
            }
            
            mappaValori.put("impianto", getRegistroImpianti().getImpiantoByID(idImpianto));
                
            String emailIstruttore = "";
            for(IstruttoreSelezionato istruttore : (List<IstruttoreSelezionato>)formDati.getValoriForm().get("istruttori")){
                if(istruttore.getIdSelezione() == orario.getId()){
                    emailIstruttore = istruttore.getIstruttore();
                }
            }
            mappaValori.put("istruttore", getRegistroUtenti().getUtenteByEmail(emailIstruttore));

            controller.getListaAppuntamentiPrenotazioneInAtto().get(((List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario)).getPrenotazioneSpecsAppuntamento().impostaValoriSpecificheExtraPrenotazione(mappaValori);
            controller.getListaAppuntamentiPrenotazioneInAtto().get(((List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario))
                    .calcolaCosto();
        }

        for(Appuntamento appuntamento : controller.getListaAppuntamentiPrenotazioneInAtto()){
            appuntamento.aggiungiPartecipante(controller.getPrenotazioneInAtto().getSportivoPrenotante());
        }
        
    }

    @Override
    public void aggiornaElementiDopoConfermaPrenotazione(EffettuaPrenotazioneHandlerRest controller) {
        for(Appuntamento app : controller.getListaAppuntamentiPrenotazioneInAtto()){
            Calendario calendarioDaUnire = new Calendario();
            calendarioDaUnire.aggiungiAppuntamento(app);
            getRegistroImpianti()
            .aggiornaCalendarioImpianto(
                    (Impianto) controller.getPrenotazioneInAtto().getSingolaSpecificaExtra("impianto",
                            app.getPrenotazioneSpecsAppuntamento()),
                    calendarioDaUnire);
            getRegistroUtenti().aggiornaCalendarioIstruttore(calendarioDaUnire, (UtentePolisportivaAbstract)controller.getPrenotazioneInAtto().getSingolaSpecificaExtra("istruttore",
            app.getPrenotazioneSpecsAppuntamento()));
        }
        
    }

    @Override
    public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
        Map<String, Object> mappaDatiAggiornati = new HashMap<String, Object>();
        mappaDatiAggiornati.put("impiantiDisponibili", this.getImpiantiDTODisponibili(dati));
        mappaDatiAggiornati.put("istruttoriDisponibili", this.getIstruttoriDTODisponibili(dati));
        return mappaDatiAggiornati;
    }


}
