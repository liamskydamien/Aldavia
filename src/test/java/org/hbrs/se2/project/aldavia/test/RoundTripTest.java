package org.hbrs.se2.project.aldavia.test;

import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoundTripTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;


    @Test
    /**
     * Round Triping Test mit einer einfachen Strecke (C-R-Ass-D).
     * Dieses Muster für Unit-Tests wird in der Vorlesung SE-2 eingeführt (Kapitel 6).
     *
     */

    void createReadAndDeleteAUser() {

        // Schritt 1: C = Create (hier: Erzeugung und Abspeicherung mit der Method save()
        // Anlegen eines Users. Eine ID wird automatisch erzeugt durch JPA
        User user = new User();
        user.setEmail("test@myserver.de");

        // Anlegen eines Studenten
        Student student = new Student();
        student.setUser(user);
        student.setVorname( "Torben" );
        student.setNachname("Michel");

        // und ab auf die DB damit (save!)
        userRepository.save( user );
        studentRepository.save(student);

        // Da die ID auto-generiert wurde, müssen wir uns die erzeugte ID nach dem Abspeichern merken:
        int idTmp = user.getId();

        // Schritt 2: R = Read (hier: Auslesen über die Methode find()
        Optional<User> wrapper = userRepository.findById( idTmp );
        User userAfterCreate = null;
        if ( wrapper.isPresent() ) {
            userAfterCreate = wrapper.get();
        }
        System.out.println("User: " + userAfterCreate);

        // Auslesen des Studenten
        Optional<Student> wrapper2 = studentRepository.findByUser(userAfterCreate);
        Student studentAfterCreate = null;
        if ( wrapper2.isPresent() ) {
            studentAfterCreate = wrapper2.get();
        }
        System.out.println("Student: " + studentAfterCreate);

        // Schritt 3: Ass = Assertion: Vergleich der vorhandenen Objekte auch Gleichheit...
        assertEquals( studentAfterCreate.getNachname() , "Michel" );
        assertEquals( studentAfterCreate.getVorname() , "Torben" );
        // ... sowie auf Identität
        assertNotSame( user , userAfterCreate );
        assertNotSame(student, studentAfterCreate);

        // Schritt 4: D = Deletion, also Löschen des Users, um Datenmüll zu vermeiden
        int studentTmpId = studentAfterCreate.getStudentId();
        studentRepository.deleteById(studentTmpId);
        userRepository.deleteById(idTmp);

        // Schritt 4.1: Wir sind vorsichtig und gucken, ob der User wirklich gelöscht wurde ;-)
        Optional<User> wrapperAfterDelete = userRepository.findById(idTmp);
        Optional<Student> wrapperStudentAfterDelete = studentRepository.findById(studentTmpId);

        System.out.println("Wrapper: " + wrapperAfterDelete);
        System.out.println("Student-Wrapper: " + wrapperStudentAfterDelete);

        assertFalse( wrapperAfterDelete.isPresent() );
        assertFalse( wrapperStudentAfterDelete.isPresent() );
    }

    @AfterEach
    public void deleteUser(){
        // Hier könnte man nach einem RoundTrip die DB noch weiter bereinigen
    }
}
