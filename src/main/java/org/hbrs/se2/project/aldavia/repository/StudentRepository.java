package org.hbrs.se2.project.aldavia.repository;

import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUser(User user);

    @Query("SELECT s FROM Student s WHERE s.user.userid = ?1")
    Optional<Student> findByUserID(String userid);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.matrikelNummer = ?1")
    void deleteByMatrikelNummer(String matrikelNummer);
}
