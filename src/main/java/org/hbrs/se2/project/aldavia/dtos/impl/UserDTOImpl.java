package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.*;
import org.hbrs.se2.project.aldavia.dtos.RolleDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOImpl implements UserDTO {

    private int id;
    private String email;
    private String password;
    private String userid;
    private String phone;
    private String profilePicture;
    private String beschreibung;
    private List<RolleDTO> roles;

}
