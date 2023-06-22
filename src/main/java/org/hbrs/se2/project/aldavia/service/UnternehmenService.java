package org.hbrs.se2.project.aldavia.service;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UnternehmenService {

    private final UnternehmenRepository unternehmenRepository;


    private final UserRepository userRepository;

    public Unternehmen getUnternehmen(String userName) throws ProfileException {
        Optional<User> user = userRepository.findByUserid(userName);
        if (user.isPresent()) {
            Optional<Unternehmen> unternehmen = unternehmenRepository.findByUser(user.get());
            if (unternehmen.isPresent()) {
                return unternehmen.get();
            } else {
                throw new ProfileException("Unternehmen nicht auffindbar!", ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
            }
        } else {
            throw new ProfileException("Unternehmen nicht durch User gefunden", ProfileException.ProfileExceptionType.PROFILE_NOT_FOUND);
        }

    }

    public void updateUnternehmenInformation(Unternehmen unternehmen, UnternehmenProfileDTO dto) throws ProfileException {
        try {

            User user = unternehmen.getUser();

            if (dto.getName() != null) {
                unternehmen.setName(dto.getName());
            }

            if (dto.getAdressen() != null && unternehmen.getAdressen() != null) {
                if (!(dto.getAdressen().equals(unternehmen.getAdressen()))) {
                    unternehmen.getAdressen().clear();
                    Set<Adresse> adressenFromDTO = dto.getAdressen();
                    for (Adresse a : adressenFromDTO) {
                        unternehmen.addAdresse(a);
                    }
                }
            }

            if (dto.getStellenanzeigen() != null) {
                if (!(dto.getStellenanzeigen().equals(unternehmen.getStellenanzeigen()))) {
                    unternehmen.getStellenanzeigen().clear();
                    Set<Stellenanzeige> stellenanzeigeFromDTO = dto.getStellenanzeigen();
                    for (Stellenanzeige s : stellenanzeigeFromDTO) {
                        unternehmen.addStellenanzeige(s);
                    }
                }
            }


            /*if (dto.getStellenanzeigen() != null) {
                if (!(dto.getStellenanzeigen().equals(unternehmen.getStellenanzeigen()))) {
                    for (Stellenanzeige s : unternehmen.getStellenanzeigen()) {
                        unternehmen.removeStellenanzeige(s);
                    }
                    for (Stellenanzeige s : dto.getStellenanzeigen()) {
                        unternehmen.addStellenanzeige(s);
                    }
                }
            }*/

            if (dto.getAp_nachname() != null) {
                if (!dto.getAp_nachname().equals(unternehmen.getAp_nachname())) {
                    unternehmen.setAp_nachname(dto.getAp_nachname());
                }
            }

            if (dto.getAp_vorname() != null) {
                if (!dto.getAp_vorname().equals(unternehmen.getAp_vorname())) {
                    unternehmen.setAp_vorname(dto.getAp_vorname());
                }
            }

            if (dto.getWebside() != null) {
                unternehmen.setWebseite(dto.getWebside());
            }

            if (dto.getBeschreibung() != null) {
                unternehmen.setBeschreibung(dto.getBeschreibung());
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

            if (dto.getPassword() != null) {
                user.setPassword(dto.getPassword());
            }

            unternehmenRepository.save(unternehmen);

        } catch (Exception e) {
            throw e;
        }
    }
    public void deleteUnternehmen(Unternehmen unternehmen) throws ProfileException {
        if (unternehmen.getStellenanzeigen() != null) {
            for (Stellenanzeige s : unternehmen.getStellenanzeigen()) {
                unternehmen.removeStellenanzeige(s);
            }
        }

        if(unternehmen.getAdressen() != null) {
            for (Adresse a : unternehmen.getAdressen()) {
                unternehmen.removeAdresse(a);
            }
        }
        unternehmenRepository.delete(unternehmen);
    }

}

