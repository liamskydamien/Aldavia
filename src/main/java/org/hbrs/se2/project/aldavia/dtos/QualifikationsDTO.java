package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualifikationsDTO {
    private String bezeichnung;
    private String beschreibung;
    private String bereich;
    private String institution;
    private String beschaeftigungsart;
    private LocalDate von;
    private LocalDate bis;
    private int id;
}
