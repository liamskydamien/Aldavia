package org.hbrs.se2.project.aldavia.util.Builder;

import org.hbrs.se2.project.aldavia.dtos.RolleDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.UserDTOImpl;

import java.util.List;

public class UserDTOBuilder {
    private String userName;
    private String password;
    private String mail;

    private String phone;

    private String profilePicture;

    private List<RolleDTO> roles;

    private int id;

    public UserDTOBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserDTOBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserDTOBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDTOBuilder setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserDTOBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserDTOBuilder setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    @SuppressWarnings("unchecked")
    public UserDTOBuilder setRoles(List<? extends RolleDTO> roles) {
        this.roles = (List<RolleDTO>) roles;
        return this;
    }

    public UserDTO createUserDTO() {
        return new UserDTOImpl(id, mail, password, userName, phone, profilePicture, roles);
    }
}
