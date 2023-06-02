package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStudentProfileDTO {
    private AddStudentInformationDTO addStudentInformationDTO;
    private ChangeStudentInformationDTO changeStudentInformationDTO;
    private DeletionStudentInformationDTO deletionStudentInformationDTO;
}
