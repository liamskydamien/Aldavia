package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.SpracheDTO;
import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.SprachenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
@Transactional
public class SprachenService {
    @Autowired
    private SprachenRepository sprachenRepository;

    private final Logger logger = LoggerFactory.getLogger(SprachenService.class);

    public Sprache getSprache(SpracheDTO spracheDTO){
        Optional<Sprache> awaitSprache = sprachenRepository.findByBezeichnungAndLevel(spracheDTO.getName(), spracheDTO.getLevel());
        return awaitSprache.orElse(sprachenRepository.save(Sprache.builder()
                .bezeichnung(spracheDTO.getName())
                .level(spracheDTO.getLevel())
                .build()));
    }

    /**
     * Create a new Sprache or return an existing one
     * @param spracheDTO The SpracheDTO
     * @return Sprache
     */
    public Sprache addStudentToSprache (SpracheDTO spracheDTO, Student student) {
        logger.info("Adding student " + student.getUser().getUserid() + " to " + spracheDTO.getName());
        Optional<Sprache> awaitSprache = sprachenRepository.findById(spracheDTO.getId());
        Sprache sprache;
        sprache = awaitSprache.orElseGet(() -> Sprache.builder()
                .bezeichnung(spracheDTO.getName())
                .level(spracheDTO.getLevel())
                .build());
        sprache = sprache.addStudent(student);
        return sprachenRepository.save(sprache);
    }

    /**
     * Remove a student from a Sprache and save it
     * @param spracheDTO The SpracheDTO
     * @param student The student
     * @throws PersistenceException If the Sprache is not found
     */
    public Sprache removeStudentFromSprache(SpracheDTO spracheDTO, Student student) throws PersistenceException {
        logger.info("Removing student" + student.getUser().getUserid() + " from " + spracheDTO.getName());
        Optional<Sprache> awaitSprache = sprachenRepository.findById(spracheDTO.getId());
        if (awaitSprache.isPresent()){
            Sprache sprache = awaitSprache.get();
            sprache = sprache.removeStudent(student);
            return sprachenRepository.save(sprache);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.SPRACHE_NOT_FOUND, "Sprache not found");
        }
    }
}
