package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.BewerbungsDTO;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;

import java.util.ArrayList;
import java.util.List;

public class BewerbungsListFactory {
    private static BewerbungsListFactory instance;

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
    public List<BewerbungsDTO> createBewerbungsDTOs(List<Bewerbung> bewerbungsList){
        List<BewerbungsDTO> bewerbungsDTOs = new ArrayList<>();
        for (Bewerbung bewerbung : bewerbungsList) {
            bewerbungsDTOs.add(BewerbungsDTO.builder()
                    .id(bewerbung.getId())
                    .studentId(bewerbung.getStudent().getId())
                    .datum(bewerbung.getDatum())
                    .stellenanzeigeId(bewerbung.getStellenanzeige().getId())
                    .bewerbungsSchreiben(bewerbung.getBewerbungsSchreiben())
                    .build());
        }
        return bewerbungsDTOs;
    }
}
