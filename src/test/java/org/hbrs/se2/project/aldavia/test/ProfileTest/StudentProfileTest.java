package org.hbrs.se2.project.aldavia.test.ProfileTest;

import org.hbrs.se2.project.aldavia.control.StudentProfileControl;


import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dao.*;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.test.TestStudentFactory;
import org.hbrs.se2.project.aldavia.util.DTOTransformator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentProfileTest {
    @Autowired
    private StudentProfileControl studentProfileControl;

    @Autowired
    private QualifikationDAO qualifikationDAO;

    @Autowired
    private SpracheDAO sprachenDAO;

    @Autowired
    private KenntnisseDAO kenntnisseDAO;

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private TaetigkeitsfeldDAO taetigkeitsfeldDAO;

    @Autowired
    private TestStudentFactory testStudentFactory;

    @Autowired
    private StudentRepository studentRepository;

    private List<Qualifikation> qualifikations;
    private List<Kenntnis> kenntnisse;
    private List<Sprache> sprachen;
    private List<Taetigkeitsfeld> taetigkeitsfelder;

    @BeforeEach
    public void setUp() throws PersistenceException {
        qualifikations = new ArrayList<>();
        kenntnisse = new ArrayList<>();
        sprachen = new ArrayList<>();
        taetigkeitsfelder = new ArrayList<>();

        // Qualifikationen
        QualifikationsDTOImpl qualifikationsDTO = new QualifikationsDTOImpl();
        qualifikationsDTO.setBezeichnung("Test");
        qualifikationsDTO.setBeschreibung("Test");
        qualifikationsDTO.setBereich("Test");
        qualifikationsDTO.setBeschaeftigungsart("2020-01-01");
        Qualifikation qualifikation = qualifikationDAO.addQualifikation(qualifikationsDTO);
        qualifikations.add(qualifikation);

        // Kenntnisse
        KenntnisDTOImpl kenntnisDTO = new KenntnisDTOImpl();
        kenntnisDTO.setBezeichnung("Test");
        Kenntnis kenntnis = kenntnisseDAO.addKenntnis(kenntnisDTO);
        kenntnisse.add(kenntnis);

        // Sprachen
        SpracheDTOImpl sprachenDTO = new SpracheDTOImpl();
        sprachenDTO.setBezeichnung("Test");
        sprachenDTO.setLevel("Test");
        Sprache sprache = sprachenDAO.createSprache(sprachenDTO);
        sprachen.add(sprache);

        // Taetigkeitsfelder
        TaetigkeitsfeldDTOImpl taetigkeitsfeldDTO = new TaetigkeitsfeldDTOImpl();
        taetigkeitsfeldDTO.setBezeichnung("Test");
        Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldDAO.addTaetigkeitsfeld(taetigkeitsfeldDTO);
        taetigkeitsfelder.add(taetigkeitsfeld);
    }

    @Test
    public void testGetStudentProfile() {
        try {
            Student student = testStudentFactory.createStudent();
            student.setQualifikationen(qualifikations);
            student.setTaetigkeitsfelder(taetigkeitsfelder);
            student.setKenntnisse(kenntnisse);
            student.setSprachen(sprachen);
            studentRepository.save(student);
            studentProfileControl.createAndUpdateStudentProfile(DTOTransformator.transformStudentProfileDTO(student), student.getUser().getUserid());
            StudentProfileDTO studentProfileDTO = studentProfileControl.getStudentProfile(student.getUser().getUserid());
            assertEquals(studentProfileDTO, DTOTransformator.transformStudentProfileDTO(student), "StudentProfileDTO is not equal to StudentProfile");
            studentDAO.removeKenntnis(student, kenntnisse.get(0));
            studentDAO.removeQualifikation(student, qualifikations.get(0));
            studentDAO.removeSprache(student, sprachen.get(0));
            studentDAO.removeTaetigkeitsfeld(student, taetigkeitsfelder.get(0));
            studentRepository.delete(student);

        } catch (Exception e) {
            System.out.println("Error in testGetStudentProfile");
        }
    }


}
