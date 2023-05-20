package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dao.StudentDAO;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.SpracheDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.StudentProfileDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.util.DTOTransformator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentProfileControl {

    @Autowired
    private StudentDAO studentDAO;

    /**
     * Get the student profile of a student
     * @param username The username of the student
     * @return StudentProfileDTO
     * @throws ProfileException
     */
    public StudentProfileDTO getStudentProfile(String username) throws ProfileException{
        try {
            System.out.println("Loading student profile for user: " + username);
            Student student = studentDAO.getStudent(username);
            System.out.println("Loaded student: " + student.getVorname() + " " + student.getNachname());
            return DTOTransformator.transformStudentProfileDTO(student);
        }
        catch (PersistenceException persistenceException){
            throw new ProfileException("Profile not found", ProfileException.ProfileExceptionType.ProfileNotFound);
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
            Student studentFromDB = studentDAO.getStudent(username);
            System.out.println("Found student: " + studentFromDB.getVorname() + " " + studentFromDB.getNachname());
            studentDAO.createAndUpdateStudent(student, studentFromDB);
            System.out.println("Updated student: " + studentFromDB.getVorname() + " " + studentFromDB.getNachname());
    }
        catch (PersistenceException e) {
            throw new ProfileException("Error while updating student profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }
}
