package org.hbrs.se2.project.aldavia.service;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.QualifikationsDTO;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QualifikationenService {

    @Autowired
    private QualifikationRepository qualifikationRepository;
    @Autowired
    private StudentRepository studentRepository;

    private final Logger logger = LoggerFactory.getLogger(QualifikationenService.class);

    /**
     * Add a Qualifikation for a student
     * @param qualifikationsDTO The QualifikationsDTO
     * @param student The student
     * @throws PersistenceException If the Qualifikation is not found
     */
    // TODO bug since the qualification is not updated but a new one gets created
    public Qualifikation addUpdateQualifikation(QualifikationsDTO qualifikationsDTO, Student student) throws PersistenceException {
        logger.info("Adding Qualifikation to Student with student id: " + student.getId());
        if (qualifikationsDTO.getId() == -1) {
            logger.info("Creating new Qualifikation");
            Qualifikation qualifikation = Qualifikation.builder()
                    .bezeichnung(qualifikationsDTO.getBezeichnung())
                    .beschreibung(qualifikationsDTO.getBeschreibung())
                    .bereich(qualifikationsDTO.getBereich())
                    .von(qualifikationsDTO.getVon())
                    .bis(qualifikationsDTO.getBis())
                    .beschaftigungsverhaltnis(qualifikationsDTO.getBeschaeftigungsart())
                    .institution(qualifikationsDTO.getInstitution())
                    .build();
            qualifikation.setStudent(student);
            logger.info("Saving Qualifikation with id: " + qualifikation.getId() + " to database");
            return qualifikationRepository.save(qualifikation);
        } else {
            logger.info("Fetching Qualifikation with id: " + qualifikationsDTO.getId());
            Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(qualifikationsDTO.getId());
            if (awaitQualifikation.isPresent()) {
                logger.info("Updating Qualifikation");
                Qualifikation qualifikation = awaitQualifikation.get();
                qualifikation.setBezeichnung(qualifikationsDTO.getBezeichnung());
                qualifikation.setBeschreibung(qualifikationsDTO.getBeschreibung());
                qualifikation.setBereich(qualifikationsDTO.getBereich());
                qualifikation.setVon(qualifikationsDTO.getVon());
                qualifikation.setBis(qualifikationsDTO.getBis());
                qualifikation.setBeschaftigungsverhaltnis(qualifikationsDTO.getBeschaeftigungsart());
                qualifikation.setInstitution(qualifikationsDTO.getInstitution());
                qualifikation.setStudent(student);
                logger.info("Saving Qualifikation");
                return qualifikationRepository.save(qualifikation);
            } else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.QUALIFIKATION_NOT_FOUND, "Qualifikation not found");
            }
        }
    }

    /**
     * Remove a Qualifikation
     * @param qualifikationsDTO The QualifikationsDTO
     * @throws PersistenceException If the Qualifikation is not found
     */
    public void removeQualifikation(QualifikationsDTO qualifikationsDTO) throws PersistenceException {
        logger.info("Removing Qualifikation with id: " + qualifikationsDTO.getId());
        if (qualifikationRepository.existsById(qualifikationsDTO.getId())) {
            qualifikationRepository.deleteById(qualifikationsDTO.getId());
            logger.info("Qualifikation removed");
        } else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.QUALIFIKATION_NOT_FOUND, "Qualifikation not found");
        }
    }

    public void removeQualifikation(Qualifikation qualifikation) {
        logger.info("Removing Qualifikation with id: " + qualifikation.getId());
        Student student = qualifikation.getStudent();
        student.removeQualifikation(qualifikation);
        studentRepository.save(student);
        qualifikationRepository.delete(qualifikation);
        logger.info("Qualifikation removed");
    }
}
