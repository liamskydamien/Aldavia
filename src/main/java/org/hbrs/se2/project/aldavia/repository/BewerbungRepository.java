package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface BewerbungRepository extends JpaRepository<Bewerbung, Integer> {
}
=======
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BewerbungRepository extends JpaRepository<Bewerbung, Integer> {
}
>>>>>>> 24f3e91607fa3c5cd4e1689b30f715d4f0b2d7ca
