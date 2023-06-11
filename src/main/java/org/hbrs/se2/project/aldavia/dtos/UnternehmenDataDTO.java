package org.hbrs.se2.project.aldavia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UnternehmenDataDTO {
    private String name;
    private String profileLink;
}
