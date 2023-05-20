package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.checkerframework.checker.units.qual.A;
import org.hbrs.se2.project.aldavia.dao.*;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.KenntnisDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.QualifikationsDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.SpracheDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.TaetigkeitsfeldDTOImpl;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestStudentDAO {
    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private KenntnisseDAO kenntnisseDAO;

    @Autowired
    private SpracheDAO spracheDAO;

    @Autowired
    private QualifikationDAO qualifikationDAO;

    @Autowired
    private TaetigkeitsfeldDAO taetigkeitsfeldDAO;

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Test
    public void testAddAndDeleteKenntnis() {
        try {
            Student student = testStudentFactory.createStudent();
            KenntnisDTOImpl kenntnisDTO = new KenntnisDTOImpl();
            kenntnisDTO.setBezeichnung("TestKenntnis");
            Kenntnis kenntnis = kenntnisseDAO.addKenntnis(kenntnisDTO);
            studentDAO.addKenntnis(student, kenntnis);
            assertEquals(student.getKenntnisse().get(0), kenntnis);
            assertEquals(kenntnis.getStudenten().get(0), student);
            studentDAO.removeKenntnis(student, kenntnis);
            assertEquals(student.getKenntnisse().size(), 0);
            assertEquals(kenntnis.getStudenten().size(), 0);
        } catch (Exception e) {
            System.out.println("Fehler");
        }
    }

    @Test
    public void testAddAndDeleteSprache(){
        try {
           Student student = testStudentFactory.createStudent();
            SpracheDTOImpl spracheDTO = new SpracheDTOImpl();
            spracheDTO.setBezeichnung("TestSprache");
            spracheDTO.setLevel("Muttersprache");
           Sprache sprache = spracheDAO.createSprache(spracheDTO);
           studentDAO.addSprache(student, sprache);
           assertEquals(student.getSprachen().get(0), sprache);
           assertEquals(sprache.getStudenten().get(0), student);
           studentDAO.removeSprache(student, sprache);
           assertEquals(student.getSprachen().size(), 0);
           assertEquals(sprache.getStudenten().size(), 0);
           testStudentFactory.deleteStudent();
        }
        catch (Exception e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testAddAndDeleteQualifikation(){
        try {
            Student student = testStudentFactory.createStudent();
            QualifikationsDTOImpl qualifikationsDTO = new QualifikationsDTOImpl();
            qualifikationsDTO.setBezeichnung("TestQualifikation");
            qualifikationsDTO.setBeschreibung("TestBeschreibung");
            qualifikationsDTO.setBereich("TestBereich");
            qualifikationsDTO.setBeschaeftigungsart("TestBeschaeftigungsart");
            Qualifikation qualifikation = qualifikationDAO.addQualifikation(qualifikationsDTO);
            studentDAO.addQualifikation(student, qualifikation);
            assertEquals(student.getQualifikationen().get(0), qualifikation);
            assertEquals(qualifikation.getStudenten().get(0), student);
            studentDAO.removeQualifikation(student, qualifikation);
            assertEquals(student.getQualifikationen().size(), 0);
            assertEquals(qualifikation.getStudenten().size(), 0);
            testStudentFactory.deleteStudent();
        }
        catch (Exception e){
            System.out.println("Fehler");
        }
    }

    @Test
    public void testAddAndDeleteTaetigkeitsfeld(){
        try{
            Student student = testStudentFactory.createStudent();
            TaetigkeitsfeldDTOImpl taetigkeitsfeldDTO = new TaetigkeitsfeldDTOImpl();
            taetigkeitsfeldDTO.setBezeichnung("TestTaetigkeitsfeld");
            Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldDAO.addTaetigkeitsfeld(taetigkeitsfeldDTO);
            studentDAO.addTaetigkeitsfeld(student, taetigkeitsfeld);
            assertEquals(student.getTaetigkeitsfelder().get(0), taetigkeitsfeld);
            assertEquals(taetigkeitsfeld.getStudenten().get(0), student);
            studentDAO.removeTaetigkeitsfeld(student, taetigkeitsfeld);
            assertEquals(student.getTaetigkeitsfelder().size(), 0);
            assertEquals(taetigkeitsfeld.getStudenten().size(), 0);
            testStudentFactory.deleteStudent();
        }
        catch (Exception e){
            System.out.println("Fehler");
        }
    }
}
