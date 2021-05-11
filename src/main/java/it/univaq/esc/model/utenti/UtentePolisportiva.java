package it.univaq.esc.model.utenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value = "UtenteBase")
public class UtentePolisportiva extends UtentePolisportivaAbstract implements Serializable{
    @Column
    private String nome;
    @Column
    private String cognome;
    @Column
    private String email;
    @Column
    private String password;
    

    public UtentePolisportiva(){}

    public UtentePolisportiva(String nome, String cognome, String email, String password){
        this.setCognome(cognome);
        this.setEmail(email);
        this.setNome(nome);
        this.setPassword(password);
    }
    /**
     * @return String return the nome
     */
    private String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    private void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return String return the cognome
     */
    private String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    private void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return String return the email
     */
    private String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    private void setEmail(String email) {
        this.email = email;
    }

    

    /**
     * @return String return the password
     */
    private String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    private void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setProprieta(Map<String, Object> mappaProprieta) {
        for(String chiave: mappaProprieta.keySet()){
            switch (chiave) {
                case "nome":
                    this.setNome((String)mappaProprieta.get(chiave));
                    break;
                case "cognome":
                    this.setCognome((String)mappaProprieta.get(chiave));
                    break;
                case "email":
                    this.setEmail((String)mappaProprieta.get(chiave));
                    break;
                case "password":
                    this.setPassword((String)mappaProprieta.get(chiave));
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

}
