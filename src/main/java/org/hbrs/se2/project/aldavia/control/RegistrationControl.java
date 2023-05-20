package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOCompany;
import org.hbrs.se2.project.aldavia.dtos.RegistrationDTOStudent;
import org.hbrs.se2.project.aldavia.dtos.RegistrationResult;
import org.hbrs.se2.project.aldavia.repository.StudentRepository;
import org.hbrs.se2.project.aldavia.repository.UnternehmenRepository;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrationControl {
    @Autowired
    private StudentRepository repositoryS;

    @Autowired
    private UnternehmenRepository repositoryC;

    @Autowired
    private UserRepository repositoryU;

    public RegistrationResult createStudent(RegistrationDTOStudent dto ) {
        RegistrationResult result = new RegistrationResult();


        String mailAddress = dto.getMail();
        String userName = dto.getUserName();
        Optional<User> oMail= repositoryU.findUserByEmail(mailAddress);
        Optional<User> oName = repositoryU.findUserByUserid(userName);
        if (!(oMail.isEmpty())) {
            result.setReason(RegistrationResult.EMAIL_ALREADY_EXISTS);
            result.setResult(false);
        } else if (!(oName.isEmpty())) {
            result.setReason(RegistrationResult.USERNAME_ALREADY_EXISTS);
            result.setResult(false);

        } else {
            result.setReason(RegistrationResult.REGISTRATION_SUCCESSFULL);
            result.setResult(true);
            User userNeu =
                    User.builder()
                            .email(dto.getMail())
                            .password(dto.getPassword())
                            .userid(dto.getUserName())
                            .build();
            repositoryU.save(userNeu);

            Student studentNeu =
                    Student.builder()
                            .nachname(dto.getNachname())
                            .vorname(dto.getVorname())
                            .user(userNeu)
                            .build();
            repositoryS.save(studentNeu);


        }

        return result;
    }

    public RegistrationResult createUnternehmen(RegistrationDTOCompany dto) {
        RegistrationResult result = new RegistrationResult();

        String mailAddress = dto.getMail();
        String userName = dto.getUserName();
        if (!(repositoryU.findUserByEmail(mailAddress).isEmpty())) {
            result.setReason(RegistrationResult.EMAIL_ALREADY_EXISTS);
            result.setResult(false);
        } else if (!(repositoryU.findUserByUserid(userName).isEmpty())) {
            result.setReason(RegistrationResult.USERNAME_ALREADY_EXISTS);
            result.setResult(false);

        } else {
            result.setReason(RegistrationResult.REGISTRATION_SUCCESSFULL);
            result.setResult(true);
            User userNeu =
                    User.builder()
                            .email(dto.getMail())
                            .password(dto.getPassword())
                            .userid(dto.getUserName())
                            .build();
            repositoryU.save(userNeu);

            Unternehmen unternehmenNeu =
                    Unternehmen.builder()
                            .name(dto.getCompanyName())
                            .user(userNeu)
                            .build();



            repositoryC.save(unternehmenNeu);


        }

        return result;
    }




}
