package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.RolleDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    /**
     * Methode zur Authentifizierung eines Benutzers
     * @param username Benutzername
     * @param password Passwort
     * @return true, wenn die Authentifizierung erfolgreich war, sonst false
     * @throws DatabaseUserException
     */
    public boolean authenticate(String username, String password ) throws DatabaseUserException {

        User tmpUser = this.getUserWithJPA( username , password );

        if ( tmpUser == null ) {
            throw new DatabaseUserException(
                    DatabaseUserException.
                            DatabaseUserExceptionType.
                            UserNotFound,
                    "No User could be found! Please check your credentials!");
        }
        this.userDTO = buildUserDTO( tmpUser );
        return true;
    }

    /**
     * Methode zur Anfrage des aktuellen Nutzers
     * @return UserDTO des aktuellen Nutzers
     */
    public UserDTO getCurrentUser(){
        return this.userDTO;

    }

    /**
     * Backdoor für Testzwecke (JPA)
     * @param username
     * @param password
     * @return
     * @throws DatabaseUserException

    public UserDTO TestGetUserWithJPA( String username , String password ) throws DatabaseUserException {
        return this.getUserWithJPA( username , password );
    }
    */

    /**
     * Methode zur Anfrage eines Benutzers mit JPA
     * @param username Benutzername
     * @param password Passwort
     * @return UserDTO
     * @throws DatabaseUserException
     */
    private User getUserWithJPA( String username , String password ) throws DatabaseUserException {
        Optional<User> userTmp;
        filterForCommonErrors(username, password);

        try {
            if(checkForEMailAdress(username)){
                userTmp = repository.findUserByEmailAndPassword(username, password);
            }
            else {
                userTmp = repository.findUserByUseridAndPassword(username, password);
            }
        } catch ( org.springframework.dao.DataAccessResourceFailureException e ) {
           throw new DatabaseUserException(
                   DatabaseUserException.
                        DatabaseUserExceptionType.
                        DatabaseConnectionFailed,
                   "A failure occured while trying to connect to database. Please try again later.");
        }
        return userTmp.orElse(null);
    }

    /**
     * Methode zur Filterung von häufigen Fehlern
     * @param username Benutzername / E-Mail-Adresse
     * @param password Passwort
     * @throws DatabaseUserException wenn ein Fehler auftritt
     */
    public void filterForCommonErrors(String username, String password) throws DatabaseUserException {
        if( username == null || password == null ) {
            throw new DatabaseUserException(
                    DatabaseUserException.
                            DatabaseUserExceptionType.
                            UserNotFound,
                    "Credentials are empty! Please check your credentials!");
        }
        else if ( username.equals("") || password.equals("") ) {
            throw new DatabaseUserException(
                    DatabaseUserException.
                            DatabaseUserExceptionType.
                            UserNotFound,
                    "Credentials are empty! Please check your credentials!");
        }
    }

    /**
     * Methode zur Überprüfung, ob der Benutzername eine E-Mail-Adresse ist
     * @param username Benutzername oder E-Mail-Adresse
     * @return true, wenn es sich um eine E-Mail-Adresse handelt, sonst false
     */
    private boolean checkForEMailAdress(String username){
        return username.contains("@");
    }

    /**
     * Methode zur Erstellung eines UserDTOs
     * @param user User
     * @return UserDTO
     */
    private UserDTO buildUserDTO(User user){
        return UserDTOImpl.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .userid(user.getUserid())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .beschreibung(user.getBeschreibung())
                .roles(user.getRollen().stream().map(role -> RolleDTOImpl.builder()
                        .bezeichnung(role.getBezeichnung())
                        .build()).collect(Collectors.toList()))
                .build();
    }

}
