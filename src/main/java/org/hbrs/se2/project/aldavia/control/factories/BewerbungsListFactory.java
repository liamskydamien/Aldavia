package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.BewerbungsDataDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;

import java.util.ArrayList;
import java.util.List;

public class BewerbungsListFactory {
    private static BewerbungsListFactory instance;
    private final UserDataDTOFactory userDataDTOFactory = UserDataDTOFactory.getInstance();
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
    public List<BewerbungsDataDTO> createBewerbungsDataStudentDTOs(List<Bewerbung> bewerbungsList){
        List<BewerbungsDataDTO> bewerbungsDataDTOList = new ArrayList<>();
        for (Bewerbung bewerbung : bewerbungsList) {
            bewerbungsDataDTOList.add(createBewerbungsDataStudentDTO(bewerbung));
        }
        return bewerbungsDataDTOList;
    }

    /**
     * Create a BewerbungsDTO from a Bewerbung
     * @param bewerbung Bewerbung
     * @return BewerbungsDTO
     */
    private BewerbungsDataDTO createBewerbungsDataStudentDTO(Bewerbung bewerbung){
        return BewerbungsDataDTO.builder()
                .id(bewerbung.getId())
                .unternehmen(userDataDTOFactory.createUnternehmenDataDTO(bewerbung.getStellenanzeige().getUnternehmen_stellenanzeigen()))
                .stellenanzeige(stellenanzeigeDataDTOFactory.createStellenanzeigeDTO(bewerbung.getStellenanzeige()))
                .datum(bewerbung.getDatum())
                .bewerbungsSchreiben(bewerbung.getBewerbungsSchreiben())
                .status(bewerbung.getStatus())
                .build();
    }

    /**
     * Create a List of BewerbungsDTOs from a List of Bewerbung
     * @param bewerbungsList List of Bewerbung
     * @return List of BewerbungsDTOs
     */
    public List<BewerbungsDataDTO> createBewerbungsDataUnternehmenDTOs(List<Bewerbung> bewerbungsList){
        List<BewerbungsDataDTO> bewerbungsDataDTOList = new ArrayList<>();
        for (Bewerbung bewerbung : bewerbungsList) {
            bewerbungsDataDTOList.add(createBewerbungsDataUnternehmenDTO(bewerbung));
        }
        return bewerbungsDataDTOList;
    }

    /**
     * Create a BewerbungsDTO from a Bewerbung
     * @param bewerbung Bewerbung
     * @return BewerbungsDTO
     */
    private BewerbungsDataDTO createBewerbungsDataUnternehmenDTO(Bewerbung bewerbung){
        return BewerbungsDataDTO.builder()
                .id(bewerbung.getId())
                .student(userDataDTOFactory.createStudentDataDTO(bewerbung.getStudent()))
                .stellenanzeige(stellenanzeigeDataDTOFactory.createStellenanzeigeDTO(bewerbung.getStellenanzeige()))
                .datum(bewerbung.getDatum())
                .bewerbungsSchreiben(bewerbung.getBewerbungsSchreiben())
                .status(bewerbung.getStatus())
                .build();
    }


}
