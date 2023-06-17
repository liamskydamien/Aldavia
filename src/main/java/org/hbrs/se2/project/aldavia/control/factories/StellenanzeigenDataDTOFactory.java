package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigenDataDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StellenanzeigenDataDTOFactory {

    private static final Logger logger = LoggerFactory.getLogger(StellenanzeigenDataDTOFactory.class);

    private static StellenanzeigenDataDTOFactory instance = null;

    private StellenanzeigenDataDTOFactory() {
    }

    public static StellenanzeigenDataDTOFactory getInstance() {
        if (instance == null) {
            instance = new StellenanzeigenDataDTOFactory();
        }
        return instance;
    }

    public StellenanzeigenDataDTO createStellenanzeigenDataDTO(Stellenanzeige stellenanzeige) {
        try {
            return StellenanzeigenDataDTO.builder()
                    .bewerbungen(createBewerbungsDTOs(stellenanzeige.getBewerbungen()))
                    .stellenanzeige(StellenanzeigeDTOFactory.getInstance().createStellenanzeigeDTO(stellenanzeige))
                    .build();
        }
        catch (ProfileException e) {
            logger.error("Error creating StellenanzeigenDataDTO", e);
            return null;
        }
    }

    private List<BewerbungsDTO> createBewerbungsDTOs(List<Bewerbung> bewerbungsList) throws ProfileException {
        List<BewerbungsDTO> bewerbungsDTOList = new ArrayList<>();
        for (Bewerbung bewerbung : bewerbungsList) {
            bewerbungsDTOList.add(BewerbungsDTO.builder()
                            .id(bewerbung.getId())
                            .bewerbungsSchreiben(bewerbung.getBewerbungsSchreiben())
                            .datum(bewerbung.getDatum())
                            .status(bewerbung.getStatus())
                            .student(StudentProfileDTOFactory.getInstance().createStudentProfileDTO(bewerbung.getStudent()))
                            .stellenanzeige(StellenanzeigeDTOFactory.getInstance().createStellenanzeigeDTO(bewerbung.getStellenanzeige()))
                    .build());
        }
        return bewerbungsDTOList;
    }

}
