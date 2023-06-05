package org.hbrs.se2.project.aldavia.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter


public class RegistrationDTOStudent {


	private String userName;

	private String vorname;

	private String nachname;
	private String mail;
	private String password;
	private int registrationDate;
	

	@Override
	public String toString() {
		return "UserDTO [UserName=" + userName + ", password=" + password + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegistrationDTOStudent other = (RegistrationDTOStudent) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName, vorname, nachname, mail, password, registrationDate);
	}
}
