package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Kenntnis;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Taetigkeitsfeld;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.hbrs.se2.project.aldavia.util.comperators.StellenanzeigenComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class SearchControl {

    // Controls
    private final BewerbungsControl bewerbungsControl;

    // Services
    private final StudentService studentService;
    private final StellenanzeigenService stellenanzeigenService;

    // Utils
    private final Logger logger = LoggerFactory.getLogger(SearchControl.class);
    private final StellenanzeigeDTOFactory stellenanzeigeDTOFactory = StellenanzeigeDTOFactory.getInstance();

    /**
     * Returns all Stellenanzeigen
     *
     * @return List of StellenanzeigeDTOs
     */
    public List<StellenanzeigeDTO> getAllStellenanzeigen() {
        logger.info("Getting all Stellenanzeigen");
        List<Stellenanzeige> stellenanzeigen = stellenanzeigenService.getStellenanzeigen();
        logger.info("Found {} Stellenanzeigen", stellenanzeigen.size());
        List<StellenanzeigeDTO> stellenanzeigenDTO = stellenanzeigen.stream().map(stellenanzeigeDTOFactory::createStellenanzeigeDTO).collect(Collectors.toList());
        logger.info("Created {} StellenanzeigeDTOs", stellenanzeigenDTO.size());
        return stellenanzeigenDTO;
    }

    /**
     * Sorts the outputted List based on the users preferences
     * @param username Username of the student
     * @return  a list sorted on the users preferences
     */
    public List<StellenanzeigeDTO> getRecommendedStellenanzeigen(String username) {
        logger.info("Collecting recommended Stellenanzeigen for user {}", username);
        logger.info("Collecting information about user {}", username);
        try {
            Student student = studentService.getStudent(username);
            logger.info("Found user {}", username);
            logger.info("Collecting all information about {} interests and skills", username);
            List<String> interessen = student.getTaetigkeitsfelder().stream().map(Taetigkeitsfeld::getBezeichnung).collect(Collectors.toList());
            List<String> kenntnisse = student.getKenntnisse().stream().map(Kenntnis::getBezeichnung).collect(Collectors.toList());
            logger.info("Collected needed information.");
            StellenanzeigenComparator stellenanzeigenComparator = new StellenanzeigenComparator(interessen, kenntnisse);
            List<StellenanzeigeDTO> stellenanzeigenDTOs = getAllStellenanzeigen();
            logger.info("Sorting Stellenanzeigen based on user");
            stellenanzeigenDTOs.sort(stellenanzeigenComparator);
            logger.info("Sorted Stellenanzeigen accordingly.");
            return stellenanzeigenDTOs;
        } catch (Exception e) {
            logger.error("Could not find user {}", username);
            return getAllStellenanzeigen();
        }
    }

    public BewerbungsControl getBewerbungsControl() {
        return bewerbungsControl;
    }

}
