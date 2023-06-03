package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.ChangeStudentInformationDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QualifikationenService qualifikationenService;



    /**
     * Get the student profile of a student
     * @param username The username of the student
     * @return Student
     * @throws ProfileException if student not found
     */
    public Student getStudent(String username) throws ProfileException {
        Optional<Student> student = studentRepository.findByUserID(username);
        if (student.isPresent()) {
            return student.get();
        }
        else {
            throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
        }
    }

    /**
     * Update the student information
     * @param student The student
     * @param changeStudentInformationDTO The DTO with the new information
     * @throws ProfileException if student not found
     */
    public void updateStudentInformation(Student student, ChangeStudentInformationDTO changeStudentInformationDTO) throws ProfileException {
        try {

            User user = student.getUser();
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

            if (changeStudentInformationDTO.getProfilbild() != null) {
                user.setProfilePicture(changeStudentInformationDTO.getProfilbild());
            }

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

            if (changeStudentInformationDTO.getMatrikelnummer() != null) {
                student.setMatrikelNummer(changeStudentInformationDTO.getMatrikelnummer());
            }

            if (changeStudentInformationDTO.getLebenslauf() != null) {
                student.setLebenslauf(changeStudentInformationDTO.getLebenslauf());
            }

            studentRepository.save(student);
        }
        catch (Exception e) {
            throw new ProfileException("Error while updating student information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

    /**
     * Delete the student
     * @param student The student
     * @throws ProfileException if student not found
     */
    @Transactional
    public void deleteStudent(Student student) throws ProfileException {
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
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            throw new ProfileException("Error while creating student information", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }

}
