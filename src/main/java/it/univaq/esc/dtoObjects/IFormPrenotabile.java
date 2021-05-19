package it.univaq.esc.dtoObjects;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.springframework.stereotype.Component;


@Component
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoPrenotazione")
@JsonSubTypes({
    @Type(value = FormPrenotaImpianto.class, name = "IMPIANTO"),
    @Type(value = FormPrenotaLezioneDTO.class, name = "LEZIONE"),
    @Type(value = FormCreaCorso.class, name = "CORSO")
})
public interface IFormPrenotabile {
    
    public HashMap<String, Object> getValoriForm();
}