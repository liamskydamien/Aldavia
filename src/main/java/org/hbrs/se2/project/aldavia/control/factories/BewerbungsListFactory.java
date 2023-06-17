package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;

import java.util.ArrayList;
import java.util.List;

public class BewerbungsListFactory {
    private static BewerbungsListFactory instance;
    private final StellenanzeigeDTOFactory stellenanzeigeDataDTOFactory = StellenanzeigeDTOFactory.getInstance();

    private BewerbungsListFactory() {
    }

    /**
     * Singleton Pattern
     * @return instance of BewerbungsListFactory
     */
    public static synchronized BewerbungsListFactory getInstance() {
        if (instance == null) {
            instance = new BewerbungsListFactory();
        }
        return instance;
    }

    /**
     * Create a List of BewerbungsDTOs from a List of Bewerbung
     * @param bewerbungsList List of Bewerbung
     * @return List of BewerbungsDTOs
     */
    public List<BewerbungsDTO> createBewerbungsDTOs(List<Bewerbung> bewerbungsList) throws ProfileException {
        List<BewerbungsDTO> bewerbungsDataDTOList = new ArrayList<>();
        for (Bewerbung bewerbung : bewerbungsList) {
            bewerbungsDataDTOList.add(createBewerbungsDTO(bewerbung));
        }
        return bewerbungsDataDTOList;
    }

    /**
     * Create a BewerbungsDTO from a Bewerbung
     * @param bewerbung Bewerbung
     * @return BewerbungsDTO
     */
    private BewerbungsDTO createBewerbungsDTO(Bewerbung bewerbung) throws ProfileException {
        return BewerbungsDTO.builder()
                .id(bewerbung.getId())
                .stellenanzeige(stellenanzeigeDataDTOFactory.createStellenanzeigeDTO(bewerbung.getStellenanzeige()))
                .datum(bewerbung.getDatum())
                .bewerbungsSchreiben(bewerbung.getBewerbungsSchreiben())
                .status(bewerbung.getStatus())
                .student(StudentProfileDTOFactory.getInstance().createStudentProfileDTO(bewerbung.getStudent()))
                .build();
    }
}
