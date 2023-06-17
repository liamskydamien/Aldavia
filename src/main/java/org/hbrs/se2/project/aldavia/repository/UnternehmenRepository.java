package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UnternehmenRepository extends JpaRepository<Unternehmen, Integer> {
    Optional<Unternehmen> findByUser(User user);
    Optional<Unternehmen> findByName(String name);


    @Query("SELECT u FROM Unternehmen u WHERE u.user.userid = ?1")
    Optional<Unternehmen> findByUserID(String userid);




}
