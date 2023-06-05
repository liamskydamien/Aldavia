package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class KenntnisseService {
    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    /**
     * Get a Kenntnis from the database
     * @param kenntnisDTO The KenntnisDTO
     * @return Kenntnis
     */
    public Kenntnis getKenntnis(KenntnisDTO kenntnisDTO){
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        return awaitKenntnis.orElse(kenntnisseRepository.save(Kenntnis.builder()
                .bezeichnung(kenntnisDTO.getName())
                .build()));
    }

    /**
     * Add a Kenntnis to a student
     * @param kenntnisDTO The KenntnisDTO
     * @param student The student
     * @return Kenntnis
     */
    public void addStudentToKenntnis(KenntnisDTO kenntnisDTO, Student student) {
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        Kenntnis kenntnis;
        kenntnis = awaitKenntnis.orElseGet(() -> Kenntnis.builder()
                .bezeichnung(kenntnisDTO.getName())
                .build());
        kenntnis = kenntnis.addStudent(student);
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
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        if (awaitKenntnis.isPresent()){
            Kenntnis kenntnis = awaitKenntnis.get();
            kenntnis = kenntnis.removeStudent(student);
            kenntnisseRepository.save(kenntnis);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.KenntnisNotFound, "Kenntnis not found");
        }
    }
}
