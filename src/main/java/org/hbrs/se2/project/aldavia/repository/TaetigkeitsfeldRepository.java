package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("taetigkeitsfeldRepository")
public interface TaetigkeitsfeldRepository extends JpaRepository<Taetigkeitsfeld, String> {
}
