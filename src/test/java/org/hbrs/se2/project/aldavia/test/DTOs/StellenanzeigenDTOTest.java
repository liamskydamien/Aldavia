package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StellenanzeigenDTOTest {
        @Test
        public void testGettersAndSetters() {
            // Create an instance of StellenanzeigeDTO
            StellenanzeigeDTO stellenanzeigeDTO = new StellenanzeigeDTO();

            // Create a list of TaetigkeitsfeldDTO
            List<TaetigkeitsfeldDTO> taetigkeitsfelder = new ArrayList<>();

            // Create an instance of TaetigkeitsfeldDTO
            TaetigkeitsfeldDTO taetigkeitsfeldDTO = new TaetigkeitsfeldDTO();

            taetigkeitsfelder.add(taetigkeitsfeldDTO);

            // Create a list of BewerbungsDTO
            List<BewerbungsDTO> bewerbungen = new ArrayList<>();

            // Create an instance of BewerbungsDTO
            BewerbungsDTO bewerbungDTO = BewerbungsDTO.builder()
                    .id(1)
                    .datum(LocalDate.now())
                    .student(new StudentProfileDTO())
                    .status("Submitted")
                    .bewerbungsSchreiben("Cover letter")
                    .build();

            bewerbungen.add(bewerbungDTO);

            // Create an instance of UnternehmenProfileDTO
            UnternehmenProfileDTO unternehmenProfileDTO = new UnternehmenProfileDTO();

            // Set values using setters
            stellenanzeigeDTO.setId(1);
            stellenanzeigeDTO.setBezeichnung("Software Engineer");
            stellenanzeigeDTO.setBeschreibung("Job description");
            stellenanzeigeDTO.setBeschaeftigungsverhaeltnis("Full-time");
            stellenanzeigeDTO.setStart(LocalDate.now());
            stellenanzeigeDTO.setEnde(LocalDate.now().plusMonths(6));
            stellenanzeigeDTO.setErstellungsdatum(LocalDate.now());
            stellenanzeigeDTO.setBezahlung("Competitive salary");
            stellenanzeigeDTO.setBeschaeftigungsumfang("40 hours per week");
            stellenanzeigeDTO.setTaetigkeitsfelder(taetigkeitsfelder);
            stellenanzeigeDTO.setBewerbungen(bewerbungen);
            stellenanzeigeDTO.setUnternehmen(unternehmenProfileDTO);

            // Test getters
            Assertions.assertEquals(1, stellenanzeigeDTO.getId());
            Assertions.assertEquals("Software Engineer", stellenanzeigeDTO.getBezeichnung());
            Assertions.assertEquals("Job description", stellenanzeigeDTO.getBeschreibung());
            Assertions.assertEquals("Full-time", stellenanzeigeDTO.getBeschaeftigungsverhaeltnis());
            Assertions.assertEquals(LocalDate.now(), stellenanzeigeDTO.getStart());
            Assertions.assertEquals(LocalDate.now().plusMonths(6), stellenanzeigeDTO.getEnde());
            Assertions.assertEquals(LocalDate.now(), stellenanzeigeDTO.getErstellungsdatum());
            Assertions.assertEquals("Competitive salary", stellenanzeigeDTO.getBezahlung());
            Assertions.assertEquals("40 hours per week", stellenanzeigeDTO.getBeschaeftigungsumfang());
            Assertions.assertEquals(taetigkeitsfelder, stellenanzeigeDTO.getTaetigkeitsfelder());
            Assertions.assertEquals(bewerbungen, stellenanzeigeDTO.getBewerbungen());
            Assertions.assertEquals(unternehmenProfileDTO, stellenanzeigeDTO.getUnternehmen());
        }
}
