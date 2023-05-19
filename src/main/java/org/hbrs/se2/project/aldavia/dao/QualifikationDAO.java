package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.QualifikationsDTO;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class QualifikationDAO {

    @Autowired
    private QualifikationRepository qualifikationRepository;

    /**
     * Adds a new Qualifikation to the database if it does not exist yet.
     *
     * @param qualifikation the Qualifikation to add
     * @return the added Qualifikation
     * @throws PersistenceException if the Qualifikation could not be fetched or created
     */
    public Qualifikation addQualifikation(QualifikationsDTO qualifikation) throws PersistenceException {
        try {
            Qualifikation newQualifikation = new Qualifikation();
            newQualifikation.setBezeichnung(qualifikation.getBezeichnung());
            newQualifikation.setBereich(qualifikation.getBereich());
            newQualifikation.setBeschreibung(qualifikation.getBeschreibung());
            newQualifikation.setBeschaeftigungsart(qualifikation.getBeschaeftigungsart());
            Optional<Qualifikation> fetchedQualifikation = qualifikationRepository.findQualifikationByBereichAndBeschaeftigungsartAndBeschreibungAndBezeichnung(qualifikation.getBereich(),
                    qualifikation.getBeschaeftigungsart(),
                    qualifikation.getBeschreibung(),
                    qualifikation.getBezeichnung());
            if (fetchedQualifikation.isPresent()) {
                return fetchedQualifikation.get();
            } else {
                qualifikationRepository.save(newQualifikation);
                return newQualifikation;
            }
        }
        catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileAddingQualifikation, "Error while adding qualifikation");
        }

    }

    /**
     * Deletes a Qualifikation from the database if it exists.
     *
     * @param qualifikation the Qualifikation to delete
     * @throws PersistenceException if the Qualifikation could not be deleted
     */
    public void deleteQualifikation(Qualifikation qualifikation) throws PersistenceException {
        if (qualifikationRepository.existsById(qualifikation.getId())) {
            qualifikationRepository.delete(qualifikation);
        } else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileDeletingQualifikation, "Error while deleting qualifikation");
        }
    }

    /**
     * Adds a student to a qualifikation
     * @param student       the student to add
     * @param qualifikation the qualifikation to add the student to
     * @throws PersistenceException if the student was not added to the qualifikation
     */
    public void addStudentToQualifikation(Student student, Qualifikation qualifikation) throws PersistenceException {
        if (qualifikation.getStudenten() == null) {
            List<Student> studenten = new ArrayList<>();
            studenten.add(student);
            qualifikation.setStudenten(studenten);
            qualifikationRepository.save(qualifikation);
        } else {
            if (!qualifikation.getStudenten().contains(student)) {
                qualifikation.getStudenten().add(student);
                qualifikationRepository.save(qualifikation);
            } else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileAddingStudentToQualifikation, "Error while adding student to qualifikation");
            }
        }
    }

    /**
     * Removes a student from a qualifikation
     * @param student the student to remove
     * @param qualifikation the qualifikation to remove the student from
     * @throws PersistenceException if the student was not added to the qualifikation
     */
    public void removeStudentFromQualifikation(Student student, Qualifikation qualifikation) throws PersistenceException {
        List<Student> studenten = qualifikation.getStudenten();
        if(studenten.contains(student)) {
            studenten.remove(student);
            qualifikation.setStudenten(studenten);
            qualifikationRepository.save(qualifikation);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileRemovingStudentFromQualifikation, "Error while removing student from qualifikation");
        }
    }
}