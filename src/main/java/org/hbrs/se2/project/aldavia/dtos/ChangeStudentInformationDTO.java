package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeStudentInformationDTO {
    private String vorname;
    private String nachname;
    private String email;
    private String telefonnummer;
    private LocalDate geburtsdatum;
    private LocalDate studienbeginn;
    private String studiengang;
    private String matrikelnummer;
    private String lebenslauf;
    private String beschreibung;
    private String profilbild;
}
