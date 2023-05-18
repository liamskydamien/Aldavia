package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Sprache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SprachenRepository extends JpaRepository<Sprache, Integer> {
    Optional<Sprache> findByNameAndLevel(String name, String level);
    boolean existsByNameAndLevel(String name, String level);
}
