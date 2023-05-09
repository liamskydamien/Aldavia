package org.hbrs.se2.project.aldavia.registration;

import org.springframework.stereotype.Component;

@Component
public class RegistrationControlCompany {
	
	public RegistrationResult registerUser( RegistrationDTOStudent dto ) {
		RegistrationResult result = new RegistrationResult();

		String mailAddress = dto.getMail(); // E-Mail-Adresse
		// CheckOnDB( mailAddress )  --> Check Existenz über eine DAO (z.B. UserDAO) - ToDo

		String userName = dto.getUserName();
		//CheckOnDB if userName is available

		result.setReason(RegistrationResult.REGISTRATION_SUCCESSFULL);
		result.setResult(true);

		return result;
	}

}
