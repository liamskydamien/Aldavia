package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BewerbungsDTO {
    private int id;
    private LocalDate datum;
    private int studentId;
    private int stellenanzeigeId;
}
