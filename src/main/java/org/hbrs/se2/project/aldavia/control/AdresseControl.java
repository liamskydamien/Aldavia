package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.AdresseDTOImpl;
import org.hbrs.se2.project.aldavia.entities.Adresse;
import org.hbrs.se2.project.aldavia.entities.Stellenanzeige;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.repository.AdresseRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdresseControl {

    @Autowired
    AdresseRepository adresseRepository;

    @Autowired
    UnternehmenRepository unternehmenRepository;

    public Adresse getAdresse(Integer id) throws ProfileException {
        Optional<Adresse> adresse = adresseRepository.findById(id);
        if (adresse.isPresent()) {
            return adresse.get();
        } else {
            throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.ProfileNotFound);
        }
    }

    public void updateAdresseInformation(Adresse adresse, AdresseDTOImpl dto) throws ProfileException {
        try {
            if (dto.getLand() != null) {
                adresse.setLand(dto.getLand());
            }

            if (dto.getUnternehmen() != null) {
                for (Unternehmen u : adresse.getUnternehmen()) {
                    adresse.removeUnternehmen(u);
                    unternehmenRepository.save(u);
                }
                for (Unternehmen u : dto.getUnternehmen()) {
                    adresse.addUnternehmen(u);
                    unternehmenRepository.save(u);
                }
            }

            if (dto.getPlz() != null) {
                adresse.setPlz(dto.getPlz());
            }

            if (dto.getOrt() != null) {
                adresse.setOrt(dto.getOrt());
            }

            if (dto.getHausnummer() != null) {
                adresse.setHausnummer(dto.getHausnummer());
            }

            adresseRepository.save(adresse);


        } catch (Exception e) {
            throw new ProfileException("Error while updating student information", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }

    public void deleteAdresse(Adresse adresse) throws ProfileException {
        if (adresse.getUnternehmen() != null) {
            for (Unternehmen u : adresse.getUnternehmen()) {
                adresse.removeUnternehmen(u);
            }
        }
        adresseRepository.delete(adresse);
    }

    public void newAdresse(AdresseDTOImpl dto) throws ProfileException {
        Adresse adresse = new Adresse();
        updateAdresseInformation(adresse,dto);

    }

}
