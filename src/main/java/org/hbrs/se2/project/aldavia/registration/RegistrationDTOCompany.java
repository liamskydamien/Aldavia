package org.hbrs.se2.project.aldavia.registration;

public class RegistrationDTOCompany {

    private String name;
    private String mail;
    private String password;
    private int registrationDate;
    private boolean isStudent;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
        return "UserDTO [UserName=" + name + ", password=" + password + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RegistrationDTOCompany other = (RegistrationDTOCompany) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;

        return true;
    }

}
