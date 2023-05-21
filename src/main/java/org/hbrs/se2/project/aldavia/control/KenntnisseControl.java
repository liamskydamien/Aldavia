package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.KenntnisDTO;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.KenntnisseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KenntnisseControl {
    @Autowired
    private KenntnisseRepository kenntnisseRepository;

    /**
     * Add a Kenntnis to a student
     * @param kenntnisDTO The KenntnisDTO
     * @param student The student
     * @return Kenntnis
     */
    public Kenntnis addKenntnis(KenntnisDTO kenntnisDTO, Student student) {
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        if (awaitKenntnis.isPresent()){
            Kenntnis kenntnis = awaitKenntnis.get();
            kenntnis.addStudent(student);
            return kenntnisseRepository.save(kenntnis);
        }
        else {
            Kenntnis kenntnis = Kenntnis.builder()
                    .bezeichnung(kenntnisDTO.getName())
                    .build();
            kenntnis.addStudent(student);
            return kenntnisseRepository.save(kenntnis);
        }
    }

    /**
     * Remove a student from a Kenntnis and save it
     * @param kenntnisDTO The KenntnisDTO
     * @param student The student
     * @throws PersistenceException If the Kenntnis is not found
     */
    public void removeStudentFromKenntnis(KenntnisDTO kenntnisDTO, Student student) throws PersistenceException {
        Optional<Kenntnis> awaitKenntnis = kenntnisseRepository.findById(kenntnisDTO.getName());
        if (awaitKenntnis.isPresent()){
            Kenntnis kenntnis = awaitKenntnis.get();
            kenntnis.removeStudent(student);
            if(kenntnis.getStudents().isEmpty()){
                kenntnisseRepository.delete(kenntnis);
            }
            else {
                kenntnisseRepository.save(kenntnis);
            }
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.KenntnisNotFound, "Kenntnis not found");
        }
    }
}
