package it.univaq.esc.model.utenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UtentePolisportiva implements Serializable{
	
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer idUtente;
		
		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn
		private AnagraficaUtente anagraficaUtente;
		
		@OneToMany(cascade = CascadeType.ALL)
		@JoinColumn
		@LazyCollection(LazyCollectionOption.FALSE)
		private List<ProfiloUtente> profiliAssociati = new ArrayList<ProfiloUtente>();

		
		public  UtentePolisportiva(AnagraficaUtente anagraficaUtente) {
			setAnagraficaUtente(anagraficaUtente);
		}



		
		public List<String> getRuoli() {
			List<String> listaRuoli = new ArrayList<String>();
			getProfiliAssociati().forEach((profilo) -> listaRuoli.add(profilo.getRuoloRelativo().toString()));
			
			return listaRuoli;
		}


		public boolean isEqual(UtentePolisportiva utenteDaConfrontare) {
			if (this.getEmail().equals(utenteDaConfrontare.getEmail())) {
				return true;
			}
			return false;
		}
		
		
		
		public ProfiloSportivo comeSportivo() {
			if(is(TipoRuolo.SPORTIVO)) {
				return (ProfiloSportivo) getProfiloAssociatoRealtivoA(TipoRuolo.SPORTIVO);
			}
			return null;
		}
		
		public ProfiloIstruttore comeIstruttore() {
			if(is(TipoRuolo.ISTRUTTORE)) {
				return (ProfiloIstruttore) getProfiloAssociatoRealtivoA(TipoRuolo.ISTRUTTORE);
			}
			return null;
		}
		
		public ProfiloManutentore comeManutentore() {
			if(is(TipoRuolo.MANUTENTORE)) {
				return (ProfiloManutentore) getProfiloAssociatoRealtivoA(TipoRuolo.MANUTENTORE);
			}
			return null;
		}
		
		public ProfiloDirettore comeDirettore() {
			if(is(TipoRuolo.DIRETTORE)) {
				return (ProfiloDirettore) getProfiloAssociatoRealtivoA(TipoRuolo.DIRETTORE);
			}
			return null;
		}
		
		public boolean is(TipoRuolo ruolo) {
			for(ProfiloUtente profiloUtente : getProfiliAssociati()) {
				if(profiloUtente.isProfilo(ruolo)) {
					return true;
				}
			}
			return false;
		}

		private ProfiloUtente getProfiloAssociatoRealtivoA(TipoRuolo ruolo) {
			for(ProfiloUtente profiloUtente : getProfiliAssociati()) {
				if(profiloUtente.isProfilo(ruolo)) {
					return profiloUtente;
				}
			}
			return null;
		}
		
		public void aggiungi(ProfiloUtente nuovoProfiloUtente) {
			TipoRuolo ruolo = nuovoProfiloUtente.getRuoloRelativo();
			if(!is(ruolo)) {
				getProfiliAssociati().add(nuovoProfiloUtente);
			}
		}
		
		public boolean isSuaQuesta(String email) {
			return getAnagraficaUtente().getEmail().equals(email);
		}
		
		public String getEmail() {
			return getAnagraficaUtente().getEmail();
		}
		
		/**
		 * Metodo che ritorna la password
		 * @return
		 */
		public String getPassword() {
			return getAnagraficaUtente().getPassword();
		}
		
		public String getNome() {
			return getAnagraficaUtente().getNome();
		}
		
		
		public String getCognome() {
			return getAnagraficaUtente().getCognome();
		}

		public String getNominativoCompleto() {
			return getNome() + " " + getCognome();
		}
	}


