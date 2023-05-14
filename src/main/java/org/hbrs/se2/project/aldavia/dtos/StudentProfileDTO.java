package org.hbrs.se2.project.aldavia.dtos;

import java.time.LocalDate;

public interface StudentProfileDTO extends ProfileDTO {
    String getVorname();
    String getNachname();
    String getMatrikelNummer();
    String getStudiengang();
    LocalDate getStudienbeginn();
    LocalDate getGeburtsdatum();
    // more fields follow soon TODO! Add more fields
}
