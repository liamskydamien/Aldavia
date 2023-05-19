package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dao.TaetigkeitsfeldDAO;
import org.hbrs.se2.project.aldavia.dtos.impl.TaetigkeitsfeldDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TesteTaetigkeitsfeldDAO {
    @Autowired
    private TaetigkeitsfeldDAO taetigkeitsfeldDAO;

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    @Test
    public void testAddTaetigkeitsfeld(){
        TaetigkeitsfeldDTOImpl taetigkeitsfeld = new TaetigkeitsfeldDTOImpl();
        taetigkeitsfeld.setBezeichnung("Test");
        try{
            Taetigkeitsfeld testTaetigkeitsfeld = taetigkeitsfeldDAO.addTaetigkeitsfeld(taetigkeitsfeld);
            assertEquals(testTaetigkeitsfeld.getBezeichnung(), taetigkeitsfeld.getBezeichnung());
            assertTrue(taetigkeitsfeldRepository.findById(testTaetigkeitsfeld.getBezeichnung()).isPresent());
            taetigkeitsfeldRepository.deleteById(testTaetigkeitsfeld.getBezeichnung());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testDeleteTaetigkeitsfeld(){
        TaetigkeitsfeldDTOImpl taetigkeitsfeld = new TaetigkeitsfeldDTOImpl();
        taetigkeitsfeld.setBezeichnung("Test");
        try{
            Taetigkeitsfeld testTaetigkeitsfeld = taetigkeitsfeldDAO.addTaetigkeitsfeld(taetigkeitsfeld);
            taetigkeitsfeldDAO.deleteTaetigkeitsfeld(testTaetigkeitsfeld);
            assertFalse(taetigkeitsfeldRepository.findById(testTaetigkeitsfeld.getBezeichnung()).isPresent());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testAddAndDeleteStudent(){
        TaetigkeitsfeldDTOImpl taetigkeitsfeldDTO = new TaetigkeitsfeldDTOImpl();
        taetigkeitsfeldDTO.setBezeichnung("Test");
        Student student = testStudentFactory.createStudent();
        try{
            Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldDAO.addTaetigkeitsfeld(taetigkeitsfeldDTO);
            taetigkeitsfeldDAO.addStudentToTaetigkeitsfeld(student, taetigkeitsfeld);
            assertTrue(taetigkeitsfeld.getStudenten().contains(student));
            taetigkeitsfeldDAO.removeStudentFromTaetigkeitsfeld(student, taetigkeitsfeld);
            assertFalse(taetigkeitsfeld.getStudenten().contains(student));
            taetigkeitsfeldDAO.deleteTaetigkeitsfeld(taetigkeitsfeld);
            assertFalse(taetigkeitsfeldRepository.findById(taetigkeitsfeld.getBezeichnung()).isPresent());
        }
        catch (PersistenceException e){
            System.out.println("Fehler");
        }
    }
}
