package org.hbrs.se2.project.aldavia.test.BewerbungsTest;

import org.hbrs.se2.project.aldavia.control.exception.BewerbungsException;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.control.factories.StudentProfileDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.*;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
public class BewerbungsServiceTest {

    @Autowired
    private BewerbungsService bewerbungsService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UnternehmenRepository unternehmenRepository;

    @Autowired
    private StellenanzeigeRepository stellenanzeigeRepository;

    private Student studentEntity;
    private Unternehmen unternehmenEntity;
    private Stellenanzeige stellenanzeigeEntity;

    public static final String MESSAGE = "Wrong Exception thrown";


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
                .taetigkeitsfelder(new ArrayList<>())
                .build();

        stellenanzeigeEntity = stellenanzeigeRepository.save(stellenanzeige);
    }

    @AfterEach
    public void teardown() {
        studentRepository.delete(studentEntity);
        unternehmenRepository.delete(unternehmenEntity);
    }

    @Test
    public void testAddBewerbung() {
        try {
            Bewerbung bewerbung = bewerbungsService.addBewerbung(studentEntity, stellenanzeigeEntity, "Test");
            BewerbungsDTO bewerbungsDTO = BewerbungsDTO.builder()
                    .student(StudentProfileDTOFactory.getInstance().createStudentProfileDTO(studentEntity))
                    .stellenanzeige(StellenanzeigeDTOFactory.getInstance().createStellenanzeigeDTO(stellenanzeigeEntity))
                    .datum(bewerbung.getDatum())
                    .id(bewerbung.getId())
                    .build();

            assertEquals(bewerbung.getStudent().getId(), studentEntity.getId());
            assertEquals(bewerbung.getStellenanzeige().getId(), stellenanzeigeEntity.getId());

            Bewerbung bewerbungFetched = bewerbungsService.getBewerbung(bewerbungsDTO);
            assertEquals(bewerbungFetched.getStudent().getId(), studentEntity.getId());
            assertEquals(bewerbungFetched.getStellenanzeige().getId(), stellenanzeigeEntity.getId());
            assertEquals(bewerbungFetched.getDatum(), bewerbung.getDatum());
            assertEquals(bewerbungFetched.getBewerbungsSchreiben(), bewerbung.getBewerbungsSchreiben());
            assertThrows(BewerbungsException.class, () -> bewerbungsService.addBewerbung(studentEntity, stellenanzeigeEntity, "Test"));
        } catch (BewerbungsException | ProfileException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRemove() {
        try {
            Bewerbung bewerbung = bewerbungsService.addBewerbung(studentEntity, stellenanzeigeEntity, "Test");
            BewerbungsDTO bewerbungsDTO = BewerbungsDTO.builder()
                    .student(StudentProfileDTOFactory.getInstance().createStudentProfileDTO(studentEntity))
                    .stellenanzeige(StellenanzeigeDTOFactory.getInstance().createStellenanzeigeDTO(stellenanzeigeEntity))
                    .datum(bewerbung.getDatum())
                    .id(bewerbung.getId())
                    .build();

            bewerbungsService.removeBewerbung(bewerbung);
            BewerbungsException exception = assertThrows(BewerbungsException.class, () -> bewerbungsService.getBewerbung(bewerbungsDTO));

            assertEquals(exception.getExceptionType(), BewerbungsException.BewerbungsExceptionType.BEWERBUNG_NOT_FOUND, MESSAGE);
        } catch (BewerbungsException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBewerbungAlreadyExists() throws BewerbungsException {
        // Build
        Bewerbung bewerbung = bewerbungsService.addBewerbung(studentEntity, stellenanzeigeEntity, "Test");
        BewerbungsDTO bewerbungsDTO = BewerbungsDTO.builder()
                .studentId(studentEntity.getId())
                .stellenanzeigeId(stellenanzeigeEntity.getId())
                .datum(bewerbung.getDatum())
                .id(bewerbung.getId())
                .build();


        // Assert
        BewerbungsException ex2 = assertThrows(BewerbungsException.class, () -> {
            Bewerbung bewerbung2 = bewerbungsService.addBewerbung(studentEntity, stellenanzeigeEntity, "Test");
            BewerbungsDTO bewerbungsDTO2 = BewerbungsDTO.builder()
                    .studentId(studentEntity.getId())
                    .stellenanzeigeId(stellenanzeigeEntity.getId())
                    .datum(bewerbung2.getDatum())
                    .id(bewerbung2.getId())
                    .build();
        });

        assertEquals(BewerbungsException.BewerbungsExceptionType.BEWERBUNG_ALREADY_EXISTS, ex2.getExceptionType(), MESSAGE);
    }
}
