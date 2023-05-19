package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
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
    private StudentRepository studentRepository;

    /**
     * Get the student profile of a student
     * @param username The username of the student
     * @return StudentProfileDTO
     * @throws ProfileException
     */
    public StudentProfileDTO getStudentProfile(String username) throws ProfileException{
        try {
            System.out.println("Loading student profile for user: " + username);
            Optional<Student> awaitStudent = studentRepository.findByUserID(username);
            if (awaitStudent.isPresent()) {
                Student student = awaitStudent.get();
                System.out.println("Loaded student: " + student.getVorname() + " " + student.getNachname());
                return DTOTransformator.transformStudentProfileDTO(student);
            } else {
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
    public boolean createAndUpdateStudentProfile(StudentProfileDTO student, String username) throws ProfileException {
        // Gets student from database
        try {
            System.out.println("Finding student with username: " + username);
            Optional<Student> awaitStudent = studentRepository.findByUserID(username);
            if (awaitStudent.isPresent()) {
                Student studentFromDB = awaitStudent.get();
                System.out.println("Found student: " + studentFromDB.getVorname() + " " + studentFromDB.getNachname());
                // Set values
                studentFromDB.setVorname(student.getVorname());
                studentFromDB.setNachname(student.getNachname());
                studentFromDB.setMatrikelNummer(student.getMatrikelNummer());
                studentFromDB.setStudiengang(student.getStudiengang());
                studentFromDB.setStudienbeginn(student.getStudienbeginn());
                studentFromDB.setGeburtsdatum(student.getGeburtsdatum());
                // Save student
                studentRepository.save(studentFromDB);
                return true;
            } else {
                throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.ProfileNotFound);
            }
        }
        catch (Exception e) {
            throw new ProfileException("Error while saving student profile", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

    private Student updateStudentProfile(Student student ,StudentProfileDTO studentDTO){
        student.setVorname(studentDTO.getVorname());
        student.setNachname(studentDTO.getNachname());
        student.setMatrikelNummer(studentDTO.getMatrikelNummer());
        student.setStudiengang(studentDTO.getStudiengang());
        student.setStudienbeginn(studentDTO.getStudienbeginn());
        student.setGeburtsdatum(studentDTO.getGeburtsdatum());
        return student;
    }
}
