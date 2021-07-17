package it.univaq.esc.model.prenotazioni;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.univaq.esc.model.TipoEventoNotificabile;
import it.univaq.esc.model.catalogoECosti.ModalitaPrenotazione;
import it.univaq.esc.model.utenti.UtentePolisportiva;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "prenotazioni")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idPrenotazione")
@Getter
@Setter
@NoArgsConstructor
public class Prenotazione extends Notificabile {

	@ManyToOne()
	@JoinColumn()
	private UtentePolisportiva sportivoPrenotante;

	@Column
	@CreationTimestamp
	private LocalDateTime oraDataPrenotazione;

	@OneToMany(mappedBy = "prenotazione")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Appuntamento> listaAppuntamenti = new ArrayList<Appuntamento>();

	

	public void aggiungi(Appuntamento appuntamento) {
		getListaAppuntamenti().add(appuntamento);
		appuntamento.setPrenotazione(this);
	}

	public TipoPrenotazione getTipoPrenotazione() {

		return this.getListaAppuntamenti().get(0).getTipoPrenotazione();
	}

	public String getNomeSportAssociatoAllaPrenotazione() {
		return this.getListaAppuntamenti().get(0).getSportEvento().getNome();
	}
	
	public String getNomeEvento() {
		Appuntamento appuntamento = getListaAppuntamenti().get(0);
		return appuntamento.getNomeEvento();
	}

	@Override
	public Map<String, Object> getInfo() {
		Map<String, Object> infoPrenotazionMap = new HashMap<String, Object>();
		infoPrenotazionMap.put("tipoPrenotazione", this.getTipoPrenotazione());
		infoPrenotazionMap.put("sportNome", this.getNomeSportAssociatoAllaPrenotazione());
		infoPrenotazionMap.put("identificativo", this.getIdPrenotazione());
		infoPrenotazionMap.put("numeroIncontri", getNumeroIncontri());
		infoPrenotazionMap.put("modalitaPrenotazione", getModalitaPrenotazione());

		return infoPrenotazionMap;
	}

	public Integer getIdPrenotazione() {
		return this.getIdNotificabile();
	}

	public Integer getNumeroIncontri() {
		return getListaAppuntamenti().size();
	}

	@Override
	public String getTipoEventoNotificabile() {
		return TipoEventoNotificabile.PRENOTAZIONE.toString();
	}

	public ModalitaPrenotazione getModalitaPrenotazione() {
		return getListaAppuntamenti().get(0).getModalitaPrenotazione();
	}

	public boolean isAssociataA(Appuntamento appuntamento) {
		for (Appuntamento appuntamentoPrenotazione : getListaAppuntamenti()) {
			if (appuntamentoPrenotazione.isEqual(appuntamento)) {
				return true;
			}
		}
		return false;
	}
	
	

}