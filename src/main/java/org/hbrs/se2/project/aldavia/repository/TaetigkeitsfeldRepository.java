package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaetigkeitsfeldRepository extends JpaRepository<Taetigkeitsfeld, Integer> {
    @Query("SELECT t FROM Taetigkeitsfeld t WHERE t.bezeichnung = ?1")
    Optional<Taetigkeitsfeld> findByBezeichnung(String bezeichnung);

    @Modifying
    @Query("DELETE FROM Taetigkeitsfeld t WHERE t.bezeichnung = ?1")
    void deleteByBezeichnung(String bezeichnung);
}

