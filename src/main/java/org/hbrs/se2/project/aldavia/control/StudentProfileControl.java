package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class StudentProfileControl {

    private final StudentService studentService;

    private final KenntnisseService kenntnisseService;

    private final QualifikationenService qualifikationenService;

    private final SprachenService sprachenService;

    private final TaetigkeitsfeldService taetigkeitsfeldService;

    private final StudentProfileDTOFactory studentProfileDTOFactory;

    private final Logger logger = LoggerFactory.getLogger(StudentProfileControl.class);



    /**
     * Get the student profile of a student
     * @param username The username of the student
     * @return StudentProfileDTO
     */
    public StudentProfileDTO getStudentProfile(String username) throws ProfileException{
        try {
            logger.info("Getting Student from Database with username: " + username);
            Student student = studentService.getStudent(username);
            StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student);
            logger.info("Found Student: " + student.getVorname() + " " + student.getNachname());
            return studentProfileDTO;
        }
        catch (Exception e) {
            throw new ProfileException("Error while loading student profile" + e,ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
        //
    }

    public void updateStudentProfile(StudentProfileDTO updatedVersion, String username) throws ProfileException, PersistenceException {
        StudentProfileDTO oldVersion = getStudentProfile(username);
        if (oldVersion == null) {
            throw new ProfileException("Student does not exist", ProfileException.ProfileExceptionType.STUDENT_DOES_NOT_EXIST);
        }
        else {
            logger.info("Getting Student from Database with username: " + username);

            Student student = studentService.getStudent(username);

            logger.info("Found Student: " + student.getVorname() + " " + student.getNachname());

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
            removeAttributes(student);

            // Add inputs to Lists
            addAttributes(student, updatedVersion);

            // Save student information
            logger.info("Updating Student in Database: " + student.getVorname() + " " + student.getNachname());
            studentService.createOrUpdateStudent(student);
            logger.info("Updated Student in Database: " + student.getVorname() + " " + student.getNachname());
        }
    }

    private void removeAttributes(Student student){
        List<Kenntnis> kenntnisse = student.getKenntnisse() != null? new ArrayList<>(student.getKenntnisse()) : new ArrayList<>();
        List<Sprache> sprachen = student.getSprachen() != null? new ArrayList<>(student.getSprachen()) : new ArrayList<>();
        List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder() != null? new ArrayList<>(student.getTaetigkeitsfelder()) : new ArrayList<>();
        List<Qualifikation> qualifikationen = student.getQualifikationen() != null? new ArrayList<>(student.getQualifikationen()) : new ArrayList<>();

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
            qualifikationenService.removeQualifikation(qualifikation);
        }
    }

    private void addAttributes(Student student, StudentProfileDTO updatedVersion) throws PersistenceException {
        // Add Kenntnisse
        if (updatedVersion.getKenntnisse() != null) {
            for (KenntnisDTO kenntnisDTO : updatedVersion.getKenntnisse()) {
                kenntnisseService.addStudentToKenntnis(kenntnisDTO, student);
            }
        }

        // Add Sprachen
        if (updatedVersion.getSprachen() != null) {
            for (SpracheDTO spracheDTO : updatedVersion.getSprachen()) {
                sprachenService.addStudentToSprache(spracheDTO, student);
            }
        }

        // Add Taetigkeitsfelder
        if(updatedVersion.getTaetigkeitsfelder() != null) {
            for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : updatedVersion.getTaetigkeitsfelder()) {
                taetigkeitsfeldService.addStudentToTaetigkeitsfeld(taetigkeitsfeldDTO, student);
            }
        }

        // Add Qualifikationen
        if (updatedVersion.getQualifikationen() != null) {
            for (QualifikationsDTO qualifikationsDTO : updatedVersion.getQualifikationen()) {
                qualifikationenService.addUpdateQualifikation(qualifikationsDTO, student);
            }
        }
    }

}
