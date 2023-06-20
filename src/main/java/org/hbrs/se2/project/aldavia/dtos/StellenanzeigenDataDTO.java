package org.hbrs.se2.project.aldavia.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StellenanzeigenDataDTO {
    private List<BewerbungsDTO> bewerbungen;
    private StellenanzeigeDTO stellenanzeige;
}
