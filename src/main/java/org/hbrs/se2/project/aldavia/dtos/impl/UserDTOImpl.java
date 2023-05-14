package org.hbrs.se2.project.aldavia.dtos.impl;

import lombok.Getter;
import lombok.Setter;
import org.hbrs.se2.project.aldavia.dtos.RolleDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;

import java.util.List;

@Getter
@Setter
public class UserDTOImpl implements UserDTO {

    private int id;
    private String firstname;
    private String lastname;
    private List<RolleDTO> roles;
    private String userid;


}
