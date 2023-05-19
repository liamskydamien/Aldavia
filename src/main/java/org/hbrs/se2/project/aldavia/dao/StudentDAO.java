package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.dtos.impl.StudentProfileDTOImpl;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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

    /**
     * Load student by username from database
     * @param username The username
     * @return Student
     * @throws PersistenceException with type ErrorWhileGettingStudent if an error occurs while getting the student
     */
    public Student getStudent(String username) throws PersistenceException {
        Optional<Student> student = studentRepository.findByUserID(username);
        if(student.isPresent()){
            return student.get();
        }
        throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileGettingStudent, "Student not found");
    }

    public void createAndUpdateStudent(StudentProfileDTO studentProfileDTO, Student student) throws PersistenceException {
        student.setVorname(studentProfileDTO.getVorname());
        student.setNachname(studentProfileDTO.getNachname());
        student.setGeburtsdatum(studentProfileDTO.getGeburtsdatum());
        student.setMatrikelNummer(studentProfileDTO.getMatrikelNummer());
        student.setStudiengang(studentProfileDTO.getStudiengang());
        student.setStudienbeginn(studentProfileDTO.getStudienbeginn());
        student = updateKenntnisse(studentProfileDTO.getKenntnisse(), student);
        student = updateQualifikationen(studentProfileDTO.getQualifikationen(), student);
        student = updateSprachen(studentProfileDTO.getSprachen(), student);
        student = updateTaetigkeitsfelder(studentProfileDTO.getTaetigkeitsfelder(), student);
        studentRepository.save(student);
    }

    /**
     * Add a sprache to a student
     * @param student The student
     * @param sprache  The sprache
     * @return Student
     * @throws PersistenceException with type ErrorWhileAddingSprache if an error occurs while adding the sprache
     */
    public Student addSprache(Student student, Sprache sprache) throws PersistenceException {
        List<Sprache> sprachen = student.getSprachen();
        sprachen.add(sprache);
        student.setSprachen(sprachen);
        studentRepository.save(student);
        spracheDAO.addStudentToSprache(student, sprache.getSpracheId());
        return student;
    }

    /**
     * Remove a sprache from a student
     * @param student The student
     * @param sprache The sprache
     * @return Student
     * @throws PersistenceException with type ErrorWhileRemovingSprache if an error occurs while removing the sprache
     */
    public Student removeSprache(Student student, Sprache sprache) throws PersistenceException {
        List<Sprache> sprachen = student.getSprachen();
        sprachen.remove(sprache);
        student.setSprachen(sprachen);
        studentRepository.save(student);
        spracheDAO.removeStudentFromSprache(student, sprache.getSpracheId());
        return student;
    }

    /**
     * Add a kenntnis to a student
     * @param student The student
     * @param kenntnis The kenntnis
     * @return Student
     * @throws PersistenceException with type ErrorWhileAddingKenntnis if an error occurs while adding the kenntnis
     */
    public Student addKenntnis(Student student, Kenntnis kenntnis) throws PersistenceException {
        List<Kenntnis> kenntnisse = student.getKenntnisse();
        kenntnisse.add(kenntnis);
        student.setKenntnisse(kenntnisse);
        studentRepository.save(student);
        kenntnisseDAO.addStudentToKenntnis(kenntnis, student);
        return student;
    }

    /**
     * Remove a kenntnis from a student
     * @param student The student
     * @param kenntnis The kenntnis
     * @return Student
     * @throws PersistenceException
     */
    public Student removeKenntnis(Student student, Kenntnis kenntnis) throws PersistenceException {
        List<Kenntnis> kenntnisse = student.getKenntnisse();
        kenntnisse.remove(kenntnis);
        student.setKenntnisse(kenntnisse);
        studentRepository.save(student);
        kenntnisseDAO.removeStudentFromKenntnis(kenntnis, student);
        return student;
    }

    /**
     * Add a qualifikation to a student
     * @param student The student
     * @param qualifikation The qualifikation
     * @return Student
     * @throws PersistenceException with type ErrorWhileAddingQualifikation if an error occurs while adding the qualifikation
     */
    public Student addQualifikation(Student student, Qualifikation qualifikation) throws PersistenceException {
        List<Qualifikation> qualifikationen = student.getQualifikationen();
        qualifikationen.add(qualifikation);
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);
        qualifikationDAO.addStudentToQualifikation(student, qualifikation);
        return student;
    }

    /**
     * Remove a qualifikation from a student
     * @param student The student
     * @param qualifikation The qualifikation
     * @return Student
     * @throws PersistenceException with type ErrorWhileRemovingQualifikation if an error occurs while removing the qualifikation
     */
    public Student removeQualifikation(Student student, Qualifikation qualifikation) throws PersistenceException {
        List<Qualifikation> qualifikationen = student.getQualifikationen();
        qualifikationen.remove(qualifikation);
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);
        qualifikationDAO.removeStudentFromQualifikation(student, qualifikation);
        return student;
    }

    /**
     * Add a taetigkeitsfeld to a student
     * @param student The student
     * @param taetigkeitsfeld The taetigkeitsfeld
     * @return Student
     * @throws PersistenceException with type ErrorWhileAddingTaetigkeitsfeld if an error occurs while adding the taetigkeitsfeld
     */
    public Student addTaetigkeitsfeld(Student student, Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder();
        taetigkeitsfelder.add(taetigkeitsfeld);
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);
        taetigkeitsfeldDAO.addStudentToTaetigkeitsfeld(student, taetigkeitsfeld);
        return student;
    }

    /**
     * Remove a taetigkeitsfeld from a student
     * @param student The student
     * @param taetigkeitsfeld The taetigkeitsfeld
     * @return Student
     * @throws PersistenceException with type ErrorWhileRemovingTaetigkeitsfeld if an error occurs while removing the taetigkeitsfeld
     */
    public Student removeTaetigkeitsfeld(Student student, Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        List<Taetigkeitsfeld> taetigkeitsfelder = student.getTaetigkeitsfelder();
        taetigkeitsfelder.remove(taetigkeitsfeld);
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);
        taetigkeitsfeldDAO.removeStudentFromTaetigkeitsfeld(student, taetigkeitsfeld);
        return student;
    }

    public Student updateSprachen(List<SpracheDTO> spracheDTOS, Student student) throws PersistenceException {
        List<Sprache> sprachen = new ArrayList<>();
        for (SpracheDTO spracheDTO : spracheDTOS) {
            Sprache sprache = spracheDAO.createSprache(spracheDTO);
            sprachen.add(sprache);
            spracheDAO.addStudentToSprache(student, sprache.getSpracheId());
        }
        student.setSprachen(sprachen);
        studentRepository.save(student);
        return student;
    }

    public Student updateKenntnisse(List<KenntnisDTO> kenntnisDTOS, Student student) throws PersistenceException {
        List<Kenntnis> kenntnisse = new ArrayList<>();
        for (KenntnisDTO kenntnisDTO : kenntnisDTOS) {
            Kenntnis kenntnis = kenntnisseDAO.addKenntnis(kenntnisDTO);
            kenntnisse.add(kenntnis);
            kenntnisseDAO.addStudentToKenntnis(kenntnis, student);
        }
        student.setKenntnisse(kenntnisse);
        studentRepository.save(student);
        return student;
    }

    public Student updateQualifikationen(List<QualifikationsDTO> qualifikationDTOS, Student student) throws PersistenceException {
        List<Qualifikation> qualifikationen = new ArrayList<>();
        for (QualifikationsDTO qualifikationDTO : qualifikationDTOS) {
            Qualifikation qualifikation = qualifikationDAO.addQualifikation(qualifikationDTO);
            qualifikationen.add(qualifikation);
            qualifikationDAO.addStudentToQualifikation(student, qualifikation);
        }
        student.setQualifikationen(qualifikationen);
        studentRepository.save(student);
        return student;
    }

    public Student updateTaetigkeitsfelder(List<TaetigkeitsfeldDTO> taetigkeitsfeldDTOS, Student student) throws PersistenceException {
        List<Taetigkeitsfeld> taetigkeitsfelder = new ArrayList<>();
        for (TaetigkeitsfeldDTO taetigkeitsfeldDTO : taetigkeitsfeldDTOS) {
            Taetigkeitsfeld taetigkeitsfeld = taetigkeitsfeldDAO.addTaetigkeitsfeld(taetigkeitsfeldDTO);
            taetigkeitsfelder.add(taetigkeitsfeld);
            taetigkeitsfeldDAO.addStudentToTaetigkeitsfeld(student, taetigkeitsfeld);
        }
        student.setTaetigkeitsfelder(taetigkeitsfelder);
        studentRepository.save(student);
        return student;
    }
}
