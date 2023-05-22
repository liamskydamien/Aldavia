package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SprachenControl {
    @Autowired
    private SprachenRepository sprachenRepository;

    /**
     * Create a new Sprache or return an existing one
     * @param spracheDTO The SpracheDTO
     * @return Sprache
     */
    public Sprache addStudentToSprache (SpracheDTO spracheDTO, Student student) throws PersistenceException {
        if(sprachenRepository.existsByBezeichnungAndLevel(spracheDTO.getName(), spracheDTO.getLevel())){
            Optional<Sprache> awaitSprache = sprachenRepository.findByBezeichnungAndLevel(spracheDTO.getName(), spracheDTO.getLevel());
            if (awaitSprache.isPresent()){
                Sprache sprache = awaitSprache.get();
                sprache.addStudent(student);
                return sprachenRepository.save(sprache);
            }
            else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
            }
        }
        else {
            Sprache sprache = Sprache.builder()
                    .bezeichnung(spracheDTO.getName())
                    .level(spracheDTO.getLevel())
                    .build();
            return sprachenRepository.save(sprache);
        }
    }

    /**
     * Remove a student from a Sprache and save it
     * @param spracheDTO The SpracheDTO
     * @param student The student
     * @throws PersistenceException If the Sprache is not found
     */
    public void removeStudentFromSprache(SpracheDTO spracheDTO, Student student) throws PersistenceException {
        Optional<Sprache> awaitSprache = sprachenRepository.findById(spracheDTO.getId());
        if (awaitSprache.isPresent()){
            Sprache sprache = awaitSprache.get();
            sprache.removeStudent(student);
            if(sprache.getStudents().isEmpty()){
                sprachenRepository.delete(sprache);
            }
            else {
                sprachenRepository.save(sprache);
            }
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.SpracheNotFound, "Sprache not found");
        }
    }


}
