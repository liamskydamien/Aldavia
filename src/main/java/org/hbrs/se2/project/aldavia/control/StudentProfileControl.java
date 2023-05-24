package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentControl studentControl;

    @Autowired
    private KenntnisseControl kenntnisseControl;

    @Autowired
    private QualifikationenControl qualifikationenControl;

    @Autowired
    private SprachenControl sprachenControl;

    @Autowired
    private TaetigkeitsfeldControl taetigkeitsfeldControl;

    private final StudentProfileDTOFactory studentProfileDTOFactory = StudentProfileDTOFactory.getInstance();

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
                List<KenntnisDTO> deletionKenntnisse = new ArrayList<>(deletionStudentInformationDTO.getKenntnisse());
                for (KenntnisDTO kenntnisDTO : deletionKenntnisse) {
                    kenntnisseControl.removeStudentFromKenntnis(kenntnisDTO, student);
                }
            }
            if (deletionStudentInformationDTO.getSprachen() != null) {
                List<SpracheDTO> deletionSprachen = new ArrayList<>(deletionStudentInformationDTO.getSprachen());
                for (SpracheDTO spracheDTO : deletionSprachen) {
                    sprachenControl.removeStudentFromSprache(spracheDTO, student);
                }
            }

            if (deletionStudentInformationDTO.getTaetigkeitsfelder() != null) {
                List<TaetigkeitsfeldDTO> deletionTaetigkeitsfelder = new ArrayList<>(deletionStudentInformationDTO.getTaetigkeitsfelder());
                for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : deletionTaetigkeitsfelder) {
                    taetigkeitsfeldControl.removeStudentFromTaetigkeitsfeld(taetigkeitsfeldDTO, student);
                }
            }


            if (deletionStudentInformationDTO.getQualifikationen() != null) {
                List<QualifikationsDTO> deletionQualifikationen = new ArrayList<>(deletionStudentInformationDTO.getQualifikationen());
                for (QualifikationsDTO qualifikationsDTO : deletionQualifikationen) {
                    student.setQualifikationen(null);
                    qualifikationenControl.removeQualifikation(qualifikationsDTO);
                }
            }


            // Add Information

            if (addStudentInformationDTO.getKenntnisse() != null) {
                List<KenntnisDTO> addKenntnisse = new ArrayList<>(addStudentInformationDTO.getKenntnisse());
                for (KenntnisDTO kenntnisDTO : addKenntnisse) {
                    kenntnisseControl.addStudentToKenntnis(kenntnisDTO, student);
                }
            }

            if (addStudentInformationDTO.getSprachen() != null) {
                List<SpracheDTO> addSprachen = new ArrayList<>(addStudentInformationDTO.getSprachen());
                for (SpracheDTO spracheDTO : addSprachen) {
                    sprachenControl.addStudentToSprache(spracheDTO, student);
                }
            }

            if (addStudentInformationDTO.getTaetigkeitsfelder() != null) {
                List<TaetigkeitsfeldDTO> addTaetigkeitsfelder = new ArrayList<>(addStudentInformationDTO.getTaetigkeitsfelder());
                for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : addTaetigkeitsfelder) {
                    taetigkeitsfeldControl.addStudentToTaetigkeitsfeld(taetigkeitsfeldDTO, student);
                }
            }


            if (addStudentInformationDTO.getQualifikationen() != null) {
                List<QualifikationsDTO> addQualifikationen = new ArrayList<>(addStudentInformationDTO.getQualifikationen());
                for (QualifikationsDTO qualifikationsDTO : addQualifikationen) {
                    qualifikationenControl.addUpdateQualifikation(qualifikationsDTO, student);
                }
            }

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
