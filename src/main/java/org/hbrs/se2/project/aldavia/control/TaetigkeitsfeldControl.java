package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.repository.TaetigkeitsfeldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaetigkeitsfeldControl {
    @Autowired
    private TaetigkeitsfeldRepository taetigkeitsfeldRepository;

    /**
     * Add a Taetigkeitsfeld to a student
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @return Taetigkeitsfeld
     */
    public Taetigkeitsfeld addTaetigkeitsfeld(TaetigkeitsfeldDTO taetigkeitsfeldDTO, Student student) {
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        if (awaitTaetigkeitsfeld.isPresent()){
            Taetigkeitsfeld taetigkeitsfeld = awaitTaetigkeitsfeld.get();
            taetigkeitsfeld.addStudent(student);
            return taetigkeitsfeldRepository.save(taetigkeitsfeld);
        }
        else {
            Taetigkeitsfeld taetigkeitsfeld = Taetigkeitsfeld.builder()
                    .bezeichnung(taetigkeitsfeldDTO.getName())
                    .build();
            return taetigkeitsfeldRepository.save(taetigkeitsfeld);
        }
    }

    /**
     * Remove a student from a Taetigkeitsfeld and save it
     * @param taetigkeitsfeldDTO The TaetigkeitsfeldDTO
     * @param student The student
     */
    public void removeTaetigkeitsfeld(TaetigkeitsfeldDTO taetigkeitsfeldDTO, Student student) {
        Optional<Taetigkeitsfeld> awaitTaetigkeitsfeld = taetigkeitsfeldRepository.findById(taetigkeitsfeldDTO.getName());
        if (awaitTaetigkeitsfeld.isPresent()){
            Taetigkeitsfeld taetigkeitsfeld = awaitTaetigkeitsfeld.get();
            taetigkeitsfeld.removeStudent(student);
            if(taetigkeitsfeld.getStudents().isEmpty()){
                taetigkeitsfeldRepository.delete(taetigkeitsfeld);
            }
            else {
                taetigkeitsfeldRepository.save(taetigkeitsfeld);
            }
            taetigkeitsfeldRepository.delete(taetigkeitsfeld);
        }
    }
}
