package org.hbrs.se2.project.aldavia.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.control.factories.StellenanzeigeDTOFactory;
import org.hbrs.se2.project.aldavia.dtos.AdresseDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenProfileDTO;
import org.hbrs.se2.project.aldavia.entities.*;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
@Transactional
public class UnternehmenService {

    private final UnternehmenRepository unternehmenRepository;

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UnternehmenService.class);

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
            logger.info("Updating unternehmen " + unternehmen.getUser().getUserid());

            User user = unternehmen.getUser();
            updateUserData(user, dto);
            updateUnternehmensData(unternehmen, dto);
            logger.info("Saving updated unternehmen " + unternehmen.getUser().getUserid());
            unternehmenRepository.save(unternehmen);

        } catch (Exception e) {
            throw new ProfileException("Unternehmen konnte nicht geupdatet werden", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }
    }
    public void deleteUnternehmen(Unternehmen unternehmen) {
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

    private void updateUserData(User user, UnternehmenProfileDTO dto) {

        StudentService.changeData(user, dto.getEmail(), dto.getTelefonnummer(), dto.getProfilbild());

        if (dto.getPassword() != null) {
                user.setPassword(dto.getPassword());
            }
    }


    private void updateUnternehmensData(Unternehmen unternehmen, UnternehmenProfileDTO dto){
        if (dto.getName() != null) {
            unternehmen.setName(dto.getName());
        }
        //Comparing by size since there is no update opition for Adressen
        // TODO: What does this method do?
            if (dto.getAdressen() != null && unternehmen.getAdressen() != null) {
                if (!(dto.getAdressen().equals(unternehmen.getAdressen()))) {
                    unternehmen.getAdressen().clear();
                }
            }

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


    }
    @SneakyThrows
    public void createOrUpdateUnternehmen(Unternehmen unternehmen) {
        logger.info("Creating or Updating Unternehmen " + unternehmen.getUser().getUserid());
        try{
            unternehmenRepository.save(unternehmen);
        } catch (Exception e){
            logger.error("Error while creating or updating Unternehmen " + unternehmen.getUser().getUserid());
            throw new ProfileException("Unternehmen konnte nicht geupdatet werden", ProfileException.ProfileExceptionType.DATABASE_CONNECTION_FAILED);
        }


    }

    }

