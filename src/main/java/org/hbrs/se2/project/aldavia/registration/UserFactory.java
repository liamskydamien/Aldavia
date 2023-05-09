package org.hbrs.se2.project.aldavia.registration;

public class UserFactory {
	
	public static RegistrationDTOStudent createNewUserWithNameAndPassword(String name, String password ) {
		RegistrationDTOStudent dto = new RegistrationDTOStudent();
		dto.setUserName(name);
		dto.setPassword(password);
		return dto;
	}
	
	public static RegistrationDTOStudent createDefaultUserWithNoPassword() {
		RegistrationDTOStudent dto = UserFactory.getDefaultUser();
		dto.setPassword("");
		return dto;
	}
	
	public static RegistrationDTOStudent createDefaultUserWithNoPasswordAndNoAddress() {
		RegistrationDTOStudent dto = UserFactory.getDefaultUser();
		dto.setPassword("");
		dto.setMail("");
		return dto;
	}

	/**
	 * Erzeugung eines Default-Users mit vorbelegten Attributen
	 * (Template Pattern [GOF])
	 * @return
	 */
	private static RegistrationDTOStudent getDefaultUser() {
		RegistrationDTOStudent dto = new RegistrationDTOStudent();
		dto.setUserName("Stefan Meyer");
		dto.setPassword("abc99");
		dto.setMail("Bonn");

		return dto;
	}

}
