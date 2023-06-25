package org.hbrs.se2.project.aldavia.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTOCompany {


    private String userName;

    private String companyName;
    private String mail;
    private String password;
    private int registrationDate;
    private String webseite;


    @Override
    public String toString() {
        return "UserDTO [UserName=" + companyName + ", password=" + password + "]";
    }

}
