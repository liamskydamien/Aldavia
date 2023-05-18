package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDAO {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SpracheDAO spracheDAO;

    @Autowired
    private KenntnisseDAO kenntnisseDAO;

    @Autowired
    private QualifikationDAO qualifikationDAO;

    @Autowired
    private TaetigkeitsfeldDAO taetigkeitsfeldDAO;

    public Student addSprache(Student student, Sprache sprache) throws PersistenceException {
        List<Sprache> sprachen = student.getSprachen();
        sprachen.add(sprache);
        student.setSprachen(sprachen);
        studentRepository.save(student);
        spracheDAO.addStudentToSprache(student, sprache.getSpracheId());
        return student;
    }

    public Student removeSprache(Student student, Sprache sprache) throws PersistenceException {
        List<Sprache> sprachen = student.getSprachen();
        sprachen.remove(sprache);
        student.setSprachen(sprachen);
        studentRepository.save(student);
        spracheDAO.removeStudentFromSprache(student, sprache.getSpracheId());
        return student;
    }

    public Student addKenntnis(Student student, Kenntnis kenntnis) throws PersistenceException {
        List<Kenntnis> kenntnisse = student.getKenntnisse();
        kenntnisse.add(kenntnis);
        student.setKenntnisse(kenntnisse);
        studentRepository.save(student);
        kenntnisseDAO.addStudentToKenntnis(kenntnis, student);
        return student;
    }

    public Student removeKenntnis(Student student, Kenntnis kenntnis) throws PersistenceException {
        List<Kenntnis> kenntnisse = student.getKenntnisse();
        kenntnisse.remove(kenntnis);
        student.setKenntnisse(kenntnisse);
        studentRepository.save(student);
        kenntnisseDAO.removeStudentFromKenntnis(kenntnis, student);
        return student;
    }

    public Student addQualifikation(Student student, Qualifikation qualifikation) throws PersistenceException {
        List<Qualifikation> qualifikationen = student.getQualifikationen();
        qualifikationen.add(qualifikation);
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);
        qualifikationDAO.addStudentToQualifikation(student, qualifikation);
        return student;
    }

    public Student removeQualifikation(Student student, Qualifikation qualifikation) throws PersistenceException {
        List<Qualifikation> qualifikationen = student.getQualifikationen();
        qualifikationen.remove(qualifikation);
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);
        qualifikationDAO.removeStudentFromQualifikation(student, qualifikation);
        return student;
    }

    public Student addTaetigkeitsfeld(Student student, Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder();
        taetigkeitsfelder.add(taetigkeitsfeld);
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);
        taetigkeitsfeldDAO.addStudentToTaetigkeitsfeld(student, taetigkeitsfeld);
        return student;
    }

    public Student removeTaetigkeitsfeld(Student student, Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder();
        taetigkeitsfelder.remove(taetigkeitsfeld);
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);
        taetigkeitsfeldDAO.removeStudentFromTaetigkeitsfeld(student, taetigkeitsfeld);
        return student;
    }
}
