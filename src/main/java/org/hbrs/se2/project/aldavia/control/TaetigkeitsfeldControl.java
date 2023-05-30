package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class TaetigkeitsfeldControl {
    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    public Taetigkeitsfeld getTaetigkeitsfeld(TaetigkeitsfeldDTO taetigkeitsfeldDTO){
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        return awaitTaetigkeitsfeld.orElse(taetigkeitsfeldRepository.save(Taetigkeitsfeld.builder()
                .bezeichnung(taetigkeitsfeldDTO.getName())
                .build()));
    }

    /**
     * Add a Taetigkeitsfeld to a student
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @return Taetigkeitsfeld
     */
    public Taetigkeitsfeld addStudentToTaetigkeitsfeld (TaetigkeitsfeldDTO taetigkeitsfeldDTO, Student student) {
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        Taetigkeitsfeld taetigkeitsfeld;
        if (awaitTaetigkeitsfeld.isPresent()){
            taetigkeitsfeld = awaitTaetigkeitsfeld.get();
        }
        else {
            taetigkeitsfeld = new Taetigkeitsfeld();
            taetigkeitsfeld.setBezeichnung(taetigkeitsfeldDTO.getName());
        }
        taetigkeitsfeld = taetigkeitsfeld.addStudent(student);
        System.out.println(taetigkeitsfeld.getBezeichnung()+ " " + taetigkeitsfeld.getStudents().get(0).getNachname());
        return taetigkeitsfeldRepository.save(taetigkeitsfeld);
    }

    /**
     * Remove a student from a Taetigkeitsfeld and save it
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @param student The student
     */
    public Taetigkeitsfeld removeStudentFromTaetigkeitsfeld(TaetigkeitsfeldDTO taetigkeitsfeldDTO, Student student) throws PersistenceException {
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        if (awaitTaetigkeitsfeld.isPresent()){
            Taetigkeitsfeld taetigkeitsfeld = awaitTaetigkeitsfeld.get();
            taetigkeitsfeld = taetigkeitsfeld.removeStudent(student);
            return taetigkeitsfeldRepository.save(taetigkeitsfeld);
        }
        else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.TaetigkeitsfeldNotFound, "Taetigkeitsfeld not found");
        }
    }
}
