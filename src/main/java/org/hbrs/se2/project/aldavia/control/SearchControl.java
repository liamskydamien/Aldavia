package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.StellenanzeigeDTO;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.service.StellenanzeigenService;
import org.hbrs.se2.project.aldavia.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class SearchControl {

    // Services
    private final StudentService studentService;
    private final StellenanzeigenService stellenanzeigenService;

    // Utils
    private final Logger logger = LoggerFactory.getLogger(SearchControl.class);
    private final StellenanzeigeDTOFactory stellenanzeigeDTOFactory = StellenanzeigeDTOFactory.getInstance();

    @Autowired
    public SearchControl(StudentService studentService, StellenanzeigenService stellenanzeigenService) {
        this.studentService = studentService;
        this.stellenanzeigenService = stellenanzeigenService;
    }

    /**
     * Returns all Stellenanzeigen
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





}
