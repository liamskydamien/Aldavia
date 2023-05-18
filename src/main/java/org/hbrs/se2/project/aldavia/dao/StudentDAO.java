package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StudentDAO {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SpracheDAO spracheDAO;

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
}
