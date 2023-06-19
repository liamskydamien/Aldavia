package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BewerbungsDTO {
    private int id;
    private LocalDate datum;
    private StudentProfileDTO student;
    private StellenanzeigeDTO stellenanzeige;
    private String status;
    private String bewerbungsSchreiben;
}
