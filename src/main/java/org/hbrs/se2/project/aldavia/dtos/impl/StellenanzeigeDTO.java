package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StellenanzeigeDTO {

    private int id;
    private String bezeichnung;

    private String beschreibung;

    private String beschaeftigungsverhaeltnis;

    private LocalDate start;

    private LocalDate ende;

    private LocalDate erstellungsdatum;

    private String bezahlung;

    private String getBeschaeftigungsumfang;

    private List<Taetigkeitsfeld> taetigkeitsfelder;

    private List<Bewerbung> bewerbungen;

    private Unternehmen unternehmen;


}
