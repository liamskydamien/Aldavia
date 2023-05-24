package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletionStudentInformationDTO {
    private List<KenntnisDTO> kenntnisse;
    private List<QualifikationsDTO> qualifikationen;
    private List<SpracheDTO> sprachen;
    private List<TaetigkeitsfeldDTO> taetigkeitsfelder;
}
