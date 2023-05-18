package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaetigkeitsfeldDAO {

    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    /**
     * Fügt ein Taetigkeitsfeld hinzu, falls es noch nicht existiert.
     * @param taetigkeitsfeld Das Taetigkeitsfeld, das hinzugefügt werden soll.
     * @return Das hinzugefügte Taetigkeitsfeld.
     * @throws PersistenceException Falls das Taetigkeitsfeld nicht hinzugefügt werden konnte.
     */
    public Taetigkeitsfeld addTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        if(taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung())) {
            Optional<Taetigkeitsfeld> fetchedTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeld.getBezeichnung());
            if(fetchedTaetigkeitsfeld.isPresent()) {
                return fetchedTaetigkeitsfeld.get();
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileFetchingTaetigkeitsfeld, "Error while fetching taetigkeitsfeld");
            }
        }
        else {
                return taetigkeitsfeldRepository.save(taetigkeitsfeld);
            }
    }

    /**
     * Entfernt ein Taetigkeitsfeld.
     * @param taetigkeitsfeld Das Taetigkeitsfeld, das entfernt werden soll.
     * @return true, falls das Taetigkeitsfeld erfolgreich entfernt wurde.
     * @throws PersistenceException Falls das Taetigkeitsfeld nicht entfernt werden konnte.
     */
    public boolean removeTaetigkeitsfeld(Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        if (taetigkeitsfeldRepository.existsById(taetigkeitsfeld.getBezeichnung())) {
            taetigkeitsfeldRepository.delete(taetigkeitsfeld);
            return true;
        } else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileRemovingTaetigkeitsfeld, "Error while removing taetigkeitsfeld");
        }
    }

    /**
     * Fügt einen Studenten zu einem Taetigkeitsfeld hinzu.
     * @param student Der Student, der hinzugefügt werden soll.
     * @param taetigkeitsfeld Das Taetigkeitsfeld, zu dem der Student hinzugefügt werden soll.
     * @return true, falls der Student erfolgreich hinzugefügt wurde.
     * @throws PersistenceException Falls der Student nicht hinzugefügt werden konnte.
     */
    public boolean addStudentToTaetigkeitsfeld(Student student, Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        if (taetigkeitsfeld.getStudenten().contains(student)) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileAddingStudentToTaetigkeitsfeld, "Error while adding student to taetigkeitsfeld");
        }
        else {
            taetigkeitsfeld.getStudenten().add(student);
            taetigkeitsfeldRepository.save(taetigkeitsfeld);
            return true;
        }
    }

    /**
     * Entfernt einen Studenten von einem Taetigkeitsfeld.
     * @param student Der Student, der entfernt werden soll.
     * @param taetigkeitsfeld Das Taetigkeitsfeld, von dem der Student entfernt werden soll.
     * @return true, falls der Student erfolgreich entfernt wurde.
     * @throws PersistenceException Falls der Student nicht entfernt werden konnte.
     */
    public boolean removeStudentFromTaetigkeitsfeld(Student student, Taetigkeitsfeld taetigkeitsfeld) throws PersistenceException {
        if (taetigkeitsfeld.getStudenten().contains(student)) {
            taetigkeitsfeld.getStudenten().remove(student);
            taetigkeitsfeldRepository.save(taetigkeitsfeld);
            return true;
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileRemovingStudentFromTaetigkeitsfeld, "Error while removing student from taetigkeitsfeld");
        }
    }


}
