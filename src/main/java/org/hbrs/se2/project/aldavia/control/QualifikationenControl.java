package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.PersistenceException;
import org.hbrs.se2.project.aldavia.dtos.QualifikationsDTO;
import org.hbrs.se2.project.aldavia.entities.Qualifikation;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.repository.QualifikationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QualifikationenControl {

    @Autowired
    private QualifikationRepository qualifikationRepository;

    /**
     * Add a Qualifikation for a student
     * @param qualifikationsDTO The QualifikationsDTO
     * @param student The student
     * @throws PersistenceException If the Qualifikation is not found
     */
    public Qualifikation addUpdateQualifikation(QualifikationsDTO qualifikationsDTO, Student student) throws PersistenceException {
        if (qualifikationsDTO.getId() == -1) {
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
            return qualifikationRepository.save(qualifikation);
        } else {
            Optional<Qualifikation> awaitQualifikation = qualifikationRepository.findById(qualifikationsDTO.getId());
            if (awaitQualifikation.isPresent()) {
                Qualifikation qualifikation = awaitQualifikation.get();
                qualifikation.setBezeichnung(qualifikationsDTO.getBezeichnung());
                qualifikation.setBeschreibung(qualifikationsDTO.getBeschreibung());
                qualifikation.setBereich(qualifikationsDTO.getBereich());
                qualifikation.setVon(qualifikationsDTO.getVon());
                qualifikation.setBis(qualifikationsDTO.getBis());
                qualifikation.setBeschaftigungsverhaltnis(qualifikationsDTO.getBeschaeftigungsart());
                qualifikation.setInstitution(qualifikationsDTO.getInstitution());
                qualifikation.setStudent(student);
                return qualifikationRepository.save(qualifikation);
            } else {
                throw new PersistenceException(PersistenceException.PersistenceExceptionType.QualifikationNotFound, "Qualifikation not found");
            }
        }
    }

    /**
     * Remove a Qualifikation
     * @param qualifikationsDTO The QualifikationsDTO
     * @throws PersistenceException If the Qualifikation is not found
     */
    public void removeQualifikation(QualifikationsDTO qualifikationsDTO) throws PersistenceException {
        if (qualifikationRepository.existsById(qualifikationsDTO.getId())) {
            qualifikationRepository.deleteById(qualifikationsDTO.getId());
        } else {
            throw new PersistenceException(PersistenceException.PersistenceExceptionType.QualifikationNotFound, "Qualifikation not found");
        }
    }

    public void removeQualifikation(Qualifikation qualifikation) {
        qualifikationRepository.delete(qualifikation);
    }
}
