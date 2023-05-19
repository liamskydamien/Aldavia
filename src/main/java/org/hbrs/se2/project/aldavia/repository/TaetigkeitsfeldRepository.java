package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface TaetigkeitsfeldRepository extends JpaRepository<Taetigkeitsfeld, Integer> {
}
=======
import org.springframework.stereotype.Repository;

@Repository
public interface TaetigkeitsfeldRepository extends JpaRepository<Taetigkeitsfeld, String> {
}
>>>>>>> 24f3e91607fa3c5cd4e1689b30f715d4f0b2d7ca
