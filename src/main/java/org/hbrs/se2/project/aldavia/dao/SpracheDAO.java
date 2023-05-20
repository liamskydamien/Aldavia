package org.hbrs.se2.project.aldavia.dao;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SpracheDAO {

    @Autowired
    private SprachenRepository repository;

    /**
     * Create a new sprache
     * @param sprache The sprache
     * @return Sprache
     * @throws PersistenceException with type ErrorWhileCreatingSprache if an error occurs while creating the sprache
     */
    public Sprache createSprache(SpracheDTO sprache) throws PersistenceException {
        try {
            Optional<Sprache> awaitSprache = repository.findByNameAndLevel(sprache.getBezeichnung(), sprache.getLevel());
            if (awaitSprache.isEmpty()) {
                Sprache newSprache = new Sprache();
                newSprache.setName(sprache.getBezeichnung());
                newSprache.setLevel(sprache.getLevel());
                repository.save(newSprache);
                return newSprache;
            }
            else {
                return awaitSprache.get();
            }
        }
        catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileCreatingSprache, "Error while creating sprache");
        }
    }

    /**
     * Update a sprache
     * @param sprache The sprache
     * @return Sprache
     * @throws PersistenceException with type ErrorWhileUpdatingSprache if an error occurs while updating the sprache
     */
    public Sprache updateSprache(SpracheDTO sprache, int sprachenId) throws PersistenceException {
        try {
            Optional<Sprache> awaitSprache = repository.findById(sprachenId);
            if (awaitSprache.isPresent()) {
                Sprache spracheFromDB = awaitSprache.get();
                spracheFromDB.setName(sprache.getBezeichnung());
                spracheFromDB.setLevel(sprache.getLevel());
                repository.save(spracheFromDB);
                return spracheFromDB;
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
            }
        }
        catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileUpdatingSprache, "Error while updating sprache");
        }
    }

    /**
     * Add a student to a sprache
     *
     * @param student    The student
     * @param sprachenId The sprache
     * @throws PersistenceException with type ErrorWhileUpdatingSprache if an error occurs while updating the sprache
     */
    public void addStudentToSprache(Student student, int sprachenId) throws PersistenceException {
        try {
            Optional<Sprache> awaitSprache = repository.findById(sprachenId);
            if (awaitSprache.isPresent()) {
                Sprache spracheFromDB = awaitSprache.get();
                if (spracheFromDB.getStudenten() == null){
                    List<Student> studenten = new ArrayList<>();
                    studenten.add(student);
                    spracheFromDB.setStudenten(studenten);
                    repository.save(spracheFromDB);
                }
                else if(spracheFromDB.getStudenten().contains(student)) {
                    throw new PersistenceException(PersistenceException.PersistenceExceptionType.StudentAlreadyInSprache, "Student already in sprache");
                }
                spracheFromDB.getStudenten().add(student);
                repository.save(spracheFromDB);
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
            }
        }
        catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileUpdatingSprache, "Error while updating sprache");
        }
    }

    /**
     * Remove a student from a sprache
     * @param student The student
     * @param sprachenId The sprache
     * @return boolean
     * @throws PersistenceException with type ErrorWhileUpdatingSprache if an error occurs while updating the sprache
     */
    public void removeStudentFromSprache(Student student, int sprachenId) throws PersistenceException {
        try {
            Optional<Sprache> awaitSprache = repository.findById(sprachenId);
            if (awaitSprache.isPresent()) {
                Sprache spracheFromDB = awaitSprache.get();
                spracheFromDB.getStudenten().remove(student);
                repository.save(spracheFromDB);
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
            }
        }
        catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileUpdatingSprache, "Error while updating sprache");
        }
    }

    /**
     * Delete a sprache
     * @param sprache The sprache
     * @return boolean
     * @throws PersistenceException with type ErrorWhileDeletingSprache if an error occurs while deleting the sprache
     */
    public boolean deleteSprache(Sprache sprache) throws PersistenceException {
        try {
            Optional<Sprache> awaitSprache = repository.findByNameAndLevel(sprache.getName(), sprache.getLevel());
            if (awaitSprache.isPresent()) {
                Sprache spracheFromDB = awaitSprache.get();
                repository.deleteById(spracheFromDB.getSpracheId());
                return true;
            } else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
            }
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.ErrorWhileDeletingSprache, "Error while deleting sprache");
        }
    }

    /**
     * Get a sprache
     * @param sprache The sprache
     * @return Sprache
     * @throws PersistenceException with type ErrorWhileGettingSprache if an error occurs while getting the sprache
     */
    public Sprache getSprache(SpracheDTO sprache) throws PersistenceException {
        try {
            Optional<Sprache> awaitSprache = repository.findByNameAndLevel(sprache.getBezeichnung(), sprache.getLevel());
            if (awaitSprache.isPresent()) {
                return awaitSprache.get();
            } else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
            }
        } catch (Exception e) {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Error while getting sprache");
        }
    }
}
