package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaetigkeitsfeldRepository extends JpaRepository<Taetigkeitsfeld, String> {
}

