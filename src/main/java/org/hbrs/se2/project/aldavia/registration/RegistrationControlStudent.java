package org.hbrs.se2.project.aldavia.registration;

public class RegistrationControlStudent {
    private StudentRepository repository = new StudentRepository();

    public RegistrationResult createStudent( RegistrationDTOStudent dto ) {
        RegistrationResult result = new RegistrationResult();

        String mailAddress = dto.getMail(); // E-Mail-Adresse
        // CheckOnDB( mailAddress )  --> Check Existenz über eine DAO (z.B. UserDAO) - ToDo

        String userName = dto.getUserName();
        //CheckOnDB if userName is available


        result.setReason(RegistrationResult.REGISTRATION_SUCCESSFULL);
        result.setResult(true);
        repository.createStudent(dto);

        return result;
    }

}
