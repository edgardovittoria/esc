package it.univaq.esc.controller.effettuaPrenotazione;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import it.univaq.esc.dtoObjects.AppuntamentoDTO;
import it.univaq.esc.dtoObjects.CheckboxPendingSelezionato;
import it.univaq.esc.dtoObjects.IFormPrenotabile;
import it.univaq.esc.dtoObjects.ImpiantoSelezionato;
import it.univaq.esc.dtoObjects.OrarioAppuntamento;
import it.univaq.esc.factory.FactorySpecifichePrenotazione;
import it.univaq.esc.model.Appuntamento;
import it.univaq.esc.model.Calendario;
import it.univaq.esc.model.Impianto;
import it.univaq.esc.model.prenotazioni.PrenotazioneSpecs;
import it.univaq.esc.model.prenotazioni.TipiPrenotazione;
import it.univaq.esc.model.costi.PrenotabileDescrizione;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCosto;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoBase;
import it.univaq.esc.model.costi.calcolatori.CalcolatoreCostoComposito;
import it.univaq.esc.model.utenti.UtentePolisportivaAbstract;
@Component
public class EffettuaPrenotazioneImpiantoState extends EffettuaPrenotazioneState{
    
    public EffettuaPrenotazioneImpiantoState(){}

    @Override
    public Map<String, Object> getDatiOpzioni(EffettuaPrenotazioneHandlerRest controller) {
        Map<String, Object> mappaValori = new HashMap<String, Object>();
        mappaValori.put("sportPraticabili", this.getSportPraticabiliPolisportiva());
        mappaValori.put("sportiviPolisportiva", this.getSportiviPolisportiva());
        mappaValori.put("appuntamentiSottoscrivibili", this.getAppuntamentiImpiantoSottoscrivibiliDaUtente(controller.getSportivoPrenotante()));


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

        List<UtentePolisportivaAbstract> sportivi = new ArrayList<UtentePolisportivaAbstract>();
        for (String email : (List<String>)formDati.getValoriForm().get("invitati")) {
            sportivi.add(getRegistroUtenti().getUtenteByEmail(email));
        }
        

        for (PrenotazioneSpecs spec : controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione()) {
            spec.setSpecificaDescrtiption(descrizioneSpecifica);
        }

        for (OrarioAppuntamento orario : (List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")) {
            // Calendario calendarioPrenotazione = new Calendario();
            LocalDateTime dataInizio = LocalDateTime.of(orario.getLocalDataPrenotazione(), orario.getOraInizio());
            LocalDateTime dataFine = LocalDateTime.of(orario.getLocalDataPrenotazione(), orario.getOraFine());

            // calendarioPrenotazione.aggiungiAppuntamento(dataInizio, dataFine ,
            // controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));
            // controller.getPrenotazioneInAtto().setCalendarioSpecifica(calendarioPrenotazione,
            // controller.getPrenotazioneInAtto().getListaSpecifichePrenotazione().get(0));

            Appuntamento appuntamentoDaImpostare = controller.getListaAppuntamentiPrenotazioneInAtto().get(((List<OrarioAppuntamento>)formDati.getValoriForm().get("listaOrariAppuntamenti")).indexOf(orario));
            appuntamentoDaImpostare.setDataOraInizioAppuntamento(dataInizio);
            appuntamentoDaImpostare.setDataOraFineAppuntamento(dataFine);
            
            boolean pending = false;
            for(CheckboxPendingSelezionato checkbox : (List<CheckboxPendingSelezionato>)formDati.getValoriForm().get("checkboxesPending")){
                if(checkbox.getIdSelezione() == orario.getId()){
                    pending = checkbox.isPending();
                }
            }

            appuntamentoDaImpostare.setPending(pending);

            HashMap<String, Object> mappaValori = new HashMap<String, Object>();
            mappaValori.put("invitati", sportivi);

        
            Integer idImpianto = 0;
            for(ImpiantoSelezionato impianto : (List<ImpiantoSelezionato>)formDati.getValoriForm().get("impianti")){
                if(impianto.getIdSelezione() == orario.getId()){
                    idImpianto = impianto.getIdImpianto();
                }
            }
            
            mappaValori.put("impianto", getRegistroImpianti().getImpiantoByID(idImpianto));
                
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
        }
        List<String> lista = new ArrayList<String>();
        lista.add("pippo");        
    }

    @Override
    public Map<String, Object> aggiornaOpzioniPrenotazione(Map<String, Object> dati) {
        Map<String, Object> datiAggiornati = new HashMap<String, Object>();
        datiAggiornati.put("impiantiDisponibili", this.getImpiantiDTODisponibili(dati));
        return datiAggiornati;
    }


    private List<AppuntamentoDTO> getAppuntamentiImpiantoSottoscrivibiliDaUtente(UtentePolisportivaAbstract utentePerCuiCercareAppuntamentiSottoscrivibili){
        List<AppuntamentoDTO> listaAppuntamentiDTO = new ArrayList<AppuntamentoDTO>();
        for(Appuntamento appuntamento : this.getRegistroAppuntamenti().getAppuntamentiSottoscrivibiliPerTipo(TipiPrenotazione.IMPIANTO.toString(), utentePerCuiCercareAppuntamentiSottoscrivibili)){
            AppuntamentoDTO appDTO = new AppuntamentoDTO();
            appDTO.impostaValoriDTO(appuntamento);
            listaAppuntamentiDTO.add(appDTO);
        }
        return listaAppuntamentiDTO;
    }

    @Override
    public HttpStatus aggiungiPartecipanteAEventoEsistente(Integer idEvento, String emailPartecipante) {
        Appuntamento appuntamento = this.getRegistroAppuntamenti().getAppuntamentoById(idEvento);
        if(appuntamento != null){
            appuntamento.aggiungiPartecipante(this.getRegistroUtenti().getUtenteByEmail(emailPartecipante));
            this.getRegistroAppuntamenti().aggiornaAppuntamento(appuntamento);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    
}
