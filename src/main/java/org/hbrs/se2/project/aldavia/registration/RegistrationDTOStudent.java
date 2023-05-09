package org.hbrs.se2.project.aldavia.registration;

public class RegistrationDTOStudent {

	private String userName;

	private String vorname;

	private String nachname;
	private String mail;
	private String password;
	private int registrationDate;
	private boolean isStudent;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String name) {
		this.userName = name;
	}

	public String getVorName() {
		return vorname;
	}

	public void setVorName(String name) {
		vorname = name;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String name){
		nachname = name;
	}


	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(int date) {
		registrationDate = date;
	}

	public boolean getStatusStudent() {
		return isStudent;
	}

	public void setStatusStudent(boolean status) {
		isStudent = status;
	}
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

}
