package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.BewerbungsControl;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.BewerbungRepository;
import org.hbrs.se2.project.aldavia.repository.StellenanzeigeRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.service.BewerbungsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
public class BewerbungsControlTest {

    // Class for Testing
    @Autowired
    private BewerbungsControl bewerbungsControl;

    // Repositories for creating test data
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UnternehmenRepository unternehmenRepository;
    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    // Service for testing
    @Autowired
    private BewerbungsService bewerbungsService;
    @Autowired
    private BewerbungRepository bewerbungRepository;

    // Test data
    private Student studentEntity;
    private Unternehmen unternehmenEntity;
    private Stellenanzeige stellenanzeigeEntity;

    @BeforeEach
    public void setup() {

        User user = User.builder()
                .userid("testBewerbung")
                .password("test")
                .email("test@bewerbung-aldavia-student.de")
                .build();

        Student student = Student.builder()
                .user(user)
                .vorname("test")
                .nachname("test")
                .build();

        User user2 = User.builder()
                .userid("testBewerbung2")
                .password("test")
                .email("test@bewerbung-aldavia-unternehmen.de")
                .build();

        Unternehmen unternehmen = Unternehmen.builder()
                .user(user2)
                .name("test")
                .build();

        studentEntity = studentRepository.save(student);
        unternehmenEntity = unternehmenRepository.save(unternehmen);

        Stellenanzeige stellenanzeige = Stellenanzeige.builder()
                .start(LocalDate.now())
                .ende(LocalDate.now().plusDays(10))
                .beschreibung("test")
                .beschaeftigungsumfang("test")
                .beschaeftigungsverhaeltnis("test")
                .bezeichnung("test")
                .unternehmen_stellenanzeigen(unternehmenEntity)
                .build();

        stellenanzeigeEntity = stellenanzeigeRepository.save(stellenanzeige);
    }

    @AfterEach
    public void teardown() {
        studentRepository.delete(studentEntity);
        unternehmenRepository.delete(unternehmenEntity);
    }

    @Test
    public void testBewerbung() {

        StellenanzeigeDTO stellenanzeigeDTO = StellenanzeigeDTO.builder()
                .id(stellenanzeigeEntity.getId())
                .build();

        try {
            // Test ADD
            bewerbungsControl.addBewerbung(studentEntity.getUser().getUserid(), stellenanzeigeDTO, "test");
            Bewerbung bewerbung = bewerbungRepository.findByStudentAndStellenanzeige(studentEntity, stellenanzeigeEntity).orElseThrow();
            assertEquals(bewerbung.getStudent(), studentEntity);
            assertEquals(bewerbung.getStellenanzeige(), stellenanzeigeEntity);
            assertEquals(bewerbung.getBewerbungsSchreiben(), "test");

            // Build DTO
            BewerbungsDTO bewerbungDTO = BewerbungsDTO.builder()
                    .id(bewerbung.getId())
                    .bewerbungsSchreiben("test")
                    .studentId(studentEntity.getId())
                    .stellenanzeigeId(stellenanzeigeEntity.getId())
                    .build();

            // Test DELETE
            bewerbungsControl.deleteBewerbung(bewerbungDTO);
            assertFalse(bewerbungRepository.existsById(bewerbung.getId()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
