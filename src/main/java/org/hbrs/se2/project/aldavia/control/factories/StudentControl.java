package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.ChangeStudentInformationDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentControl {

    @Autowired
    private StudentRepository studentRepository;

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
            throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.ProfileNotFound);
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
            if (changeStudentInformationDTO.getProfilbild() == null) {
                changeStudentInformationDTO.setProfilbild(student.getUser().getProfilePicture());
            }

            if (changeStudentInformationDTO.getLebenslauf() == null) {
                changeStudentInformationDTO.setLebenslauf(student.getLebenslauf());
            }


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

            if (changeStudentInformationDTO.getVorname() != null) {
                student.setVorname(changeStudentInformationDTO.getVorname());
            }

            if (changeStudentInformationDTO.getNachname() != null) {
                student.setNachname(changeStudentInformationDTO.getNachname());
            }

            if (changeStudentInformationDTO.getGeburtsdatum() != null) {
                student.setGeburtsdatum(changeStudentInformationDTO.getGeburtsdatum());
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
            throw new ProfileException("Error while updating student information", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

    /**
     * Delete the student
     * @param student The student
     * @throws ProfileException if student not found
     */
    public void deleteStudent(Student student) throws ProfileException {
        try {
            studentRepository.delete(student);
        }
        catch (Exception e) {
            throw new ProfileException("Error while deleting student information", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
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
            throw new ProfileException("Error while creating student information", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

}
