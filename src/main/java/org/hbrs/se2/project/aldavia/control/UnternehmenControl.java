package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.ChangeStudentInformationDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UnternehmenControl {
    @Autowired
    UnternehmenRepository unternehmenRepository;

    public Unternehmen getUnternehmen(String unternehmenName) throws ProfileException {
        Optional<Unternehmen> unternehmen = unternehmenRepository.findByName(unternehmenName);
        if (unternehmen.isPresent()) {
            return unternehmen.get();
        }
        else {
            throw new ProfileException("Student not found", ProfileException.ProfileExceptionType.ProfileNotFound);
        }
    }

    public void updateUnternehmenInformation(Unternehmen unternehmen, UnternehmenProfileDTO dto) throws ProfileException {
        try {

            User user = unternehmen.getUser();

            if(dto.getName() != null) {
                unternehmen.setName(dto.getName());
            }

            if(dto.getAdressen() != null) {
                if (!(dto.getAdressen().equals(unternehmen.getAdressen()))) {
                    for(Adresse a: unternehmen.getAdressen()) {
                        unternehmen.removeAdresse(a);
                    }
                    for(Adresse a: dto.getAdressen()) {
                        unternehmen.addAdresse(a);
                    }
                }
            }

            if(dto.getStellenanzeigen() != null) {
                if(!(dto.getStellenanzeigen().equals(unternehmen.getStellenanzeigen()))) {
                    for(Stellenanzeige s: unternehmen.getStellenanzeigen()) {
                        unternehmen.removeStellenanzeige(s);
                    }
                    for(Stellenanzeige s: dto.getStellenanzeigen()) {
                        unternehmen.addStellenanzeige(s);
                    }
                }
            }

            if(dto.getAp_nachname() != null) {
                if (!dto.getAp_nachname().equals(unternehmen.getAp_nachname())) {
                    unternehmen.setAp_nachname(dto.getAp_nachname());
                }
            }

            if(dto.getAp_vorname() != null) {
                if (!dto.getAp_vorname().equals(unternehmen.getAp_vorname())) {
                    unternehmen.setAp_vorname(dto.getAp_vorname());
                }
            }

            if(dto.getWebside() != null) {
                unternehmen.setWebseite(dto.getWebside());
            }

            if (dto.getBeschreibung() != null) {
                user.setBeschreibung(dto.getBeschreibung());
            }

            if (dto.getEmail() != null) {
                user.setEmail(dto.getEmail());
            }

            if (dto.getTelefonnummer() != null) {
                user.setPhone(dto.getTelefonnummer());
            }

            if (dto.getProfilbild() != null) {
                user.setProfilePicture(dto.getProfilbild());
            }

            if(dto.getPassword() != null) {
                user.setPassword(dto.getPassword());
            }


            unternehmenRepository.save(unternehmen);
        }
        catch (Exception e) {
            throw new ProfileException("Error while updating student information", ProfileException.ProfileExceptionType.DatabaseConnectionFailed);
        }
    }
}
