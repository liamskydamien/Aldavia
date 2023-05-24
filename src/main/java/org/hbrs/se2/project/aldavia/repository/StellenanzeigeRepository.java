package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StellenanzeigeRepository extends JpaRepository<Stellenanzeige, Integer> {
    Optional<Stellenanzeige> findByBezeichnung(String bezeichnung);
}
