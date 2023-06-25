package org.hbrs.se2.project.aldavia.test.DTOs;

import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigenDataDTO;
import org.hbrs.se2.project.aldavia.dtos.StudentProfileDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StellenanzeigenDataDTOTest {

    @Test
    public void testGettersAndSetters() {
        // Create an instance of StellenanzeigenDataDTO
        StellenanzeigenDataDTO dataDTO = new StellenanzeigenDataDTO();

        // Create an instance of StellenanzeigeDTO
        StellenanzeigeDTO stellenanzeigeDTO = StellenanzeigeDTO.builder()
                .id(1)
                .bezeichnung("Software Engineer")
                .beschreibung("Job description")
                .build();

        // Create a list of BewerbungsDTO
        List<BewerbungsDTO> bewerbungen = new ArrayList<>();

        // Create an instance of BewerbungsDTO
        BewerbungsDTO bewerbungDTO = BewerbungsDTO.builder()
                .id(1)
                .datum(LocalDate.now())
                .student(new StudentProfileDTO())
                .stellenanzeige(stellenanzeigeDTO)
                .status("Submitted")
                .bewerbungsSchreiben("Cover letter")
                .build();

        bewerbungen.add(bewerbungDTO);

        // Set values using setters
        dataDTO.setBewerbungen(bewerbungen);
        dataDTO.setStellenanzeige(stellenanzeigeDTO);

        // Test getters
        Assertions.assertEquals(bewerbungen, dataDTO.getBewerbungen());
        Assertions.assertEquals(stellenanzeigeDTO, dataDTO.getStellenanzeige());
    }
}

