package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private KenntnisseControl kenntnisseControl;

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
            Optional<Student> student = studentRepository.findByUserID(username);
            if (student.isPresent()) {
                StudentProfileDTO studentProfileDTO = studentProfileDTOFactory.createStudentProfileDTO(student.get());
                System.out.println("Found student: " + studentProfileDTO.getVorname() + " " + studentProfileDTO.getNachname());
                return studentProfileDTO;
            }
            else {
                throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.ProfileNotFound);
            }
        }
        catch (Exception e) {
            throw new ProfileException("Error while loading student profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

    /**
     * Create and update a student profile
     * @param student The student profile
     * @param username The username of the student
     * @return boolean
     * @throws ProfileException
     */
    public void createAndUpdateStudentProfile(StudentProfileDTO student, String username) throws ProfileException {
        // Gets student from database
        try {
            System.out.println("Finding student with username: " + username);
            Optional<Student> awaitStudent = studentRepository.findByUserID(username);
            if (awaitStudent.isPresent()) {
                Student studentFromDB = awaitStudent.get();

                // Add Kenntnisse
                for (KenntnisDTO kenntnis : student.getKenntnisse()) {
                    kenntnisseControl.removeStudentFromKenntnis(kenntnis, studentFromDB);
                }


            }
        }
        catch (PersistenceException e) {
            throw new ProfileException("Error while updating student profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }
}
