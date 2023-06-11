package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class KenntnisseService {
    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    private final Logger logger = LoggerFactory.getLogger(KenntnisseService.class);

    /**
     * Get a Kenntnis from the database
     * @param kenntnisDTO The KenntnisDTO
     * @return Kenntnis
     */
    public Kenntnis getKenntnis(KenntnisDTO kenntnisDTO){
        logger.info("Getting Kenntnis from Database with name: " + kenntnisDTO.getName());
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        return awaitKenntnis.orElse(kenntnisseRepository.save(Kenntnis.builder()
                .bezeichnung(kenntnisDTO.getName())
                .build()));
    }

    /**
     * Add a Kenntnis to a student
     * @param kenntnisDTO The KenntnisDTO
     * @param student The student
     */
    public void addStudentToKenntnis(KenntnisDTO kenntnisDTO, Student student) {
        logger.info("Adding Student to Kenntnis with name: " + kenntnisDTO.getName());
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        Kenntnis kenntnis;
        kenntnis = awaitKenntnis.orElseGet(() -> Kenntnis.builder()
                .bezeichnung(kenntnisDTO.getName())
                .build());
        kenntnis = kenntnis.addStudent(student);
        logger.info("Saving Kenntnis with name: " + kenntnisDTO.getName());
        kenntnisseRepository.save(kenntnis);
    }

    /**
     * Remove a student from a Kenntnis and save it
     *
     * @param kenntnisDTO The KenntnisDTO
     * @param student     The student
     * @throws PersistenceException If the Kenntnis is not found
     */
    public void removeStudentFromKenntnis(KenntnisDTO kenntnisDTO, Student student) throws PersistenceException {
        logger.info("Removing Student from Kenntnis with name: " + kenntnisDTO.getName());
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        if (awaitKenntnis.isPresent()){
            Kenntnis kenntnis = awaitKenntnis.get();
            kenntnis = kenntnis.removeStudent(student);
            logger.info("Saving Kenntnis with name: " + kenntnisDTO.getName());
            kenntnisseRepository.save(kenntnis);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.KENNTNIS_NOT_FOUND, "Kenntnis not found");
        }
    }
}
