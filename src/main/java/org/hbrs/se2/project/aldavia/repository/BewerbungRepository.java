package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BewerbungRepository extends JpaRepository<Bewerbung, Integer> {
    List<Bewerbung> findByStudent(Student student);

    List<Bewerbung> findByStellenanzeige(Stellenanzeige stellenanzeige);

    Optional<Bewerbung> findByStudentAndStellenanzeige(Student student, Stellenanzeige stellenanzeige);
}

