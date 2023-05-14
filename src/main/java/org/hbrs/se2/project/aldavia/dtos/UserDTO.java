package org.hbrs.se2.project.aldavia.dtos;

import java.util.List;

public interface UserDTO {
    public int getId();
    public String getUserid();
    public List<RolleDTO> getRoles();
}
