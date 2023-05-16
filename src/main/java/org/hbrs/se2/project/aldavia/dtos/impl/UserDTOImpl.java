package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hbrs.se2.project.aldavia.dtos.RolleDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.entities.Rolle;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDTOImpl implements UserDTO {

    private int id;
    private String email;
    private String password;
    private String userid;
    private String phone;
    private String profilePicture;
    private List<RolleDTO> roles;
}
