package it.univaq.esc.dtoObjects;


import java.util.Map;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;


@Component
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoPrenotazione")
@JsonSubTypes({
    @Type(value = FormPrenotaImpianto.class, name = "IMPIANTO"),
    @Type(value = FormPrenotaLezioneDTO.class, name = "LEZIONE"),
    @Type(value = FormCreaCorso.class, name = "CORSO"),
    @Type(value = FormPrenotaImpiantoSquadra.class, name = "IMPIANTO_SQUADRA")
})
@Getter @Setter @NoArgsConstructor
public abstract class FormPrenotabile {
    
	private String modalitaPrenotazione; 
	
    public abstract Map<String, Object> getValoriForm();
}