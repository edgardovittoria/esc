package it.univaq.esc.model.utenti;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
