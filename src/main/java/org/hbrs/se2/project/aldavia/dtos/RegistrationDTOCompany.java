package org.hbrs.se2.project.aldavia.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter
@Component
public class RegistrationDTOCompany {


    private String userName;

    private String companyName;
    private String mail;
    private String password;
    private int registrationDate;
    private String standort;


    @Override
    public String toString() {
        return "UserDTO [UserName=" + companyName + ", password=" + password + "]";
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
        if (companyName == null) {
            if (other.companyName != null)
                return false;
        } else if (!companyName.equals(other.companyName))
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
        return Objects.hash(userName, companyName, mail, password, registrationDate, standort);
    }
}
