package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QualifikationRepository extends JpaRepository<Qualifikation, Integer> {
}
