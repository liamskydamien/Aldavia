package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentControl;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentControl studentControl;

    @Autowired
    private KenntnisseControl kenntnisseControl;

    //@Autowired
    //private QualifikationenControl qualifikationenControl;

    @Autowired
    private SprachenControl sprachenControl;

    @Autowired
    private TaetigkeitsfeldControl taetigkeitsfeldControl;

    private StudentProfileDTOFactory studentProfileDTOFactory = StudentProfileDTOFactory.getInstance();

    /**
     * Get the student profile of a student
     * @param username The username of the student
     * @return StudentProfileDTO
     * @throws ProfileException
     */
    public StudentProfileDTO getStudentProfile(String username) throws ProfileException{
        try {
            System.out.println("Finding student with username: " + username);
            Student student = studentControl.getStudent(username);
            StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student);
            System.out.println("Found student: " + studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
            return studentProfileDTO;
        }
        catch (Exception e) {
            throw new ProfileException("Error while loading student profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

    /**
     * Create and update a student profile
     * @param username The username of the student
     * @return boolean
     * @throws ProfileException
     */
    public void createAndUpdateStudentProfile(UpdateStudentProfileDTO updateStudentProfileDTO, String username) throws ProfileException {
        // Gets student from database
        try {
            // Get student from database
            System.out.println("Finding student with username: " + username);
            Student student = studentControl.getStudent(username);
            System.out.println("Found student: " + student.getVorname() + " " + student.getNachname());

            // Update student information
            DeletionStudentInformationDTO deletionStudentInformationDTO = updateStudentProfileDTO.getDeletionStudentInformationDTO();
            ChangeStudentInformationDTO changeStudentInformationDTO = updateStudentProfileDTO.getChangeStudentInformationDTO();
            AddStudentInformationDTO addStudentInformationDTO = updateStudentProfileDTO.getAddStudentInformationDTO();

            // Delete Information

            if (deletionStudentInformationDTO.getKenntnisse() != null) {
                for (KenntnisDTO kenntnisDTO : deletionStudentInformationDTO.getKenntnisse()) {
                    kenntnisseControl.removeStudentFromKenntnis(kenntnisDTO, student);
                }
            }
            if (deletionStudentInformationDTO.getSprachen() != null) {
                for (SpracheDTO spracheDTO : deletionStudentInformationDTO.getSprachen()) {
                    sprachenControl.removeStudentFromSprache(spracheDTO, student);
                }
            }

            if (deletionStudentInformationDTO.getTaetigkeitsfelder() != null) {
                for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : deletionStudentInformationDTO.getTaetigkeitsfelder()) {
                    taetigkeitsfeldControl.removeStudentFromTaetigkeitsfeld(taetigkeitsfeldDTO, student);
                }
            }

            /*
            if (deletionStudentInformationDTO.getQualifikationen() != null) {
                for (QualifikationsDTO qualifikationsDTO : deletionStudentInformationDTO.getQualifikationen()) {
                    qualifikationControl.removeStudentFromTaetigkeitsfeld(qualifikationsDTO, student);
                }
            }
            */

            // Add Information

            if (addStudentInformationDTO.getKenntnisse() != null) {
                for (KenntnisDTO kenntnisDTO : addStudentInformationDTO.getKenntnisse()) {
                    kenntnisseControl.addStudentToKenntnis(kenntnisDTO, student);
                }
            }

            if (addStudentInformationDTO.getSprachen() != null) {
                for (SpracheDTO spracheDTO : addStudentInformationDTO.getSprachen()) {
                    sprachenControl.addStudentToSprache(spracheDTO, student);
                }
            }

            if (addStudentInformationDTO.getTaetigkeitsfelder() != null) {
                for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : addStudentInformationDTO.getTaetigkeitsfelder()) {
                    taetigkeitsfeldControl.addStudentToTaetigkeitsfeld(taetigkeitsfeldDTO, student);
                }
            }

            /*
            if (addStudentInformationDTO.getQualifikationen() != null) {
                for (QualifikationsDTO qualifikationsDTO : addStudentInformationDTO.getQualifikationen()) {
                    qualifikationControl.addStudentToTaetigkeitsfeld(qualifikationsDTO, student);
                }
            }
            */

            // Change Information
            studentControl.updateStudentInformation(student, changeStudentInformationDTO);

            // Save student information
            studentControl.createOrUpdateStudent(student);

        }
            catch (PersistenceException ex) {
            throw new RuntimeException(ex);
        }
    }
}
