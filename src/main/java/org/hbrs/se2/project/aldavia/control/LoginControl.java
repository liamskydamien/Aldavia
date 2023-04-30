package org.hbrs.se2.project.aldavia.control;

import org.hbrs.se2.project.aldavia.control.exception.DatabaseUserException;
import org.hbrs.se2.project.aldavia.dao.UserDAO;
import org.hbrs.se2.project.aldavia.dtos.UserDTO;
import org.hbrs.se2.project.aldavia.repository.UserRepository;
import org.hbrs.se2.project.aldavia.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.aldavia.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
    public boolean authentificate(String username, String password ) throws DatabaseUserException {

        UserDTO tmpUser = this.getUserWithJPA( username , password );

        if ( tmpUser == null ) {
            throw new DatabaseUserException(
                    DatabaseUserException.
                            DatabaseUserExceptionType.
                            UserNotFound,
                    "No User could be found! Please check your credentials!");
        }
        this.userDTO = tmpUser;
        return true;
    }

    public UserDTO getCurrentUser(){
        return this.userDTO;

    }

    /*
    private UserDTO getUserWithJDBC( String username , String password ) throws DatabaseUserException {
        UserDTO userTmp = null;
        UserDAO dao = new UserDAO();
        try {
            userDTO = dao.findUserByUseridAndPassword( username , password );
        }
        catch ( DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
            String reason = e.getReason();

            if (reason.equals(Globals.Errors.NOUSERFOUND)) {
                return userTmp;
                // throw new DatabaseUserException("No User could be found! Please check your credentials!");
            }
            else if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException(databaseUserExceptionType, "There were problems with the SQL code. Please contact the developer!");
            }
            else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException(databaseUserExceptionType, "A failure occured while trying to connect to database with JDBC. " +
                        "Please contact the admin");
            }
            else {
                throw new DatabaseUserException(databaseUserExceptionType, "A failure occured while");
            }

        }
        return userDTO;
    }
     */

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
    private UserDTO getUserWithJPA( String username , String password ) throws DatabaseUserException {
        UserDTO userTmp;
        try {
            userTmp = repository.findUserByUseridAndPassword(username, password);
        } catch ( org.springframework.dao.DataAccessResourceFailureException e ) {
            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen (ToDo!)
           throw new DatabaseUserException(
                   DatabaseUserException.
                        DatabaseUserExceptionType.
                        DatabaseConnectionFailed,
                   "A failure occured while trying to connect to database. Please try again later.");
        }
        return userTmp;
    }

}
