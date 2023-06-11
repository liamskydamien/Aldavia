package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.dtos.StudentDataDTO;
import org.hbrs.se2.project.aldavia.dtos.UnternehmenDataDTO;
import org.hbrs.se2.project.aldavia.entities.Student;
import org.hbrs.se2.project.aldavia.entities.Unternehmen;

public class UserDataDTOFactory {
    private static UserDataDTOFactory instance;
    private UserDataDTOFactory() {
    }

    /**
     * Singleton Pattern
     * @return instance of UserDataDTOFactory
     */
    public static UserDataDTOFactory getInstance() {
        if (instance == null) {
            instance = new UserDataDTOFactory();
        }
        return instance;
    }

    /**
     * Create a StudentDataDTO from a Student
     * @param student Student
     * @return StudentDataDTO
     */
    public StudentDataDTO createStudentDataDTO(Student student) {
        return StudentDataDTO.builder()
                .vorname(student.getVorname())
                .nachname(student.getNachname())
                .profileLink(createProfileLinkStudent(student.getUser().getUserid()))
                .build();
    }

    /**
     * Create a UnternehmenDataDTO from a Unternehmen
     * @param unternehmen Unternehmen
     * @return UnternehmenDataDTO
     */
    public UnternehmenDataDTO createUnternehmenDataDTO(Unternehmen unternehmen) {
        return UnternehmenDataDTO.builder()
                .name(unternehmen.getName())
                .profileLink(createProfileLink(unternehmen.getUser().getUserid()))
                .build();
    }

    /**
     * Create a profile link for a student
     * @param username username of the student
     * @return profile link
     */
    private String createProfileLinkStudent( String username) {
        return "/student/" + username;
    }

    /**
     * Create a profile link for a unternehmen
     * @param username username of the unternehmen
     * @return profile link
     */
    private String createProfileLink(String username) {
        return "/unternehmen/" + username;
    }
}
