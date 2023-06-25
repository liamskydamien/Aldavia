package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class BewerbungsDTOTest {
        @Test
        public void testGettersAndSetters() {
            // Create an instance of BewerbungsDTO
            BewerbungsDTO bewerbungDTO = new BewerbungsDTO();

            // Create an instance of StellenanzeigeDTO
            StellenanzeigeDTO stellenanzeigeDTO = StellenanzeigeDTO.builder()
                    .id(1)
                    .bezeichnung("Software Engineer")
                    .beschreibung("Job description")
                    .build();

            // Create an instance of StudentProfileDTO
            StudentProfileDTO studentProfileDTO = new StudentProfileDTO();

            // Set values using setters
            bewerbungDTO.setId(1);
            bewerbungDTO.setDatum(LocalDate.now());
            bewerbungDTO.setStudent(studentProfileDTO);
            bewerbungDTO.setStellenanzeige(stellenanzeigeDTO);
            bewerbungDTO.setStatus("Submitted");
            bewerbungDTO.setBewerbungsSchreiben("Cover letter");

            // Test getters
            Assertions.assertEquals(1, bewerbungDTO.getId());
            Assertions.assertEquals(LocalDate.now(), bewerbungDTO.getDatum());
            Assertions.assertEquals(studentProfileDTO, bewerbungDTO.getStudent());
            Assertions.assertEquals(stellenanzeigeDTO, bewerbungDTO.getStellenanzeige());
            Assertions.assertEquals("Submitted", bewerbungDTO.getStatus());
            Assertions.assertEquals("Cover letter", bewerbungDTO.getBewerbungsSchreiben());
        }
}
