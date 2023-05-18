package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class StudentDAO {

    @Autowired
    private StudentRepository repository;

    public Student addSprache(Student student, Sprache sprache) {
        List<Sprache> sprachen = student.getSprachen();
        sprachen.add(sprache);
        return null;
    }
}
