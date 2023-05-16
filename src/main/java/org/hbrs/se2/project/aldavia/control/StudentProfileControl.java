package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.StudentProfileDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
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
            Optional<Student> awaitStudent = studentRepository.findByUserID(username);
            if (awaitStudent.isPresent()) {
                Student student = awaitStudent.get();
                System.out.println("Loaded student: " + student.getVorname() + " " + student.getNachname());
                return transformStudentProfileDTO(student);
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
            Optional<Student> awaitStudent = studentRepository.findByUserID(username);
            if (awaitStudent.isPresent()) {
                Student studentFromDB = awaitStudent.get();
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

    /**
     * Transform Student to StudentProfileDTO
     * @param student The student
     * @return StudentProfileDTO
     */
    private StudentProfileDTO transformStudentProfileDTO(Student student) {

        // Get Email
        String email = student.getUser().getEmail();

        //Create new StudentProfileDTOImpl and set values
        StudentProfileDTOImpl studentProfileDTO = new StudentProfileDTOImpl();
        studentProfileDTO.setVorname(student.getVorname());
        studentProfileDTO.setNachname(student.getNachname());
        studentProfileDTO.setMatrikelNummer(student.getMatrikelNummer());
        studentProfileDTO.setStudiengang(student.getStudiengang());
        studentProfileDTO.setStudienbeginn(student.getStudienbeginn());
        studentProfileDTO.setGeburtsdatum(student.getGeburtsdatum());
        studentProfileDTO.setEmail(email);
        studentProfileDTO.setKenntnisse(student.getKenntnisse().stream().map(Kenntnis::getBezeichnung).toList());
        //studentProfileDTO.setSprachen();
        //studentProfileDTO.setQualifikationen();
        //Return StudentProfileDTOImpl
        return studentProfileDTO;
    }

}
