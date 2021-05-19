package it.univaq.esc.model.utenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "UtenteBase")
@Getter(value = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UtentePolisportiva extends UtentePolisportivaAbstract implements Serializable {
	@Column
	private String nome;
	@Column
	private String cognome;
	@Column
	private String email;
	@Column
	private String password;

	public UtentePolisportiva(String nome, String cognome, String email, String password) {
		this.setCognome(cognome);
		this.setEmail(email);
		this.setNome(nome);
		this.setPassword(password);
	}

	@Override
	public void setProprieta(Map<String, Object> mappaProprieta) {
		for (String chiave : mappaProprieta.keySet()) {
			switch (chiave) {
			case "nome":
				this.setNome((String) mappaProprieta.get(chiave));
				break;
			case "cognome":
				this.setCognome((String) mappaProprieta.get(chiave));
				break;
			case "email":
				this.setEmail((String) mappaProprieta.get(chiave));
				break;
			case "password":
				this.setPassword((String) mappaProprieta.get(chiave));
				break;
			default:
				break;
			}
		}

	}

	@Override
	public Map<String, Object> getProprieta() {
		Map<String, Object> mappaProprieta = new HashMap<String, Object>();
		mappaProprieta.put("nome", this.getNome());
		mappaProprieta.put("cognome", this.getCognome());
		mappaProprieta.put("email", this.getEmail());
		mappaProprieta.put("password", this.getPassword());

		return mappaProprieta;
	}

	@Override
	public List<String> getRuoliUtentePolisportiva() {

		return new ArrayList<String>();
	}

	@Override
	public boolean isEqual(UtentePolisportivaAbstract utenteDaConfrontare) {
		if (this.getEmail().equals((String) utenteDaConfrontare.getProprieta().get("email"))) {
			return true;
		}
		return false;
	}

}
