package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.dtos.TaetigkeitsfeldDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenDataDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;

import java.util.List;
import java.util.stream.Collectors;

public class StellenanzeigeDTOFactory {
    private static StellenanzeigeDTOFactory instance;
    public static synchronized StellenanzeigeDTOFactory getInstance() {
        if (instance == null) {
            instance = new StellenanzeigeDTOFactory();
        }
        return instance;
    }
    private StellenanzeigeDTOFactory() {}

    public StellenanzeigeDTO createStellenanzeigeDTO(Stellenanzeige stellenanzeige) {
        return StellenanzeigeDTO.builder()
                .id(stellenanzeige.getId())
                .bezeichnung(stellenanzeige.getBezeichnung())
                .beschreibung(stellenanzeige.getBeschreibung())
                .beschaeftigungsverhaeltnis(stellenanzeige.getBeschaeftigungsverhaeltnis())
                .start(stellenanzeige.getStart())
                .ende(stellenanzeige.getEnde())
                .erstellungsdatum(stellenanzeige.getErstellungsdatum())
                .bezahlung(stellenanzeige.getBezahlung())
                .beschaeftigungsumfang(stellenanzeige.getBeschaeftigungsumfang())
                .taetigkeitsfelder(createTaetigkeitsfeldDTOs(stellenanzeige.getTaetigkeitsfelder()))
                .unternehmen(createUnternehmenDataDTO(stellenanzeige.getUnternehmen_stellenanzeigen()))
                .build();

    }

    private List<TaetigkeitsfeldDTO> createTaetigkeitsfeldDTOs(List<Taetigkeitsfeld> taetigkeitsfelder) {
        return taetigkeitsfelder.stream()
                .map(taetigkeitsfeld -> TaetigkeitsfeldDTO.builder()
                        .name(taetigkeitsfeld.getBezeichnung())
                        .build())
                .collect(Collectors.toList());
    }

    private UnternehmenDataDTO createUnternehmenDataDTO(Unternehmen unternehmen) {
        return UnternehmenDataDTO.builder()
                .name(unternehmen.getName())
                .profileLink("/unternehmen/" + unternehmen.getUser().getUserid())
                .build();
    }
}
