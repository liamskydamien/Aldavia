package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.StudentProfileDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Component
public class ProfileControl {

    @Autowired
    private StudentRepository studentRepository;

    public StudentProfileDTO getStudentProfile(String username) {
        Optional<Student> awaitStudent = studentRepository.findByUserID(username);
        if (awaitStudent.isPresent()) {
            Student student = awaitStudent.get();
            System.out.println("Loaded student: " + student.getVorname() + " " + student.getNachname());
            return transformStudentProfileDTO(student);
        }
        else {
            // TODO Add exception handling
            throw new IllegalArgumentException("Student not found");
        }
    }

    public StudentProfileDTO transformStudentProfileDTO(Student student) {
        //Create new StudentProfileDTOImpl and set values
        StudentProfileDTOImpl studentProfileDTO = new StudentProfileDTOImpl();
        studentProfileDTO.setVorname(student.getVorname());
        studentProfileDTO.setNachname(student.getNachname());
        studentProfileDTO.setMatrikelNummer(student.getMatrikelNummer());
        studentProfileDTO.setStudiengang(student.getStudiengang());
        studentProfileDTO.setStudienbeginn(student.getStudienbeginn());
        studentProfileDTO.setGeburtsdatum(student.getGeburtsdatum());

        //Return StudentProfileDTOImpl
        return studentProfileDTO;
    }

}
