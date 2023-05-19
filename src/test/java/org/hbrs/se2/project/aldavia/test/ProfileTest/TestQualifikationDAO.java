package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dao.QualifikationDAO;
import org.hbrs.se2.project.aldavia.dtos.impl.QualifikationsDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
        QualifikationsDTOImpl qualifikation = new QualifikationsDTOImpl();
        qualifikation.setBezeichnung("Test");
        qualifikation.setBereich("Test");
        qualifikation.setBeschreibung("Test");
        qualifikation.setBeschaeftigungsart("Test");
        try{
            Qualifikation testQualifikation = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation.getBezeichnung(), qualifikation.getBezeichnung());
            assertEquals(testQualifikation.getBereich(), qualifikation.getBereich());
            assertEquals(testQualifikation.getBeschreibung(), qualifikation.getBeschreibung());
            assertEquals(testQualifikation.getBeschaeftigungsart(), qualifikation.getBeschaeftigungsart());
            assertTrue(qualifikationRepository.findById(testQualifikation.getId()).isPresent());
            Qualifikation testQualifikation2 = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation2.getBezeichnung(), qualifikation.getBezeichnung());
            assertEquals(testQualifikation2.getBereich(), qualifikation.getBereich());
            assertEquals(testQualifikation2.getBeschreibung(), qualifikation.getBeschreibung());
            assertEquals(testQualifikation2.getBeschaeftigungsart(), qualifikation.getBeschaeftigungsart());
            qualifikationRepository.deleteById(testQualifikation.getId());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void  testDeleteQualification(){
        QualifikationsDTOImpl qualifikation = new QualifikationsDTOImpl();
        qualifikation.setBezeichnung("Test");
        qualifikation.setBereich("Test");
        qualifikation.setBeschreibung("Test");
        qualifikation.setBeschaeftigungsart("Test");
        try{
            Qualifikation testQualifikation = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation.getBereich(), qualifikation.getBereich());
            assertEquals(testQualifikation.getBezeichnung(), qualifikation.getBezeichnung());
            assertEquals(testQualifikation.getBeschreibung(), qualifikation.getBeschreibung());
            assertEquals(testQualifikation.getBeschaeftigungsart(), qualifikation.getBeschaeftigungsart());
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
            QualifikationsDTOImpl qualifikation = new QualifikationsDTOImpl();
            qualifikation.setBezeichnung("Test");
            qualifikation.setBereich("Test");
            qualifikation.setBeschreibung("Test");
            qualifikation.setBeschaeftigungsart("Test");
            Qualifikation newQualifikation = qualifikationDAO.addQualifikation(qualifikation);
            Student student = testStudentFactory.createStudent();
            qualifikationDAO.addStudentToQualifikation(student, newQualifikation);
            assertTrue(newQualifikation.getStudenten().contains(student));
            PersistenceException persistenceException = assertThrows(PersistenceException.class, () -> qualifikationDAO.addStudentToQualifikation(student, newQualifikation));
            assertEquals(persistenceException.getPersistenceExceptionType(), PersistenceException.PersistenceExceptionType.ErrorWhileAddingStudentToQualifikation);
            qualifikationDAO.removeStudentFromQualifikation(student, newQualifikation);
            assertFalse(newQualifikation.getStudenten().contains(student));
            qualifikationDAO.deleteQualifikation(newQualifikation);
            testStudentFactory.deleteStudent();
        } catch (PersistenceException e) {
            System.out.println("Fehler");
        }
    }

    @Test
    public void testUpdateQualifikation(){
        QualifikationsDTOImpl qualifikation = new QualifikationsDTOImpl();
        qualifikation.setBezeichnung("Test");
        qualifikation.setBereich("Test");
        qualifikation.setBeschreibung("Test");
        qualifikation.setBeschaeftigungsart("Test");

        QualifikationsDTOImpl qualifikation2 = new QualifikationsDTOImpl();
        qualifikation2.setBezeichnung("Test2");
        qualifikation2.setBereich("Test2");
        qualifikation2.setBeschreibung("Test2");
        qualifikation2.setBeschaeftigungsart("Test2");

        try{
            Qualifikation testQualifikation = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation.getBezeichnung(), qualifikation.getBezeichnung());
            assertEquals(testQualifikation.getBereich(), qualifikation.getBereich());
            assertEquals(testQualifikation.getBeschreibung(), qualifikation.getBeschreibung());
            assertEquals(testQualifikation.getBeschaeftigungsart(), qualifikation.getBeschaeftigungsart());
            assertTrue(qualifikationRepository.findById(testQualifikation.getId()).isPresent());
            Qualifikation testQualifikation2 = qualifikationDAO.addQualifikation(qualifikation);
            assertEquals(testQualifikation2.getBezeichnung(), qualifikation.getBezeichnung());
            assertEquals(testQualifikation2.getBereich(), qualifikation.getBereich());
            assertEquals(testQualifikation2.getBeschreibung(), qualifikation.getBeschreibung());
            assertEquals(testQualifikation2.getBeschaeftigungsart(), qualifikation.getBeschaeftigungsart());
            qualifikationDAO.updateQualifikation(qualifikation2, testQualifikation2);
            assertEquals(testQualifikation2.getBezeichnung(), qualifikation2.getBezeichnung());
            assertEquals(testQualifikation2.getBereich(), qualifikation2.getBereich());
            assertEquals(testQualifikation2.getBeschreibung(), qualifikation2.getBeschreibung());
            assertEquals(testQualifikation2.getBeschaeftigungsart(), qualifikation2.getBeschaeftigungsart());
            qualifikationRepository.deleteById(testQualifikation.getId());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }
}
