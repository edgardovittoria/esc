package it.univaq.esc.model.prenotazioni;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter(value = AccessLevel.PRIVATE) @Setter(value = AccessLevel.PRIVATE) @NoArgsConstructor
public class OrarioAppuntamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idOrarioAppuntamento;

    @Column
    private LocalDateTime orarioInizioAppuntamento;

    @Column
    private LocalDateTime orarioFineAppuntamento;



    public boolean sovrapponeA(LocalDate dataAppuntamento, LocalTime oraInizio, LocalTime oraFine) {
        LocalDateTime orarioInizio = LocalDateTime.of(dataAppuntamento, oraInizio);
        LocalDateTime orarioFine = LocalDateTime.of(dataAppuntamento, oraFine);

		if (getOrarioInizioAppuntamento().isBefore(orarioInizio)
				&& getOrarioFineAppuntamento().isAfter(orarioInizio)) {
			return true;
		} else if (getOrarioInizioAppuntamento().isBefore(orarioFine)
				&& getOrarioFineAppuntamento().isAfter(orarioFine)) {
			return true;
		} else if (getOrarioInizioAppuntamento().equals(orarioInizio)) {
			return true;
		}
		return false;
	}


    public LocalDate getDataOrarioAppuntamento() {
		return getOrarioFineAppuntamento().toLocalDate();
	}

	public LocalTime getOraInizio() {
		return getOrarioInizioAppuntamento().toLocalTime();
	}

	public LocalTime getOraFine() {
		return getOrarioFineAppuntamento().toLocalTime();
	}


    public void imposta(LocalDate dataAppuntamento, LocalTime oraInizioAppuntamento, LocalTime oraFineAppuntamento){
        setOrarioInizioAppuntamento(LocalDateTime.of(dataAppuntamento, oraInizioAppuntamento));
        setOrarioFineAppuntamento(LocalDateTime.of(dataAppuntamento, oraFineAppuntamento));
    }
    
    public void imposta(String orarioInizio, String orarioFine) {
    	LocalDateTime orarioInizioAppuntamento = LocalDateTime.parse(orarioInizio,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
    	LocalDateTime orarioFineAppuntamento = LocalDateTime.parse(orarioFine,
				DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
    	setOrarioInizioAppuntamento(orarioInizioAppuntamento);
    	setOrarioFineAppuntamento(orarioFineAppuntamento);
    }
}
