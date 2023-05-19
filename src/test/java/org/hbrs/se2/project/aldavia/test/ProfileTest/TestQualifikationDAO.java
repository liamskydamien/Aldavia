package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dao.QualifikationDAO;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestQualifikationDAO {

    @Autowired
    private QualifikationRepository qualifikationRepository;

    @Autowired
    private QualifikationDAO qualifikationDAO;

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Test
    public void testAddQualifikation() {
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBezeichnung("Test");
        qualifikation.setBereich("Test");
        try{
            Qualifikation testQualifikation = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation, qualifikation);
            assertTrue(qualifikationRepository.findById(testQualifikation.getId()).isPresent());
            Qualifikation testQualifikation2 = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation2, qualifikation);
            qualifikationRepository.deleteById(testQualifikation.getId());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void  testDeleteQualification(){
        Qualifikation qualifikation = new Qualifikation();
        qualifikation.setBezeichnung("Test");
        qualifikation.setBereich("Test");
        try{
            Qualifikation testQualifikation = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation, qualifikation);
            assertTrue(qualifikationRepository.findById(testQualifikation.getId()).isPresent());
            qualifikationDAO.deleteQualifikation(testQualifikation);
            assertFalse(qualifikationRepository.findById(testQualifikation.getId()).isPresent());
            PersistenceException persistenceException = assertThrows(PersistenceException.class, () -> qualifikationDAO.deleteQualifikation(testQualifikation));
            assertEquals(persistenceException.getPersistenceExceptionType(), PersistenceException.PersistenceExceptionType.ErrorWhileDeletingQualifikation);
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testAddAndRemoveStudentToQualifikation() {

        try {
            Qualifikation qualifikation = new Qualifikation();
            qualifikation.setBezeichnung("Test");
            qualifikation.setBereich("Test");
            Student student = testStudentFactory.createStudent();
            qualifikationDAO.addStudentToQualifikation(student, qualifikation);
            assertTrue(qualifikation.getStudenten().contains(student));
            PersistenceException persistenceException = assertThrows(PersistenceException.class, () -> qualifikationDAO.addStudentToQualifikation(student, qualifikation));
            assertEquals(persistenceException.getPersistenceExceptionType(), PersistenceException.PersistenceExceptionType.ErrorWhileAddingStudentToQualifikation);
            qualifikationDAO.removeStudentFromQualifikation(student, qualifikation);
            assertFalse(qualifikation.getStudenten().contains(student));
            qualifikationDAO.deleteQualifikation(qualifikation);
            testStudentFactory.deleteStudent();
        } catch (PersistenceException e) {
            System.out.println("Fehler");
        }
    }
}
