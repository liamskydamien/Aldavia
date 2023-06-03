package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
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
     * @param updateStudentProfileDTO The DTO containing the information to update the student profile
     *                                (DeletionStudentInformationDTO, ChangeStudentInformationDTO, AddStudentInformationDTO)
     *                                The DTOs are used to update the student profile
     * @throws ProfileException If an error occurs while updating the student profile
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

            System.out.println("Qualifikationen: " + addStudentInformationDTO.getQualifikationen());

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

    public void updateStudentProfile(StudentProfileDTO updatedVersion, String username) throws ProfileException, PersistenceException {
        StudentProfileDTO oldVersion = getStudentProfile(username);
        if (oldVersion == null) {
            throw new ProfileException("Student does not exist", ProfileException.ProfileExceptionType.StudentDoesNotExist);
        }
        else {
            System.out.println("Getting Student from Database with username: " + username);

            Student student = studentControl.getStudent(username);

            System.out.println("Found Student: " + student.getVorname() + " " + student.getNachname());

            // Change Data in Student
            student.setVorname(updatedVersion.getVorname());
            student.setNachname(updatedVersion.getNachname());
            student.setGeburtsdatum(updatedVersion.getGeburtsdatum());
            student.getUser().setEmail(updatedVersion.getEmail());
            student.setStudiengang(updatedVersion.getStudiengang());
            student.setStudienbeginn(updatedVersion.getStudienbeginn());
            student.setMatrikelNummer(updatedVersion.getMatrikelNummer());
            student.setLebenslauf(updatedVersion.getLebenslauf());
            student.getUser().setBeschreibung(updatedVersion.getBeschreibung());
            student.getUser().setPhone(updatedVersion.getTelefonnummer());
            student.getUser().setProfilePicture(updatedVersion.getProfilbild());

            // Remove inputs from Lists

            List<Kenntnis> kenntnisse = student.getKenntnisse() != null? student.getKenntnisse() : new ArrayList<>();
            List<Sprache> sprachen = student.getSprachen() != null? student.getSprachen() : new ArrayList<>();
            List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder() != null? student.getTaetigkeitsfelder() : new ArrayList<>();
            List<Qualifikation> qualifikationen = student.getQualifikationen() != null? student.getQualifikationen() : new ArrayList<>();

            // Remove Kenntnisse
            for (Kenntnis kenntnis : kenntnisse) {
                student.removeKenntnis(kenntnis);
            }

            // Remove Sprachen
            for (Sprache sprache : sprachen) {
                student.removeSprache(sprache);
            }

            // Remove Taetigkeitsfelder
            for (Taetigkeitsfeld taetigkeitsfeld : taetigkeitsfelder) {
                student.removeTaetigkeitsfeld(taetigkeitsfeld);
            }

            // Remove Qualifikationen
            for (Qualifikation qualifikation : qualifikationen) {
                student.removeQualifikation(qualifikation);
            }

            // Add Kenntnisse
            if (updatedVersion.getKenntnisse() != null) {
                for (KenntnisDTO kenntnisDTO : updatedVersion.getKenntnisse()) {
                    kenntnisseControl.addStudentToKenntnis(kenntnisDTO, student);
                }
            }

            // Add Sprachen
            if (updatedVersion.getSprachen() != null) {
                for (SpracheDTO spracheDTO : updatedVersion.getSprachen()) {
                    sprachenControl.addStudentToSprache(spracheDTO, student);
                }
            }

            // Add Taetigkeitsfelder
            if(updatedVersion.getTaetigkeitsfelder() != null) {
                for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : updatedVersion.getTaetigkeitsfelder()) {
                    taetigkeitsfeldControl.addStudentToTaetigkeitsfeld(taetigkeitsfeldDTO, student);
                }
            }

            // Add Qualifikationen
            if (updatedVersion.getQualifikationen() != null) {
                for (QualifikationsDTO qualifikationsDTO : updatedVersion.getQualifikationen()) {
                    qualifikationenControl.addUpdateQualifikation(qualifikationsDTO, student);
                }
            }
            System.out.println("Updated Student: " + student.getVorname() + " " + student.getNachname());

            // Save student information
            studentControl.createOrUpdateStudent(student);
            System.out.println("Saved Student in Database: " + student.getVorname() + " " + student.getNachname());
        }
    }

}
