package org.hbrs.se2.project.aldavia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BewerbungsDataDTO {
    private int id;
    private LocalDate datum;
    private StellenanzeigeDTO stellenanzeige;
    private String status;
    private String bewerbungsSchreiben;
    private StudentDataDTO student;
    private UnternehmenDataDTO unternehmen;
}
