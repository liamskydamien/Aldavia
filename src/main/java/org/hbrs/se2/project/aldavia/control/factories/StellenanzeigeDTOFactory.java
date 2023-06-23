package org.hbrs.se2.project.aldavia.control.factories;

import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.Bewerbung;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StellenanzeigeDTOFactory {
    private Logger logger = LoggerFactory.getLogger(StellenanzeigeDTOFactory.class);
    private static StellenanzeigeDTOFactory instance;
    public static synchronized StellenanzeigeDTOFactory getInstance() {
        if (instance == null) {
            instance = new StellenanzeigeDTOFactory();
        }
        return instance;
    }
    private StellenanzeigeDTOFactory() {

    }

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
                .unternehmen(UnternehmenProfileDTOFactory.getInstance().createUnternehmenProfileDTO(stellenanzeige.getUnternehmen_stellenanzeigen()))
                .build();

    }


    @SneakyThrows
    public StellenanzeigeDTO createStellenanzeigeDTOCompanyKnown(Stellenanzeige stellenanzeige, UnternehmenProfileDTO unternehmen) {
        logger.info("STELLENANZEIGE DTO WIRD ERSTELLT");
        StellenanzeigeDTO stellenanzeigeDTO = StellenanzeigeDTO.builder()
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
                .unternehmen(unternehmen)
                .build();
        if(stellenanzeige.getBewerbungen() != null) {
            stellenanzeigeDTO.setBewerbungen(createBewerbungsDTOList(stellenanzeige.getBewerbungen(), stellenanzeigeDTO));
        }

        return stellenanzeigeDTO;

    }

    private List<TaetigkeitsfeldDTO> createTaetigkeitsfeldDTOs(List<Taetigkeitsfeld> taetigkeitsfelder) {
        return taetigkeitsfelder.stream()
                .map(taetigkeitsfeld -> TaetigkeitsfeldDTO.builder()
                        .name(taetigkeitsfeld.getBezeichnung())
                        .build())
                .collect(Collectors.toList());
    }

    private List<BewerbungsDTO> createBewerbungsDTOList(List<Bewerbung> bewerbungsList, StellenanzeigeDTO stellenanzeigeDTO) throws ProfileException {
        List<BewerbungsDTO> bewerbungsDTOs = new ArrayList<>();
        for (Bewerbung bewerbung : bewerbungsList) {
            StudentProfileDTO studentProfileDTO = StudentProfileDTOFactory.getInstance().createStudentProfileDTO(bewerbung.getStudent());

            BewerbungsDTO bewerbungsDTO = BewerbungsDTO.builder()
                    .id(bewerbung.getId())
                    .bewerbungsSchreiben(bewerbung.getBewerbungsSchreiben())
                    .datum(bewerbung.getDatum())
                    .status(bewerbung.getStatus())
                    .student(studentProfileDTO)
                    .stellenanzeige(stellenanzeigeDTO)
                    .build();
            bewerbungsDTOs.add(bewerbungsDTO);
        }
        return bewerbungsDTOs;
    }
}
