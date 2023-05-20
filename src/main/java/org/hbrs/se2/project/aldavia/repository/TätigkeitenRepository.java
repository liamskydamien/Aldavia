package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

public interface TätigkeitenRepository extends JpaRepository<Taetigkeitsfeld, Integer> {
}
