package it.univaq.esc.dtoObjects;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class orarioAppuntamento {
    

    private Integer id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataPrenotazione = LocalDate.now();

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraInizio;

    @DateTimeFormat(pattern = "HH:MM")
    private LocalTime oraFine;
}
