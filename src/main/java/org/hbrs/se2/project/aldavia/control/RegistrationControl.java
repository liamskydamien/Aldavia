package org.hbrs.se2.project.aldavia.control;

import lombok.RequiredArgsConstructor;
import org.hbrs.se2.project.aldavia.entities.Rolle;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOCompany;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOStudent;
import org.hbrs.se2.project.aldavia.dtos.RegistrationResult;
import org.hbrs.se2.project.aldavia.repository.RolleRepository;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.hbrs.se2.project.aldavia.util.enums.Reason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class RegistrationControl {

    private final StudentRepository repositoryS;
    private final UnternehmenRepository repositoryC;
    private final UserRepository repositoryU;
    private final RolleRepository repositoryR;
    private final Logger logger = LoggerFactory.getLogger(RegistrationControl.class);



    public RegistrationResult createStudent(RegistrationDTOStudent dto ) {
        logger.info("Trying to create a new Student Acount.");
        RegistrationResult result = new RegistrationResult();
        String mailAddress = dto.getMail();
        String userName = dto.getUserName();
        Optional<User> oMail= repositoryU.findUserByEmail(mailAddress);
        Optional<User> oName = repositoryU.findUserByUserid(userName);
        if (oMail.isPresent()) {
            logger.info("The mail address is already in use.");
            result.setReason(Reason.EMAIL_ALREADY_EXISTS);
            result.setResult(false);
        } else if (oName.isPresent()) {
            logger.info("The userName is already in use.");
            result.setReason(Reason.USERNAME_ALREADY_EXISTS);
            result.setResult(false);

        } else {
            result.setReason(Reason.REGISTRATION_SUCCESSFULL);
            result.setResult(true);
            Rolle rolle = Rolle.builder().bezeichnung(Globals.Roles.STUDENT).build();
            repositoryR.save(rolle); // Save the role in the repository
            User userNeu = User.builder()
                    .email(dto.getMail())
                    .password(dto.getPassword())
                    .userid(dto.getUserName())
                    .build();

            userNeu.addRolle(rolle); // Add the role to the user
            result.setReason(Reason.REGISTRATION_SUCCESSFULL);
            result.setResult(true);


            repositoryU.save(userNeu); // Save the user in the repository

            Student studentNeu = Student.builder()
                    .nachname(dto.getNachname())
                    .vorname(dto.getVorname())
                    .user(userNeu)
                    .build();

            repositoryS.save(studentNeu);
            logger.info("Successfully created a student acount with the following userName: " + userNeu.getUserid());


        }

        return result;
    }

    public RegistrationResult createUnternehmen(RegistrationDTOCompany dto) {
        logger.info("Trying to create a new Company Acount.");
        RegistrationResult result = new RegistrationResult();

        String mailAddress = dto.getMail();
        String userName = dto.getUserName();
        if (!(repositoryU.findUserByEmail(mailAddress).isEmpty())) {
            result.setReason(Reason.EMAIL_ALREADY_EXISTS);
            result.setResult(false);
        } else if (!(repositoryU.findUserByUserid(userName).isEmpty())) {
            result.setReason(Reason.USERNAME_ALREADY_EXISTS);
            result.setResult(false);

        } else {
            result.setReason(Reason.REGISTRATION_SUCCESSFULL);
            result.setResult(true);
            Rolle rolle = Rolle.builder().bezeichnung(Globals.Roles.UNTERNEHMEN).build();
            repositoryR.save(rolle);
            User userNeu =
                    User.builder()
                            .email(dto.getMail())
                            .password(dto.getPassword())
                            .userid(dto.getUserName())
                            .build();
            userNeu.addRolle(rolle);
            repositoryU.save(userNeu);

            Unternehmen unternehmenNeu =
                    Unternehmen.builder()
                            .name(dto.getCompanyName())
                            .webseite(dto.getWebseite())
                            .user(userNeu)
                            .build();



            repositoryC.save(unternehmenNeu);
            logger.info("Successfully created a company acount with the following userName: " + userNeu.getUserid());


        }

        return result;
    }




}
