package org.hbrs.se2.project.aldavia.dtos;

import java.util.List;

public interface UserDTO {
    public int getId();

    public String getEmail();

    public String getPassword();

    public String getUserid();

    public String getPhone();

    public String getProfilePicture();

    public String getBeschreibung();

    public List<RolleDTO> getRoles();
}
