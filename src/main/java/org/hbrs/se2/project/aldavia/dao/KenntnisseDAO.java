package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Kenntnis addKenntnis(String kenntnis) throws PersistenceException {
        if (kenntnisseRepository.existsById(kenntnis)) {
            Optional<Kenntnis> fetchedKenntnis = kenntnisseRepository.findById(kenntnis);
            if (fetchedKenntnis.isPresent()) {
                return fetchedKenntnis.get();
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileFetchingKenntnis, "Error while fetching kenntnis");
            }
        }
        else {
            Kenntnis newKenntnis = new Kenntnis();
            newKenntnis.setBezeichnung(kenntnis);
            kenntnisseRepository.save(newKenntnis);
            return newKenntnis;
        }
    }

    /**
     * Removes a student from a kenntnis
     * @param kenntnis
     * @param student
     * @return true if the student was removed successfully
     * @throws PersistenceException if the student was not added to the kenntnis
     */
    public boolean removeStudentFromKenntnis(Kenntnis kenntnis, Student student) throws PersistenceException {
        if (kenntnis.getStudenten().contains(student)) {
            kenntnis.getStudenten().remove(student);
            kenntnisseRepository.save(kenntnis);
            return true;
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileRemovingStudentFromKenntnis, "Error while removing student from kenntnis");
        }
    }

    /**
     * Adds a student to a kenntnis
     * @param kenntnis
     * @param student
     * @return true if the student was added successfully
     * @throws PersistenceException if the student was already added to the kenntnis
     */
    public boolean addStudentToKenntnis(Kenntnis kenntnis, Student student) throws PersistenceException {
        if (!kenntnis.getStudenten().contains(student)) {
            kenntnis.getStudenten().add(student);
            kenntnisseRepository.save(kenntnis);
            return true;
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileAddingStudentToKenntnis, "Error while adding student to kenntnis");
        }
    }
}
