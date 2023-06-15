package org.hbrs.se2.project.aldavia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDataDTO {
    private String vorname;
    private String nachname;
    private String profileLink;
}
