package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.dtos.RolleDTO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;

import java.util.List;

// @Component
public class AuthorizationControl {

    /**
     * Methode zur Überprüfung, ob ein Benutzer eine gegebene Rolle besitzt
     * 
     */
    public boolean isUserInRole(UserDTO user , String role  ) {
        List<RolleDTO> rolleList = user.getRoles();
        // A bit lazy but hey it works ;-)
        for (  RolleDTO rolle : rolleList ) {
            if ( rolle.getBezeichhnung().equals(role) ) return true;
        }
        return false;
    }
}
