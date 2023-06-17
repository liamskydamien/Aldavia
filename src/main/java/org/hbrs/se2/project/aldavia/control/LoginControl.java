package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.dtos.impl.RolleDTOImpl;
import org.hbrs.se2.project.aldavia.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.aldavia.entities.User;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LoginControl {

    @Autowired
    private UserRepository repository;

    private UserDTO userDTO = null;

    private final Logger logger = LoggerFactory.getLogger(LoginControl.class.getName());

    /**
     * Methode zur Authentifizierung eines Benutzers
     * @param username Benutzername
     * @param password Passwort
     * @return true, wenn die Authentifizierung erfolgreich war, sonst false
     * @throws DatabaseUserException in case user is not found
     */
    public boolean authenticate(String username, String password ) throws DatabaseUserException {

        logger.info("Authentifiziere user mit username: " + username);
        User tmpUser = this.getUserWithJPA( username , password );

        if ( tmpUser == null ) {
            throw new DatabaseUserException(
                    DatabaseUserException.
                            DatabaseUserExceptionType.
                            USER_NOT_FOUND,
                    "No User could be found! Please check your credentials!");
        }
        this.userDTO = buildUserDTO( tmpUser );
        logger.info("User " + username + " erfolgreich authentifiziert");
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
     * Methode zur Anfrage eines Benutzers mit JPA
     * @param username Benutzername
     * @param password Passwort
     * @return UserDTO
     * @throws DatabaseUserException in case user is not found
     */
    private User getUserWithJPA( String username , String password ) throws DatabaseUserException {
        Optional<User> userTmp;
        filterForCommonErrors(username, password);

        logger.info("Suche in DB user mit username: " + username);

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
                           DATABASE_CONNECTION_FAILED,
                   "A failure occured while trying to connect to database. Please try again later.");
        }

        logger.info("User mit username: " + username + " gefunden");

        return userTmp.orElse(null);
    }

    /**
     * Methode zur Filterung von häufigen Fehlern
     * @param username Benutzername / E-Mail-Adresse
     * @param password Passwort
     * @throws DatabaseUserException wenn ein Fehler auftritt
     */
    public void filterForCommonErrors(String username, String password) throws DatabaseUserException {
        if(( username == null || password == null ) || ( username.equals("") || password.equals("") )) {
            throw new DatabaseUserException(
                    DatabaseUserException.
                            DatabaseUserExceptionType.
                            USER_NOT_FOUND,
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
