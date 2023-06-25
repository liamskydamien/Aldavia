package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StellenanzeigenDataDTO {
    private List<BewerbungsDTO> bewerbungen;
    private StellenanzeigeDTO stellenanzeige;
}
