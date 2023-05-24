package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileDTO{
    private String vorname;
    private String nachname;
    private String matrikelNummer;
    private String studiengang;
    private LocalDate studienbeginn;
    private LocalDate geburtsdatum;
    private List<KenntnisDTO> kenntnisse;
    private List<TaetigkeitsfeldDTO> taetigkeitsfelder;
    private List<QualifikationsDTO> qualifikationen;
    private String lebenslauf;
    private String email;
    private String telefonnummer;
    private String profilbild;
    private List<SpracheDTO> sprachen;
    private String beschreibung;
}
