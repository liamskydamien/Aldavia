package org.hbrs.se2.project.aldavia.test.ProfileTest;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.service.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    @Mock
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
    }
    @Test
    public void testGetStudent() throws ProfileException {
        String userId = "testGet";
        Student student = new Student();
        Optional<Student> studentOpt = Optional.of(student);

        given(studentRepository.findByUserID(userId)).willReturn(studentOpt);

        //when
        Student await = studentService.getStudent(userId);

        //then
        assertEquals(student, await);
        verify(studentRepository,times(1)).findByUserID(userId);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void testUpdateStudentInformation() throws ProfileException {
        //given
        User user = new User();
        Student student = new Student();
        student.setUser(user);
        StudentProfileDTO dto = new StudentProfileDTO();

        //when
        studentService.updateStudentInformation(student,dto);
        verify(studentRepository,times(1)).save(student);
        verifyNoMoreInteractions(studentRepository);

        //then -> fällt weg, da void Methode
    }
    @Test
    public void testDeleteStudent() throws ProfileException {
        //given
        User user = new User();
        Student student = new Student();
        student.setUser(user);
        //when
        studentService.deleteStudent(student);
        verify(studentRepository,times(1)).save(student);
        verify(studentRepository,times(1)).delete(student);
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    public void testCreateOrUpdateStudent() throws ProfileException {
        User user = new User();
        Student student = new Student();
        student.setUser(user);

        studentService.createOrUpdateStudent(student);
        verify(studentRepository,times(1)).save(student);
        verifyNoMoreInteractions(studentRepository);
    }
}
