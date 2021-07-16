package it.univaq.esc.model.utenti;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProfiloDirettore extends ProfiloUtente{

	@Override
	public boolean isProfilo(TipoRuolo ruolo) {
		if(ruolo == TipoRuolo.DIRETTORE) {
			return true;
		}
		return false;
	}

	@Override
	public TipoRuolo getRuoloRelativo() {
		return TipoRuolo.DIRETTORE;
	}

}
