package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class KenntnisseDAO {

    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    /**
     * Adds a student to a kenntnis
     * @param kenntnis
     * @return the kenntnis
     * @throws PersistenceException if the kenntnis could not be fetched or created
     */
    public Kenntnis addKenntnis(KenntnisDTO kenntnis) throws PersistenceException {
        if (kenntnisseRepository.existsById(kenntnis.getBezeichnung())) {
            Optional<Kenntnis> fetchedKenntnis = kenntnisseRepository.findById(kenntnis.getBezeichnung());
            if (fetchedKenntnis.isPresent()) {
                return fetchedKenntnis.get();
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileFetchingKenntnis, "Error while fetching kenntnis");
            }
        }
        else {
            Kenntnis newKenntnis = new Kenntnis();
            newKenntnis.setBezeichnung(kenntnis.getBezeichnung());
            kenntnisseRepository.save(newKenntnis);
            return newKenntnis;
        }
    }

    /**
     * Deletes a kenntnis from the database if it exists.
     * @param kenntnis the kenntnis to delete
     * @throws PersistenceException if the kenntnis was not deleted
     */
    public void deleteKenntnis(Kenntnis kenntnis) throws PersistenceException {
        if (kenntnisseRepository.existsById(kenntnis.getBezeichnung())) {
            kenntnisseRepository.delete(kenntnis);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileDeletingKenntnis, "Error while deleting kenntnis");
        }
    }


    /**
     * Removes a student from a kenntnis
     *
     * @param kenntnis
     * @param student
     * @throws PersistenceException if the student was not added to the kenntnis
     */
    public void removeStudentFromKenntnis(Kenntnis kenntnis, Student student) throws PersistenceException {
        if (kenntnis.getStudenten().contains(student)) {
            kenntnis.getStudenten().remove(student);
            kenntnisseRepository.save(kenntnis);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileRemovingStudentFromKenntnis, "Error while removing student from kenntnis");
        }
    }

    /**
     * Adds a student to a kenntnis
     *
     * @param kenntnis the kenntnis to add the student to
     * @param student the student to add to the kenntnis
     * @throws PersistenceException if the student was already added to the kenntnis
     */
    public void addStudentToKenntnis(Kenntnis kenntnis, Student student) throws PersistenceException {
        if (kenntnis.getStudenten() == null) {
            List<Student> studenten = new ArrayList<>();
            studenten.add(student);
            kenntnis.setStudenten(studenten);
            kenntnisseRepository.save(kenntnis);
        }
        else if (!kenntnis.getStudenten().contains(student)) {
            kenntnis.getStudenten().add(student);
            kenntnisseRepository.save(kenntnis);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileAddingStudentToKenntnis, "Error while adding student to kenntnis");
        }
    }
}
