package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StellenanzeigeDTO {
    private int id;
    private String bezeichnung;
    private String beschreibung;
    private String beschaeftigungsverhaeltnis;
    private LocalDate start;
    private LocalDate ende;
    private LocalDate erstellungsdatum;
    private String bezahlung;
    private String beschaeftigungsumfang;
    private List<TaetigkeitsfeldDTO> taetigkeitsfelder;
    private List<BewerbungsDTO> bewerbungen;
    private UnternehmenDataDTO unternehmen;
}
