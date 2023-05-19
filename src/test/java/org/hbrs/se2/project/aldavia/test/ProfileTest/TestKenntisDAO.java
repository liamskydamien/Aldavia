package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.checkerframework.checker.units.qual.K;
import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import org.hbrs.se2.project.aldavia.dtos.impl.KenntnisDTOImpl;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.hbrs.se2.project.aldavia.dao.KenntnisseDAO;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;

import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestKenntisDAO {
    @Autowired
    private KenntnisseRepository kenntnisRepository;

    @Autowired
    private KenntnisseDAO kenntnisDAO;

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Test
    public void testAddKenntnis() throws PersistenceException {
        KenntnisDTOImpl kenntnis = new KenntnisDTOImpl();
        kenntnis.setBezeichnung("Test");
        try{
            Kenntnis testKenntnis = kenntnisDAO.addKenntnis(kenntnis);
            assertEquals(testKenntnis.getBezeichnung(), kenntnis.getBezeichnung());
            assertTrue(kenntnisRepository.findById(testKenntnis.getBezeichnung()).isPresent());
            Kenntnis testKenntnis2 = kenntnisDAO.addKenntnis(kenntnis);
            assertEquals(testKenntnis2.getBezeichnung(), kenntnis.getBezeichnung());
            kenntnisRepository.deleteById(testKenntnis.getBezeichnung());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testDeleteKenntnis(){
        KenntnisDTOImpl kenntnis = new KenntnisDTOImpl();
        kenntnis.setBezeichnung("Test");
        try{
            Kenntnis testKenntnis = kenntnisDAO.addKenntnis(kenntnis);
            kenntnisDAO.deleteKenntnis(testKenntnis);
            assertFalse(kenntnisRepository.findById(testKenntnis.getBezeichnung()).isPresent());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testAddAndDeleteStudent(){
        KenntnisDTOImpl kenntnis = new KenntnisDTOImpl();
        kenntnis.setBezeichnung("Test");
        Student student = testStudentFactory.createStudent();
        try{
            Kenntnis testKenntnis = kenntnisDAO.addKenntnis(kenntnis);
            kenntnisDAO.addStudentToKenntnis(testKenntnis, student);
            assertTrue(testKenntnis.getStudenten().contains(student));
            kenntnisDAO.removeStudentFromKenntnis(testKenntnis, student);
            assertFalse(testKenntnis.getStudenten().contains(student));
            kenntnisDAO.deleteKenntnis(testKenntnis);
            assertFalse(kenntnisRepository.findById(testKenntnis.getBezeichnung()).isPresent());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }
}
