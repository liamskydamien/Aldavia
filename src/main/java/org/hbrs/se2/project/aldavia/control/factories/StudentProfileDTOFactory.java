package org.hbrs.se2.project.aldavia.control.factories;

import org.hbrs.se2.project.aldavia.control.exception.ProfileException;
import org.hbrs.se2.project.aldavia.dtos.*;
import org.hbrs.se2.project.aldavia.entities.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentProfileDTOFactory{

    private static StudentProfileDTOFactory instance;

    public static synchronized StudentProfileDTOFactory getInstance() {
        if (instance == null){
            instance = new StudentProfileDTOFactory();
        }
        return instance;
    }

    private StudentProfileDTOFactory() {
    }

    /**
     * Creates a list of KenntnisDTOs from a student
     * @param student The student
     * @return List of KenntnisDTOs
     */
    public StudentProfileDTO createStudentProfileDTO(Student student) throws ProfileException {
        try {
            User user = student.getUser();
            List<TaetigkeitsfeldDTO> taetigkeitsfeldDTOList = createTaetigkeitsfeldDTOList(student);
            List<QualifikationsDTO> qualifikationsDTOList = createQualifikationsDTOList(student);
            List<SpracheDTO> spracheDTOList = createSpracheDTOList(student);
            List<KenntnisDTO> kenntnisDTOList = createKenntnisDTOList(student);

            return StudentProfileDTO.builder()
                    .email(user.getEmail() != null ? user.getEmail() : "")
                    .vorname(student.getVorname() != null ? student.getVorname() : "")
                    .nachname(student.getNachname() != null ? student.getNachname() : "")
                    .matrikelNummer(student.getMatrikelNummer() != null ? student.getMatrikelNummer() : "")
                    .studiengang(student.getStudiengang() != null ? student.getStudiengang() : "")
                    .studienbeginn(student.getStudienbeginn() != null ? student.getStudienbeginn() : LocalDate.now())
                    .geburtsdatum(student.getGeburtsdatum() != null ? student.getGeburtsdatum() : LocalDate.now())
                    .kenntnisse(kenntnisDTOList)
                    .taetigkeitsfelder(taetigkeitsfeldDTOList)
                    .lebenslauf(student.getLebenslauf() != null ? student.getLebenslauf() : "")
                    .telefonnummer(user.getPhone() != null ? user.getPhone() : "")
                    .profilbild(user.getProfilePicture() != null ? user.getProfilePicture() : "")
                    .sprachen(spracheDTOList)
                    .qualifikationen(qualifikationsDTOList)
                    .beschreibung(user.getBeschreibung() != null ? user.getBeschreibung() : "")
                    .username(user.getUserid() != null ? user.getUserid() : "")
                    .build();
        }
        catch (Exception e){
            throw new ProfileException("Fehler beim Erstellen des Studenten Profils", ProfileException.ProfileExceptionType.ERROR_CREATING_PROFILE_DTO);
        }
    }

    private SpracheDTO createSpracheDTO(Sprache sprache) {
        if (sprache == null){
            return null;
        }
        return SpracheDTO.builder()
                .name(sprache.getBezeichnung())
                .level(sprache.getLevel())
                .id(sprache.getId())
                .build();
    }

    private List<SpracheDTO> createSpracheDTOList(Student student) {
        if (student.getSprachen() != null && !student.getSprachen().isEmpty()) {
            return student.getSprachen().stream().map(this::createSpracheDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private KenntnisDTO createKenntnisDTO(Kenntnis kenntnis) {
        if (kenntnis == null){
            return null;
        }
        return KenntnisDTO.builder()
                .name(kenntnis.getBezeichnung())
                .build();
    }

    private List<KenntnisDTO> createKenntnisDTOList(Student student) {
        if (student.getKenntnisse() != null && !student.getKenntnisse().isEmpty()){
            return student.getKenntnisse().stream().map(this::createKenntnisDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private TaetigkeitsfeldDTO createTaetigkeitsfeldDTO(Taetigkeitsfeld taetigkeitsfeld) {
        if (taetigkeitsfeld == null){
            return null;
        }
        return TaetigkeitsfeldDTO.builder()
                .name(taetigkeitsfeld.getBezeichnung())
                .build();
    }

    private List<TaetigkeitsfeldDTO> createTaetigkeitsfeldDTOList(Student student) {
        if(student.getTaetigkeitsfelder() != null && !student.getTaetigkeitsfelder().isEmpty()) {
            return student.getTaetigkeitsfelder().stream().map(this::createTaetigkeitsfeldDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private QualifikationsDTO createQualifikationsDTO(Qualifikation qualifikation) {
        if (qualifikation == null){
            return null;
        }
        return QualifikationsDTO.builder()
                .bezeichnung(qualifikation.getBezeichnung())
                .beschreibung(qualifikation.getBeschreibung())
                .bereich(qualifikation.getBereich())
                .institution(qualifikation.getInstitution())
                .beschaeftigungsart(qualifikation.getBeschaftigungsverhaltnis())
                .von(qualifikation.getVon())
                .bis(qualifikation.getBis())
                .id(qualifikation.getId())
                .build();
    }

    private List<QualifikationsDTO> createQualifikationsDTOList(Student student) {
        if(student.getQualifikationen() != null && !student.getQualifikationen().isEmpty()){
            return student.getQualifikationen().stream().map(this::createQualifikationsDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}

