package org.hbrs.se2.project.aldavia.service;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StudentService {

    public static final String FROM_DB = " from DB";

    private final StudentRepository studentRepository;

    private final QualifikationenService qualifikationenService = new QualifikationenService();

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    /**
     * Get the student profile of a student
     * @param username The username of the student
     * @return Student
     * @throws ProfileException if student not found
     */
    public Student getStudent(String username) throws ProfileException {
        logger.info("Getting student " + username + FROM_DB);
        Optional<Student> student = studentRepository.findByUserID(username);
        if (student.isPresent()) {
            return student.get();
        }
        else {
            logger.error("Student " + username + " not found");
            throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
        }
    }

    /**
     * Update the student information
     * @param student The student
     * @param changeStudentInformationDTO The DTO with the new information
     * @throws ProfileException if student not found
     */
    public void updateStudentInformation(Student student, StudentProfileDTO changeStudentInformationDTO) throws ProfileException {
        logger.info("Updating student " + student.getUser().getUserid() + FROM_DB);
        try {
            User user = student.getUser();
            updateUserData(user, changeStudentInformationDTO);
            updateStudentData(student, changeStudentInformationDTO);
            logger.info("Saving updated student " + student.getUser().getUserid());
            studentRepository.save(student);
        }
        catch (Exception e) {
            throw new ProfileException("Error while updating student information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

    private void updateUserData(User user, StudentProfileDTO changeStudentInformationDTO){
        if (changeStudentInformationDTO.getBeschreibung() != null) {
            user.setBeschreibung(changeStudentInformationDTO.getBeschreibung());
        }

        if (changeStudentInformationDTO.getEmail() != null) {
            user.setEmail(changeStudentInformationDTO.getEmail());
        }

        if (changeStudentInformationDTO.getTelefonnummer() != null) {
            user.setPhone(changeStudentInformationDTO.getTelefonnummer());
        }

        if (changeStudentInformationDTO.getProfilbild() != null) {
            user.setProfilePicture(changeStudentInformationDTO.getProfilbild());
        }
    }

    private void updateStudentData(Student student, StudentProfileDTO changeStudentInformationDTO){
        if (changeStudentInformationDTO.getVorname() != null) {
            student.setVorname(changeStudentInformationDTO.getVorname());
        }

        if (changeStudentInformationDTO.getNachname() != null) {
            student.setNachname(changeStudentInformationDTO.getNachname());
        }

        if (changeStudentInformationDTO.getGeburtsdatum() != null) {
            student.setGeburtsdatum(changeStudentInformationDTO.getGeburtsdatum());
        }

        if (changeStudentInformationDTO.getLebenslauf() != null) {
            student.setLebenslauf(changeStudentInformationDTO.getLebenslauf());
        }

        if (changeStudentInformationDTO.getStudienbeginn() != null) {
            student.setStudienbeginn(changeStudentInformationDTO.getStudienbeginn());
        }

        if (changeStudentInformationDTO.getStudiengang() != null) {
            student.setStudiengang(changeStudentInformationDTO.getStudiengang());
        }

        if (changeStudentInformationDTO.getMatrikelNummer() != null) {
            student.setMatrikelNummer(changeStudentInformationDTO.getMatrikelNummer());
        }

        if (changeStudentInformationDTO.getLebenslauf() != null) {
            student.setLebenslauf(changeStudentInformationDTO.getLebenslauf());
        }
    }

    /**
     * Delete the student
     * @param student The student
     * @throws ProfileException if student not found
     */
    @Transactional
    public void deleteStudent(Student student) throws ProfileException {
        logger.info("Deleting student " + student.getUser().getUserid() + FROM_DB);
        try {

            if (student.getQualifikationen() != null) {
                List<Qualifikation> qualifikationen = new ArrayList<>(student.getQualifikationen());
                for (Qualifikation qualifikation : qualifikationen) {
                    student.setQualifikationen(null);
                    qualifikationenService.removeQualifikation(qualifikation);
                }
            }

            if(student.getTaetigkeitsfelder() != null) {
                List<Taetigkeitsfeld> taetigkeitsfelder = new ArrayList<>(student.getTaetigkeitsfelder());
                for (Taetigkeitsfeld taetigkeitsfeld : taetigkeitsfelder) {
                    student.removeTaetigkeitsfeld(taetigkeitsfeld);
                }
            }

            if (student.getSprachen() != null) {
                List<Sprache> sprachen = new ArrayList<>(student.getSprachen());
                for (Sprache sprache : sprachen) {
                    student.removeSprache(sprache);
                }
            }

            if (student.getKenntnisse() != null) {
                List<Kenntnis> kenntnisse = new ArrayList<>(student.getKenntnisse());
                for (Kenntnis kenntnis : kenntnisse) {
                    student.removeKenntnis(kenntnis);
                }
            }
            studentRepository.save(student);
            studentRepository.delete(student);
            logger.info("Deletion successfully!");
        }
        catch (Exception e) {
            throw new ProfileException("Error while deleting student information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

    /**
     * Create a new student
     * @param student The student
     * @throws ProfileException if student not found
     */
    public void createOrUpdateStudent(Student student) throws ProfileException {
        logger.info("Creating or Updating student " + student.getUser().getUserid() + FROM_DB);
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new ProfileException("Error while creating student information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

}
