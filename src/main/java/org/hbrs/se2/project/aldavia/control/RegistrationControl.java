package org.hbrs.se2.project.aldavia.control;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class RegistrationControl {
    @Autowired
    private StudentRepository repositoryS;

    @Autowired
    private UnternehmenRepository repositoryC;

    @Autowired
    private UserRepository repositoryU;

    @Autowired
    private RolleRepository repositoryR;



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
            Rolle rolle = Rolle.builder().bezeichnung(Globals.Roles.STUDENT).build();
            repositoryR.save(rolle); // Save the role in the repository
            User userNeu = User.builder()
                    .email(dto.getMail())
                    .password(dto.getPassword())
                    .userid(dto.getUserName())
                    .build();

            userNeu.addRolle(rolle); // Add the role to the user


            repositoryU.save(userNeu); // Save the user in the repository

            Student studentNeu = Student.builder()
                    .nachname(dto.getNachname())
                    .vorname(dto.getVorname())
                    .user(userNeu)
                    .build();

            repositoryS.save(studentNeu);
            /*if (userNeu.getRollen().isEmpty()){
                System.out.println("Rollen sind leer");
            } else {
                System.out.println(userNeu.getRollen().get(0).getBezeichnung());
            }*/


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
                            .user(userNeu)
                            .build();



            repositoryC.save(unternehmenNeu);


        }

        return result;
    }




}
