package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnternehmenRepository extends JpaRepository<Unternehmen, Integer> {
    Optional<Unternehmen> findByUser(User user);

}
