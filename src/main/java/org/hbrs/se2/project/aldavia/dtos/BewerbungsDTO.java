package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BewerbungsDTO {
    private int id;
    private String datum;
    private int studentId;
    private int stellenanzeigeId;
}
