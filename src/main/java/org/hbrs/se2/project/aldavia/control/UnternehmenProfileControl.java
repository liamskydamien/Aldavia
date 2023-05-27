package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnternehmenProfileControl {

    @Autowired
    UnternehmenControl unternehmenControl;

    @Autowired
    AdresseControl adresseControl;

    public UnternehmenProfileDTO getUnternehmenProfile(String username ) {
        try {
            System.out.println("Finding student with username: " + username);
            Unternehmen unternehmen = unternehmenControl.getUnternehmen(username);
            StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student);
            System.out.println("Found student: " + studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
            return studentProfileDTO;
        }
        catch (Exception e) {
            throw new ProfileException("Error while loading student profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }

    }


}
